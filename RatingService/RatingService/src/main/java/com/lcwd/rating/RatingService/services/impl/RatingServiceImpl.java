package com.lcwd.rating.RatingService.services.impl;

import com.lcwd.rating.RatingService.entities.Rating;
import com.lcwd.rating.RatingService.repository.RatingRepo;
import com.lcwd.rating.RatingService.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepo ratingRepo;
    @Override
    public Rating create(Rating rating) {
        return ratingRepo.save(rating);
    }

    @Override
    public List<Rating> getRating() {
        return ratingRepo.findAll();
    }

    @Override
    public void deleteRating(String ratingId) {
        ratingRepo.deleteById(ratingId);
    }

    @Override
    public List<Rating> getRatingByUserId(String userId) {
        return  ratingRepo.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingByHotelId(String hotelId) {
        return getRatingByHotelId(hotelId);
    }
}
