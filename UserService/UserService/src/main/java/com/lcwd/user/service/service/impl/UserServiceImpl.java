package com.lcwd.user.service.service.impl;

import com.lcwd.user.service.entities.Hotel;
import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.external.services.HotelService;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public User saveUser(User user) {

        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }


  @Override
    public List<User> getAllUser() {
      List<User> users = userRepository.findAll();

      return users.stream().map(user -> {
          // Fetch ratings for each user
          ResponseEntity<List<Rating>> ratingResponse = restTemplate.exchange(
                  "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
                  HttpMethod.GET,
                  null,
                  new ParameterizedTypeReference<List<Rating>>() {}
          );
          List<Rating> ratings = ratingResponse.getBody();

          // For each rating, fetch hotel details
          List<Rating> enrichedRatings = ratings.stream().map(rating -> {
              ResponseEntity<Hotel> hotelResponse = restTemplate.getForEntity(
                      "http://HOTELSERVICE/hotels/" + rating.getHotelId(),
                      Hotel.class
              );
              rating.setHotel(hotelResponse.getBody());
              return rating;
          }).collect(Collectors.toList());

          // Set ratings in user object
          user.setRatings(enrichedRatings);

          return user;
      }).collect(Collectors.toList());
    }



    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given Id is not Found"));

        // Correct deserialization using ParameterizedTypeReference
        ResponseEntity<List<Rating>> ratingResponse = restTemplate.exchange(
                "http://RATINGSERVICE/ratings/users/" + user.getUserId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Rating>>() {
                }
        );
        List<Rating> ratings = ratingResponse.getBody();

//        // For each rating, fetch the hotel and set it
//            List<Rating> ratingList = ratings.stream().map(rating -> {
//                ResponseEntity<Hotel> forEntity = restTemplate.getForEntity(
//                    "http://HOTELSERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            rating.setHotel(hotel);
//            return rating;
//        }).collect(Collectors.toList());
//
//        user.setRatings(ratingList);
//
        List<Rating> enrichedRatings = ratings.stream().map(rating -> {
            Hotel hotel = hotelService.getHotel(rating.getHotelId()); // Feign call
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(enrichedRatings);
        return user;


    }

}
