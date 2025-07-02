package ex3;

import assignments.ex3.Index2D;
import assignments.ex3.Map;
import assignments.ex3.Map2D;
import assignments.ex3.Pixel2D;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Shirs duek
 * @ID 325175115
 */

/** This is NOT a Junit class - as it tests GUI components which
 * should not be tested using Junit.
 *
 * The Code uses the STDDraw class:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 * @author boaz.benmoshe
 *
 */
public class StdDrawTest {

    static int[][] _mat = {{1, 1, 1, 1, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 1, 1, 1, 1}, {1, 0, 1, 0, 1}};

    static int[][] _m4 = new int[100][100];
    static Pixel2D p1 = new Index2D(0, 0);
    static Pixel2D p2 = new Index2D(0, 4);
    static Pixel2D p3 = new Index2D(2, 4);
    static Pixel2D p4 = new Index2D(5, 4);
    static int obscolor = 0;
    static int[][] newMat1;

    static Pixel2D[] points = new Pixel2D[]{p1, p2, p3, p4};

    static int[][] _mat2 = {{1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}};


    public static void main(String[] a) throws InterruptedException {
//        testBigMap();
//        TimeUnit.SECONDS.sleep(2);
//        testBigMap1();
//        TimeUnit.SECONDS.sleep(2);
//        testSetPixel();
//        TimeUnit.SECONDS.sleep(2);
//        testChak();
//        TimeUnit.SECONDS.sleep(2);
//        testPointPath();
//        TimeUnit.SECONDS.sleep(2);
//        testShortPath2Points();
//        TimeUnit.SECONDS.sleep(2);
//        testShortsPath4Points();
//        TimeUnit.SECONDS.sleep(2);
//        testNumberOfConnectedComponents();
//        TimeUnit.SECONDS.sleep(2);
//       testToMatrixUser(); // Entering a specific matrix of the user
//        TimeUnit.SECONDS.sleep(2);
       testToUser(); // Entering the size of a matrix and performing operations on it.

    }

