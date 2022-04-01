package model.hotel;

import model.room.*;

import java.util.*;

public class Hotel {
    private static int ID = 0;
    private int id;
    private String name;
    private TreeSet<Room> rooms;

    public Hotel(String name, TreeSet<Room> rooms) {
        ID++;
        this.id = ID;
        this.name = name;
        this.rooms = rooms;
    }

    public Hotel(String name) {
        ID++;
        this.id = ID;
        this.name = name;
        rooms =   new TreeSet<Room>(new RoomComparator());
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
