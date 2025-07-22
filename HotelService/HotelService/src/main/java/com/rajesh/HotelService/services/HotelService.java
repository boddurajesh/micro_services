package com.rajesh.HotelService.services;

import com.rajesh.HotelService.entities.Hotel;

import java.util.List;

public interface HotelService {

    Hotel create (Hotel hotel);

    List<Hotel> getAllHotels();

    Hotel getHotel(String id);
}
