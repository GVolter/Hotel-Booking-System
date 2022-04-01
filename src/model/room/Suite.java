package model.room;

public class Suite extends Room{
    private int noOfRooms;

    public Suite(int noOfRoom, int noOfBeds, int roomFloor, double price, int noOfRooms) {
        super(noOfRoom, noOfBeds, roomFloor, price);
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
                "status='" + status + '\'' +
                ", noOfRoom=" + noOfRoom +
                ", noOfBeds=" + noOfBeds +
                ", roomFloor=" + roomFloor +
                ", price=" + price +
                ", noOfRooms=" + noOfRooms +
                '}';
    }
}