    public static void testToUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a map size: The first is height, the second is width");
        int height = scanner.nextInt();
        int width = scanner.nextInt();
        int[][] map = new int[width][height];
        Map2D myMap = new Map(map);
        while (true) {
            System.out.println("if you want fill write 1");
            System.out.println("if you want Shortest path write 2");
            System.out.println("if you want paint specific points write 3");
            System.out.println("if you want regions write 4");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\"Enter the coordinates (x y) for point \"");
                    int startPointX = scanner.nextInt();
                    int startPointY = scanner.nextInt();
                    System.out.println("Choose a new color(number from 1 until 10)");
                    int newColor = scanner.nextInt();
                    System.out.println("if Cyclic write 1 or else write 2");
                    int cyclic = scanner.nextInt();
                    switch (cyclic) {
                        case 1:
                            myMap.setCyclic(true);
                            myMap.fill(new Index2D(startPointX, startPointY), newColor);
                            drawMat(myMap.getMap());
                            break;
                        case 2:
                            myMap.setCyclic(false);
                            myMap.fill(new Index2D(startPointX, startPointY), newColor);
                            drawMat(myMap.getMap());
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Enter the number of points you want for path:");
                    int numPoints = scanner.nextInt();
                    Pixel2D[] pointsToFill = new Pixel2D[numPoints];
                    for (int i = 0; i < numPoints; i++) {
                        System.out.println("Enter the coordinates (x y) for point " + (i + 1) + ":");
                        int x = scanner.nextInt();
                        int y = scanner.nextInt();
                        pointsToFill[i] = new Index2D(x, y);
                    }
                    System.out.println("Choose a absColor (number from 1 until 10)");
                    int absColor1 = scanner.nextInt();
                    System.out.println("if Cyclic write 1 or else write 2");
                    int cyclic1 = scanner.nextInt();
                    switch (cyclic1) {
                        case 1:
                            myMap.setCyclic(true);
                            Pixel2D[] path = myMap.shortestPath(pointsToFill, absColor1);
                            System.out.println("Choose a new color (number from 1 until 10)");
                            int newColor1 = scanner.nextInt();
                            for (Pixel2D p : path) {
                                myMap.setPixel(p, newColor1);
                            }
                            drawMat(myMap.getMap());
                            break;
                        case 2:
                            myMap.setCyclic(false);
                            Pixel2D[] path2 = myMap.shortestPath(pointsToFill, absColor1);
                            System.out.println("Choose a new color (number from 1 until 10)");
                            int newColor2 = scanner.nextInt();
                            for (Pixel2D p : path2) {
                                myMap.setPixel(p, newColor2);
                            }
                            drawMat(myMap.getMap());
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Enter the number of points you want to fill:");
                    int numPoints1 = scanner.nextInt();
                    Pixel2D[] pointsToFill1 = new Pixel2D[numPoints1];
                    for (int i = 0; i < numPoints1; i++) {
                        System.out.println("Enter the coordinates (x y) for point " + (i + 1) + ":");
                        int x = scanner.nextInt();
                        int y = scanner.nextInt();
                        pointsToFill1[i] = new Index2D(x, y);
                    }

                    System.out.println("Choose a new color (number from 1 until 10)");
                    int newColor3 = scanner.nextInt();
                    for (Pixel2D p : pointsToFill1) {
                        myMap.setPixel(p, newColor3);
                    }
                    drawMat(myMap.getMap());
                    break;
                case 4:
                    System.out.println("if Cyclic write 1 or else write 2");
                    int cyclic2 = scanner.nextInt();
                    switch (cyclic2) {
                        case 1:
                            myMap.setCyclic(true);
                            System.out.println("Enter obsColor:");
                            int obsColor = scanner.nextInt();
                            int[][] newMat1;
                            System.out.println("Amount of areas when 0 is obsColor(black)\n");
                            newMat1 = drawConnectedComponents(myMap.getMap(), obsColor);
                            drawMat(newMat1);
                            break;
                        case 2:
                            myMap.setCyclic(false);
                            System.out.println("Enter obsColor:");
                            int obsColor1 = scanner.nextInt();
                            int[][] newMat2;
                            System.out.println("Amount of areas when 0 is obsColor(black)\n");
                            newMat2 = drawConnectedComponents(myMap.getMap(), obsColor1);
                            drawMat(newMat2);
                            break;
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
            System.out.println("Do you want to continue? (yes/no)");
            String continueChoice = scanner.next();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                break;
            }
        }
        System.out.println("Program ended.");
    }

