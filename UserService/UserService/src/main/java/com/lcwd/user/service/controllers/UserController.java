package com.lcwd.user.service.controllers;


import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
    int retryCount =1;
    @GetMapping("/{userId}")
   // @CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
   // @Retry(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallBack")
    @RateLimiter(name="userRateLimiter", fallbackMethod = "ratingHotelFallBack")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){

        System.out.println("Retry Count:" +retryCount);
        retryCount++;
      User user =  userService.getUser(userId);
      return ResponseEntity.ok(user);
    }



    // creating fall back method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallBack(String userId, Exception ex){

       // logger.info("Fallback is executed because service is down ", ex.getMessage());
        System.out.println("Fallback is executed: " + ex.getMessage());
        User dummyUser = new User(
                "0000",
                "Dummy User",
                "dummyuser@example.com",
                "This user is created as a fallback"
        );

        return new ResponseEntity<>(dummyUser, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<User>>getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }
}
