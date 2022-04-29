package com.company.model.hotel;

import com.company.model.room.*;
import com.company.model.user.HotelManager;

import java.util.*;

public class Hotel {
    private static int id_max = 0;
    private int id;
    private String name;
    private TreeSet<Room> rooms;

    public Hotel(String name, TreeSet<Room> rooms) {
        id_max++;
        this.id = id_max;
        this.name = name;
        this.rooms = rooms;
    }

    public Hotel(String name) {
        id_max++;
        this.id = id_max;
        this.name = name;
        rooms = new TreeSet<>(new RoomComparator());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Room> getRooms() {
        return rooms;
    }

    public void setRooms(TreeSet<Room> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
