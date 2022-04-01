package model.room;

public class OneRoom extends Room{
    private OneRoomType type;


    public OneRoom(int noOfRoom, int noOfBeds, int roomFloor, double price, OneRoomType type) {
        super(noOfRoom, noOfBeds, roomFloor, price);
        this.type = type;
    }

    public OneRoomType getType() {
        return type;
    }

    public void setType(OneRoomType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OneRoom{" +
                "type=" + type +
                ", status='" + status + '\'' +
                ", noOfRoom=" + noOfRoom +
                ", noOfBeds=" + noOfBeds +
                ", roomFloor=" + roomFloor +
                ", price=" + price +
                '}';
    }
}
