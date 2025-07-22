package com.lcwd.rating.RatingService.conttoller;


import com.lcwd.rating.RatingService.entities.Rating;
import com.lcwd.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

     @Autowired
    private RatingService ratingService;

     @PostMapping
     public ResponseEntity<Rating> create(@RequestBody Rating rating){
         return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
     }

     @GetMapping
     public ResponseEntity<List<Rating>> getRating(){
         return ResponseEntity.ok(ratingService.getRating());
     }

    @DeleteMapping("/{ratingId}")
    public ResponseEntity<String> deleteRating(@PathVariable String ratingId) {
        ratingService.deleteRating(ratingId);
        return ResponseEntity.ok("Rating with ID " + ratingId + " has been deleted successfully.");
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingByUserId(@PathVariable String userId){
        return ResponseEntity.ok(ratingService.getRatingByUserId(userId));
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingByHotelId(@PathVariable String hotelId){
        return ResponseEntity.ok(ratingService.getRatingByHotelId(hotelId));
    }



}
