package byow.Core;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

class MapGenerator {
    TETile[][] landscape;
    private Random r;
    private int numOfRooms = 0;
    ArrayList<Room> rooms;
    private Position start1;

    MapGenerator(TETile[][] landscape, int seed) {
        this.landscape = landscape;
        r = new Random(seed);
        rooms = new ArrayList<>();
        start1 = new Position(landscape, 1, 1);
        this.landscape = generate();
    }

    //generates a world in the landscape given a random seed
    TETile[][] generate() {
        landscape = new TETile[Engine.WIDTH][Engine.HEIGHT];
        for (int i = 0; i < Engine.WIDTH; i++) {
            for (int j = 0; j < Engine.HEIGHT; j++) {
                landscape[i][j] = Tileset.SAND;
            }
        }
        int placeFails = 0;
        while (placeFails < 40) {
            if (!createRoom()) {
                placeFails++;
            } else {
                numOfRooms++;
            }
        }

        for (Room room : rooms) {
            int random = r.nextInt(rooms.size());
            Room room2 = rooms.get(random);
            connectRooms(room.upperLeft, room2.upperLeft);
        }
        drawWall();

        while (!(landscape[start1.x][start1.y].description().equals("floor"))) {
            start1 = new Position(landscape, r.nextInt(Engine.WIDTH), r.nextInt(Engine.HEIGHT));
        }
        return landscape;
    }

    void drawAvatar(char c) {
        landscape[start1.x][start1.y] = Tileset.FLOOR;
        Avatar a = new Avatar(landscape, start1.x, start1.y);
        a.position = a.move(c);
        start1 = new Position(landscape, (a.position).x, (a.position).y);
        landscape[start1.x][start1.y] = Tileset.AVATAR;
        System.out.println((a.position).x);
        System.out.println((a.position).y);
    }

    //put walls around all the floor
    void drawWall() {
        for (int x = 0; x < landscape.length; x++) {
            for (int y = 0; y < landscape[0].length; y++) {
                TETile current = landscape[x][y];
                if (current.character() == '·') {
                    continue;
                }
                ArrayList<TETile> neighbors = new ArrayList<>();
                if (x != 0) {
                    neighbors.add(landscape[x - 1][y]);
                    if (y != 0) {
                        neighbors.add(landscape[x - 1][y - 1]);
                    }
                    if (y != landscape[0].length - 1) {
                        neighbors.add(landscape[x - 1][y + 1]);
                    }
                }

                if (x != landscape.length - 1) {
                    TETile e = landscape[x + 1][y];
                    if (y != 0) {
                        neighbors.add(landscape[x + 1][y - 1]);
                    }
                    if (y != landscape[0].length - 1) {
                        neighbors.add(landscape[x + 1][y + 1]);
                    }
                }

                if (y != 0) {
                    neighbors.add(landscape[x][y - 1]);
                }
                if (y != landscape[0].length - 1) {
                    neighbors.add(landscape[x][y + 1]);
                }

                boolean needWall = floorDetector(neighbors);
                if (needWall) {
                    landscape[x][y] = Tileset.WALL;
                }
            }
        }
    }

    private boolean floorDetector(ArrayList<TETile> neighbors) {
        for (TETile tile : neighbors) {
            if (tile.character() == '·') {
                return true;
            }
        }
        return false;
    }

    boolean createRoom() {
        int width = r.nextInt(10) + 5;
        int height = r.nextInt(10) + 5;
        int ulX = r.nextInt(landscape.length - 3 - width) + 1;
        int ulY = r.nextInt(landscape[0].length - 3 - height) + 1;
        Position ul = new Position(landscape, ulX, ulY);
        Room room = new Room(ul, width, height);
        for (int i = ulX; i < width + ulX; i++) {
            for (int j = ulY; j < height + ulY; j++) {
                if (landscape[i][j].character() == '·') {
                    return false;
                }
            }
        }
        for (int i = ulX; i < width + ulX; i++) {
            for (int j = ulY; j < height + ulY; j++) {
                landscape[i][j] = Tileset.FLOOR;
            }
        }
        rooms.add(room);
        return true;
    }

    boolean connectRooms(Position roomA, Position roomB) {
        if (roomA.x < 0 || roomA.y < 0 || roomB.x < 0 || roomB.y < 0) {
            return false;
        }

        if (roomA.x == roomB.x) {
            if (roomA.y > roomB.y) {
                goUp(roomA.y, roomB.y, roomA.x);
            } else if (roomA.y < roomB.y) {
                goDown(roomA.y, roomB.y, roomA.x);
            } else {
                return false;
            }
        }

        if (roomA.y < roomB.y) {
            if (roomA.x > roomB.x) {
                goDown(roomA.y, roomB.y, roomA.x);
                goLeft(roomB.x, roomA.x, roomB.y);
            } else if (roomA.x < roomB.x) {
                goDown(roomA.y, roomB.y, roomA.x);
                goRight(roomA.x, roomB.x, roomB.y);
            } else {
                return false;
            }
            return true;
        } else if (roomA.y > roomB.y) {
            if (roomA.x > roomB.x) {
                goUp(roomB.y, roomA.y, roomA.x);
                goLeft(roomB.x, roomA.x, roomB.y);
            } else if (roomA.x < roomB.x) {
                goUp(roomB.y, roomA.y, roomA.x);
                goRight(roomA.x, roomB.x, roomB.y);
            } else {
                return false;
            }
            return true;
        } else {
            if (roomA.x > roomB.x) {
                goLeft(roomB.x, roomA.x, roomB.y);
            } else if (roomA.x < roomB.x) {
                goRight(roomA.x, roomB.x, roomB.y);
            } else {
                return false;
            }
        }
        return true;
    }

    void goLeft(int start, int end, int y) {
        for (int i = start; i <= end; i++) {
            landscape[i][y] = Tileset.FLOOR;
        }
    }

    void goUp(int start, int end, int x) {
        for (int i = start; i <= end; i++) {
            landscape[x][i] = Tileset.FLOOR;
        }
    }

    void goDown(int start, int end, int x) {
        for (int i = start; i <= end; i++) {
            landscape[x][i] = Tileset.FLOOR;
        }
    }

    void goRight(int start, int end, int y) {
        for (int i = start; i <= end; i++) {
            landscape[i][y] = Tileset.FLOOR;
        }
    }
}