    public static void testToMatrixUser() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Choose a map size: The first is width , the second is height ");
            int height = scanner.nextInt();
            int width = scanner.nextInt();
            int[][] map = new int[width][height];
            Map2D myMap = new Map(map);
            System.out.println("Now please enter the values for each cell in the matrix:");
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    System.out.println("Enter the value for cell (" + (i) + ", " + j + "):");
                    int value = scanner.nextInt();
                    myMap.setPixel(i, j, value);
                }
            }
        for (int[] row : myMap.getMap()) System.out.println(Arrays.toString(row));
        while (true) {
            System.out.println("if you want fill write 1");
            System.out.println("if you want Shortest path write 2");
            System.out.println("if you want paint specific points write 3");
            System.out.println("if you want regions write 4");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\"Enter the coordinates (x y) for point \"");
                    int startPointX = scanner.nextInt();
                    int startPointY = scanner.nextInt();
                    System.out.println("Choose a new color(number from 1 until 10)");
                    int newColor = scanner.nextInt();
                    System.out.println("if Cyclic write 1 or else write 2");
                    int cyclic = scanner.nextInt();
                    switch (cyclic) {
                        case 1:
                            myMap.setCyclic(true);
                            myMap.fill(new Index2D(startPointX, startPointY), newColor);
                            drawMat(myMap.getMap());
                            break;
                        case 2:
                            myMap.setCyclic(false);
                            myMap.fill(new Index2D(startPointX, startPointY), newColor);
                            drawMat(myMap.getMap());
                            break;
                    }
                    break;
                case 2:
                    System.out.println("Enter the number of points you want for path:");
                    int numPoints = scanner.nextInt();
                    Pixel2D[] pointsToFill = new Pixel2D[numPoints];
                    for (int i = 0; i < numPoints; i++) {
                        System.out.println("Enter the coordinates (x y) for point " + (i + 1) + ":");
                        int x = scanner.nextInt();
                        int y = scanner.nextInt();
                        pointsToFill[i] = new Index2D(x, y);
                    }
                    System.out.println("Choose a absColor (number from 1 until 10)");
                    int absColor1 = scanner.nextInt();
                    System.out.println("if Cyclic write 1 or else write 2");
                    int cyclic1 = scanner.nextInt();
                    switch (cyclic1) {
                        case 1:
                            myMap.setCyclic(true);
                            Pixel2D[] path = myMap.shortestPath(pointsToFill, absColor1);
                            System.out.println("Choose a new color (number from 1 until 10)");
                            int newColor1 = scanner.nextInt();
                            for (Pixel2D p : path) {
                                myMap.setPixel(p, newColor1);
                            }
                            drawMat(myMap.getMap());
                            break;
                        case 2:
                            myMap.setCyclic(false);
                            Pixel2D[] path2 = myMap.shortestPath(pointsToFill, absColor1);
                            System.out.println("Choose a new color (number from 1 until 10)");
                            int newColor2 = scanner.nextInt();
                            for (Pixel2D p : path2) {
                                myMap.setPixel(p, newColor2);
                            }
                            drawMat(myMap.getMap());
                            break;
                    }
                    break;
                case 3:
                    System.out.println("Enter the number of points you want to fill:");
                        int numPoints1 = scanner.nextInt();
                        Pixel2D[] pointsToFill1 = new Pixel2D[numPoints1];
                        for (int i = 0; i < numPoints1; i++) {
                            System.out.println("Enter the coordinates (x y) for point " + (i + 1) + ":");
                            int x = scanner.nextInt();
                            int y = scanner.nextInt();
                            pointsToFill1[i] = new Index2D(x, y);
                        }

                        System.out.println("Choose a new color (number from 1 until 10)");
                        int newColor3 = scanner.nextInt();
                        for (Pixel2D p : pointsToFill1) {
                            myMap.setPixel(p, newColor3);
                        }
                        drawMat(myMap.getMap());
                        break;
                case 4:
                    System.out.println("if Cyclic write 1 or else write 2");
                    int cyclic2 = scanner.nextInt();
                    switch (cyclic2) {
                        case 1:
                            myMap.setCyclic(true);
                            System.out.println("Enter obsColor:");
                            int obsColor = scanner.nextInt();
                            int[][] newMat1;
                            System.out.println("Amount of areas when 0 is obsColor(black)\n");
                            newMat1 = drawConnectedComponents(myMap.getMap(), obsColor);
                            drawMat(newMat1);
                            break;
                        case 2:
                            myMap.setCyclic(false);
                            System.out.println("Enter obsColor:");
                            int obsColor1 = scanner.nextInt();
                            int[][] newMat2;
                            System.out.println("Amount of areas when 0 is obsColor(black)\n");
                            newMat2 = drawConnectedComponents(myMap.getMap(), obsColor1);
                            drawMat(newMat2);
                            break;
                    }
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
            System.out.println("Do you want to continue? (yes/no)");
            String continueChoice = scanner.next();
            if (!continueChoice.equalsIgnoreCase("yes")) {
                break;
            }
        }
        System.out.println("Program ended.");
    }

    public static void testBigMap() {
        StdDraw.clear();
        Map2D mapCopy1 = new Map(_m4);
        for (int i = 0; i < mapCopy1.getWidth(); i++) {
            for (int j = 0; j < mapCopy1.getHeight(); j++) {
                if (j < 40) {
                    mapCopy1.setPixel(i, j, 1);
                }
                if (j >= 40 && j < 70) {
                    mapCopy1.setPixel(i, j, 2);
                }
                if (j >= 70 && j < 100) {
                    mapCopy1.setPixel(i, j, 3);
                }

            }
        }
        drawMat(mapCopy1.getMap());
    }

    public static void testBigMap1() {
        System.out.println("\n");
        StdDraw.clear();
        Map2D mapCopy1 = new Map(_m4);
        for (int i = 0; i < mapCopy1.getWidth(); i++) {
            for (int j = 0; j < mapCopy1.getHeight(); j++) {
                if (i < 40) {
                    mapCopy1.setPixel(i, j, 1);
                }
                if (i >= 40 && i < 70) {
                    mapCopy1.setPixel(i, j, 2);
                }
                if (i >= 70 && i <= 100) {
                    mapCopy1.setPixel(i, j, 3);
                }
            }
        }
        drawMat(mapCopy1.getMap());

    }


    public static void testPointPath() {
        System.out.println("Color change at a 4 specifics point\n");
        int[][] newMat1 = drawPoints(_mat, points, 3);
        drawMat(newMat1);
    }

    public static void testShortPath2Points() throws InterruptedException {
        System.out.println("Shorts Path 2 Points when not cyclic \n");
        Map2D newMap = new Map(_mat);
        newMap.setCyclic(false);
        Pixel2D[] path = newMap.shortestPath(p1, p3, obscolor);
//        Map meMap = (Map) newMap;
        for (int i = 0; i < path.length; i++) {
            System.out.println(i + ")  " + path[i]);
        }
        System.out.println("amount of slots: " + path.length + "\n");
        newMat1 = drawPoints(_mat, path, 4);
        drawMat(newMat1);

        TimeUnit.SECONDS.sleep(2);

        System.out.println("Shorts Path 2 Points when is cyclic \n");
        Map2D newMap1 = new Map(_mat);
        Pixel2D[] path1 = newMap1.shortestPath(p1, p3, obscolor);
        for (int i = 0; i < path1.length; i++) {
            System.out.println(i + ")  " + path1[i]);
        }
        System.out.println("amount of slots: " + path1.length + "\n");
        newMat1 = drawPoints(_mat, path1, 4);
        drawMat(newMat1);


    }

    public static void testShortsPath4Points() throws InterruptedException {
        System.out.println("Shorts Path 4 Points when not cyclic \n");
        Map2D newMap3 = new Map(_mat);
        newMap3.setCyclic(false);
        Pixel2D[] path1 = newMap3.shortestPath(points, obscolor);
        for (int i = 0; i < path1.length; i++) {
            System.out.println(i + ")  " + path1[i]);
        }
        newMat1 = drawPoints(_mat, path1, 4);
        drawMat(newMat1);
        System.out.println("amount of slots: " + path1.length + "\n");

        TimeUnit.SECONDS.sleep(2);

        System.out.println("Shorts Path 4 Points when is cyclic \n");
        Map2D newMap4 = new Map(_mat);
        ;
        Pixel2D[] path2 = newMap4.shortestPath(points, obscolor);
        for (int i = 0; i < path2.length; i++) {
            System.out.println(i + ")  " + path2[i]);
        }
        newMat1 = drawPoints(_mat, path2, 4);
        drawMat(newMat1);
        System.out.println("amount of slots: " + path2.length + "\n");

    }

    public static void testNumberOfConnectedComponents() throws InterruptedException {
        System.out.println("Amount of areas when 0 is obsColor(black)\n");
        newMat1 = drawConnectedComponents(_mat, 0);
        drawMat(newMat1);

        TimeUnit.SECONDS.sleep(2);
        System.out.println("Amount of areas when 1 is obsColor(red)\n");
        newMat1 = drawConnectedComponents(_mat, 1);
        drawMat(newMat1);
    }

    public static void testChak() throws InterruptedException {
        System.out.println("Trying to get from point to point when is not cyclic: \n");
        drawMat(_mat2);
        Map2D newMap1 = new Map(_mat2);
        newMap1.setCyclic(false);
        try {
            Pixel2D[] path1 = newMap1.shortestPath(p1, p4, 0);
            _mat2 = drawPoints(_mat2, path1, 2);
            drawMat(_mat2);
        } catch (Exception e) {
            System.out.println("there is no path from p1 to p4\n");
        }
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Trying to get from point to point when is cyclic from p1 to p4: \n");
        drawMat(_mat2);
        Map2D newMap2 = new Map(_mat2);
        Pixel2D[] path1 = newMap2.shortestPath(p1, p4, 0);
        _mat2 = drawPoints(_mat2, path1, 2);
        drawMat(_mat2);
    }

    public static void testSetPixel() {
        System.out.println("Color change at a specific point\n");
        Map2D newMap2 = new Map(_mat);
        newMap2.setPixel(p1, 3);
        drawMat(newMap2.getMap());
    }


    public static int[][] drawFill(int[][] mat, Pixel2D point, int new_v) {
        StdDraw.clear();
        Map meMap = new Map(mat);
        meMap.fill(point, new_v);
        return meMap.getMap();
    }

    public static int[][] drawPoints(int[][] mat, Pixel2D[] points, int new_v) {
        StdDraw.clear();
        Map meMap = new Map(mat);
        for (int i = 0; i < points.length; i++) {
            meMap.setPixel(points[i], new_v);
        }
        return meMap.getMap();
    }

    static int[][]  drawConnectedComponents(int[][] mat, int obsColor) throws RuntimeException {
        if (obsColor < 0) {
            throw new RuntimeException("obsColor need to be positive");
        }
        int count = 0;
        int ans = 2;

        Map2D copyMap = new Map(mat);

        int marker = 1000;
        for (int i = 0; i < copyMap.getWidth(); i++) {
            for (int j = 0; j < copyMap.getHeight(); j++) {
                if (copyMap.getPixel(i, j) != marker && copyMap.getPixel(i, j) != obsColor) {
                    copyMap.fill(new Index2D(i, j), marker);

                    mat = drawFill(mat, new Index2D(i, j), ans);
                    ans++;
                    count++;
                }
            }
        }
        System.out.println("have: " + count + " regions\n");
        return mat;
    }

    public static void drawMat(int[][] mat) {
        StdDraw.clear();
        StdDraw.setScale(0, 1);
        for (int y = 0; y < mat.length; y++) {
            for (int x = 0; x < mat[0].length; x++) {
                int v = mat[y][x];
                if (v == 0) {
                    StdDraw.setPenColor(StdDraw.BLACK);
                } else if (v == 1) {
                    StdDraw.setPenColor(StdDraw.BOOK_RED);
                } else if (v == 2) {
                    StdDraw.setPenColor(StdDraw.GREEN);
                } else if (v == 3) {
                    StdDraw.setPenColor(StdDraw.PINK);
                } else if (v == 4) {
                    StdDraw.setPenColor(StdDraw.BOOK_BLUE);
                } else if (v == 5) {
                    StdDraw.setPenColor(StdDraw.ORANGE);
                } else if (v == 6) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                } else if (v == 7) {
                    StdDraw.setPenColor(StdDraw.MAGENTA);
                } else if (v == 8) {
                    StdDraw.setPenColor(StdDraw.CYAN);
                } else if (v == 9) {
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                } else if (v == 10) {
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                } else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                }

                double r = 1.0 / (3* mat.length);
                    double x1 = 0.1 + 2.2 * r * x;
                    double y1 = 1.0 - (0.1 + 2.2 * r * y);
                    StdDraw.filledSquare(x1, y1, r);
                }
            }
        StdDraw.show();
        StdDraw.pause(2);
        }

    }

