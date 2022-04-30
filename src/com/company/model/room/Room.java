package com.company.model.room;

public abstract class Room {
    private static int id_max = 0;
    private int id;
    private boolean isAvailable = true ;
    private int roomNo;
    private int roomFloor;
    private double price;

    public Room(int noOfRoom, int roomFloor, double price) {
        id_max++;
        this.id = id_max;
        this.roomNo = noOfRoom;
        this.roomFloor = roomFloor;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
