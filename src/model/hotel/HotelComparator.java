package model.hotel;

import java.util.Comparator;

public class HotelComparator implements Comparator<Hotel> {
    public int compare(Hotel h1, Hotel h2) {
        return h1.getName().compareTo(h2.getName());
    }
}
