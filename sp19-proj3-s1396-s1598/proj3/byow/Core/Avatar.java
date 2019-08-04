package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Avatar {
    Position position;
    TETile[][] grid;

    Avatar(TETile[][] grid, int xPos, int yPos) {
        this.grid = grid;
        this.position = new Position(grid, xPos, yPos);
    }

    Position move(char c) {
        if (c == 'w') { // up: w
            if ((grid[position.x][position.y + 1].character()) == Tileset.FLOOR.character()) {
                position.y += 1;
            }
        } else if (c == 's') { // down: s
            if (grid[position.x][position.y - 1].description().equals("floor")) {
                position.y -= 1;
            }
        } else if (c == 'd') { // right: d
            if (grid[position.x + 1][position.y].description().equals("floor")) {
                position.x += 1;
            }
        } else if (c == 'a') { // left: a
            if (grid[position.x - 1][position.y].description().equals("floor")) {
                position.x -= 1;
            }
        }
        return position;
    }

}
