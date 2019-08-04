package byow.Core;

public class Room {
    Position upperLeft;
    int width;
    int height;

    Room(Position given, int width, int height) {
        upperLeft = given;
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "{upperLeftX: " + upperLeft.x
                + ", upperLeftY: " + upperLeft.y
                + ", width: " + width
                + ", height: " + height + "}";
    }

    int[] arr() {
        int[] arr = {upperLeft.x, upperLeft.y, width, height};
        return arr;
    }
}
