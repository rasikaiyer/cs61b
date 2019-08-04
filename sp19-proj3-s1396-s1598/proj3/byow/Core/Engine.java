package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
//import edu.princeton.cs.introcs.StdDraw;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
//    public void interactWithKeyboard() {
//        StdDraw.text(0.5, 0.7, "New game (n)");
//        StdDraw.text(0.5, 0.6, "Load game (l)");
//        StdDraw.text(0.5, 0.5, "Quit (q)");
//
//        char c = 'a';
//        boolean next = true;
//        int seed = 0;
//        String so_far = "";
//
//        while (next) {
//            if (StdDraw.hasNextKeyTyped()) {
//                c = Character.toLowerCase(StdDraw.nextKeyTyped());
//                so_far += c;
//                next = false;
//            }
//        }
//        MapGenerator map = new MapGenerator(new TETile[WIDTH][HEIGHT], 1);
//        if (c == 110) { // new game
//            seed = getSeed();
//            so_far += Integer.toString(seed);
//            map = initializeWorld(seed);
//        }
//
//        else if (c == 108) {
//            loadString(readFile("game.txt"));
//        }
//
//        else if (c == 113) { // quit game
//            System.exit(0);
//        }
//
//        boolean save = false;
//        while(true) {
//            if (StdDraw.hasNextKeyTyped()) {
//                c = Character.toLowerCase(StdDraw.nextKeyTyped());
//                so_far += c;
//                System.out.println(so_far);
//                if (c == ':') {
//                    save = true;
//                }
//                if (c == 'q') {
//                    if (save) {
//                        writeToFile(so_far);
//                        System.exit(0);
//                    } else {
//                        System.exit(0);
//                    }
//                }
//                map.drawAvatar(c);
//                ter.renderFrame(map.landscape);
//            }
//        }
//    }

    private MapGenerator initializeWorld(int seed) {
        // ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        MapGenerator map = new MapGenerator(world, seed);
        world = map.generate();
        map.drawAvatar('a');
//        System.out.println(map.rooms);

        // ter.renderFrame(world);

        return map;
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        // Fill out this method so that it run the engine using the input
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        char first = input.charAt(0);
        char last;
        boolean next = true;
        int counter = 1;
        String seed = "";
        String previousGame = "";
        MapGenerator map = new MapGenerator(new TETile[WIDTH][HEIGHT], 1);

        if (first == 110) { // n
            while (next) {
                last = input.charAt(counter);
                seed += last;
                counter += 1;
                if (last == 's') {
                    next = false;
                }
            }
            seed = seed.substring(0, seed.length() - 1);
            map = initializeWorld((int) Long.parseLong(seed));

        } else if (first == 108) {
            previousGame = readFile("game.txt");
            map.landscape = interactWithInputString(previousGame);
        } else if (first == 113) { // quit game
//            System.exit(0);
            int x = 0;
        }
        boolean save = false;
        char c;
        while (counter < input.length()) {
            c = input.charAt(counter);
            if (c == ':') {
                save = true;
            }
            if (c == 'q') {
                if (save) {
                    if (previousGame.length() > 0) {
                        String nextGame = previousGame.substring(0, previousGame.length() - 2)
                                + input.substring(1);
                        writeToFile(nextGame);
                    } else {
                        writeToFile(input);
                    }
//                    System.exit(0);
                } else {
                    int i = 0;
//                    System.exit(0);
                }
            }
            map.drawAvatar(c);
            counter++;
        }
        return map.landscape;
    }

//    private int getSeed() {
//        StdDraw.text(0.1, 0.4, "Seed:");
//        double x = 0.17;
//        boolean next = true;
//        String s = "";
//        char c = 'a';
//        while (next) {
//            if (StdDraw.hasNextKeyTyped()) {
//                c = Character.toLowerCase(StdDraw.nextKeyTyped());
//                if ((c > 47) && (c < 58)) {
//                    s += Character.toString(c);
//                    StdDraw.text(x, 0.4, Character.toString(c));
//                    x += 0.02;
//                }
//                if (c == 's') {
//                    next = false;
//                }
//            }
//        }
//        return (int) Long.parseLong(s);
//    }

    void writeToFile(String str) {
        try (PrintWriter out = new PrintWriter("game.txt")) {
            out.println(str);
        } catch (FileNotFoundException e) {
            int i = 0;
        }
    }

    private String readFile(String fileName) {
        String str = "";
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            StringBuffer stringBuffer = new StringBuffer();
            int numCharsRead;
            char[] charArray = new char[1024];
            while ((numCharsRead = fileReader.read(charArray)) > 0) {
                stringBuffer.append(charArray, 0, numCharsRead);
            }
            fileReader.close();
            str = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

//    void loadString(String str) {
//        for (int i = 1; i < str.length(); i++) {
//            String current = str.substring(0, i);
//            TETile[][] landscape = interactWithInputString(current);
//            ter.renderFrame(landscape);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//
//            }
//
//        }
//        interactWithKeyboard();
//    }


}

