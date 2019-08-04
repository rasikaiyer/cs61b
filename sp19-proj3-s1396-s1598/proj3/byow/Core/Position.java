package byow.Core;

import byow.TileEngine.TETile;

public class Position {
    TETile[][] landscape;
    int x;
    int y;
//    String orientation;

    Position(TETile[][] landscape, int x, int y) {
        this.landscape = landscape;
        this.x = x;
        this.y = y;
    }

    int compare(Position other) {
        int xDiff = other.x - x;
        int yDiff = other.y - y;
        if (xDiff > 0 && yDiff > 0) {
            return 1;
        } else if (xDiff < 0 && yDiff > 0) {
            return 2;
        } else if (xDiff < 0 && yDiff < 0) {
            return 3;
        } else if (xDiff > 0 && yDiff < 0) {
            return 4;
        } else {
            return 0;
        }
    }
}
