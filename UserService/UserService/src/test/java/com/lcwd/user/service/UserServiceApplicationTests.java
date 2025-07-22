package com.lcwd.user.service;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.external.services.RatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private RatingService ratingService;

	@Test
	void createTesting(){

		Rating rating = Rating.builder().rating(10).userId("").hotelId("").feedback("this is created using feing client").build();
		ratingService.createRating(rating);

		System.out.println("New rating created");

	}

}
