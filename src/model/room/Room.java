package model.room;

public abstract class Room {
    protected String status = "Available";
    protected int noOfRoom;
    protected int noOfBeds;
    protected int roomFloor;
    protected double price;

    public Room(int noOfRoom, int noOfBeds, int roomFloor, double price) {
        this.noOfRoom = noOfRoom;
        this.noOfBeds = noOfBeds;
        this.roomFloor = roomFloor;
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNoOfBeds() {
        return noOfBeds;
    }

    public void setNoOfBeds(int noOfBeds) {
        this.noOfBeds = noOfBeds;
    }

    public int getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(int roomFloor) {
        this.roomFloor = roomFloor;
    }

    public int getNoOfRoom() {
        return noOfRoom;
    }

    public void setNoOfRoom(int noOfRoom) {
        this.noOfRoom = noOfRoom;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "status='" + status + '\'' +
                ", noOfRoom=" + noOfRoom +
                ", noOfBeds=" + noOfBeds +
                ", roomFloor=" + roomFloor +
                ", price=" + price +
                '}';
    }
}
