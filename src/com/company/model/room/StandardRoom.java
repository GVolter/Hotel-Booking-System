package com.company.model.room;

public class StandardRoom extends Room{
    private StandardRoomType type;


    public StandardRoom(int roomNo, int roomFloor, double price, StandardRoomType type) {
        super(roomNo, roomFloor, price);
        this.type = type;
    }

    public StandardRoomType getType() {
        return type;
    }

    public void setType(StandardRoomType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StandardRoom{" +
                "type=" + type +
                ", status='" + isAvailable() + '\'' +
                ", noOfRoom=" + getRoomNo() +
                ", roomFloor=" + getRoomFloor() +
                ", price=" + getPrice() +
                '}';
    }
}
