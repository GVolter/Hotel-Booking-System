package com.company.services;

import com.company.model.hotel.Hotel;
import com.company.model.room.Room;
import com.company.model.room.Suite;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private static HotelService instance = new HotelService();

    private HotelService() {}

    public static HotelService getInstance() {
        return instance;
    }

    private final Path PATH = Path.of("resources/hotels.csv");

    private CSVReader<Hotel> hotelCSVReader = new CSVReader<>();
    private CSVWriter<Hotel> hotelCSVWriter = new CSVWriter<>(PATH);

    private List<Hotel> hotels = new ArrayList<>();

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public List<Hotel> readHotels() {
        return hotelCSVReader.read(PATH);
    }

    public void write(Hotel hotel) {
        hotelCSVWriter.write(hotel);
    }

}
