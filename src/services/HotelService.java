package services;

import model.hotel.Hotel;
import model.room.Room;

public class HotelService {
    private static HotelService instance = new HotelService();

    private HotelService() {}

    public static HotelService getInstance() {
        return instance;
    }

    public void addRoom(Room room, Hotel hotel){
        hotel.getRooms().add(room);
    }
}
