package model.room;

import java.util.Comparator;

public class RoomComparator implements Comparator<Room> {
    public int compare(Room r1, Room r2) {
        return Integer.compare(r1.getNoOfRoom(), r2.getNoOfRoom());
    }
}
