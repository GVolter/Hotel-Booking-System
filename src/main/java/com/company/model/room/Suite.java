package com.company.model.room;

public class Suite extends Room{
    private int noOfRooms;

    public Suite(int roomNo, int roomFloor, double price, int noOfRooms) {
        super(roomNo, roomFloor, price);
        this.noOfRooms = noOfRooms;
    }

    public Suite(int noOfRoom, int roomFloor, double price, boolean isAvailable, int noOfRooms) {
        super(noOfRoom, roomFloor, price, isAvailable);
        this.noOfRooms = noOfRooms;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    @Override
    public String toString() {
        return "Suite{" +
                "status='" + isAvailable() + '\'' +
                ", noOfRoom=" + getRoomNo() +
                ", roomFloor=" + getRoomFloor() +
                ", price=" + getPrice() +
                ", noOfRooms=" + noOfRooms +
                '}';
    }
}
