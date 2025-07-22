package com.rajesh.HotelService.services.impl;

import com.rajesh.HotelService.entities.Hotel;
import com.rajesh.HotelService.exceptions.ResourceNotFoundException;
import com.rajesh.HotelService.repositories.HotelRepo;
import com.rajesh.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepo hotelRepo;
    @Override
    public Hotel create(Hotel hotel) {

        String rid = UUID.randomUUID().toString();
        hotel.setId(rid);
        return hotelRepo.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepo.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found"));
    }
}
