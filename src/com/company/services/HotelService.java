package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.room.Room;

import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private static HotelService instance = new HotelService();

    private HotelService() {}

    public static HotelService getInstance() {
        return instance;
    }

    private List<Hotel> hotels = new ArrayList<>();

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

}
