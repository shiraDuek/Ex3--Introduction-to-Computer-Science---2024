package assignments.ex3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
This is a very basic Testing class for Map - please note that this JUnit
 contains only a very limited testing method and should be added many other
 methods for testing all the functionality of Map2D - both in correctness and in runtime.
*/
 class MapTest {
    /**
     * _m_3_3 =
     * 0,1,0
     * 1,0,1
     * 0,1,0
     *
     * _m0 =
     * 1,1,1,1,1
     * 1,0,1,0,1
     * 1,0,0,0,1
     * 1,0,1,0,1
     * 1,1,1,1,1
     * 1,0,1,0,1
     *
     * 1, 1, 1, 1, 1
     * 1,-1, 1,-1, 1
     * 1,-1,-1,-1, 1
     * 1,-1, 1,-1, 1
     * 1, 1, 1, 1, 1
     * 1,-1, 1,-1, 1
     *
     * m2[3][2] = 0, m2[1][2] = 10, |sp|=11 (isCiclic = false;}
     * =============
     * 7, 8, 9, 1, 7
     * 6,-1,10,-1, 6
     * 5,-1,-1,-1, 5
     * 4,-1, 0,-1, 4
     * 3, 2, 1, 2, 3
     * 4,-1, 2,-1, 4
     *
     * m[3][2] = 0, m2[1][2] = 5, |sp|=5 (isCiclic = true;}
     * 5, 4, 3, 4, 5
     * 6,-1, 4,-1, 6
     * 5,-1,-1,-1, 5
     * 4,-1, 0,-1, 4
     * 3, 2, 1, 2, 3
     * 4,-1, 2,-1, 4
     */
    private int[][] _map = {{1,1,1,1,1}, {1,0,1,0,1}, {1,0,0,0,1},  {1,0,1,0,1},  {1,1,1,1,1}, {1,0,1,0,1}};
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private int[][] myMap = {{7,6,2,4,9},{6,3,4,9,6},{2,4,9,7,6},{6,9,5,8,6}};
    private Map2D _m0, _m1, _m2, _m3, _m3_3,myMap2;
    @BeforeEach
    public void setuo() {
        _m0 = new Map(_map);
        _m1 = new Map(_map); _m1.setCyclic(true);
        _m2 = new Map(_map); _m2.setCyclic(false);
        _m3 = new Map(_map);
        _m3_3 = new Map(_map_3_3);
        myMap2 = new Map(myMap); myMap2.setCyclic(false);


    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr); // Initializing the _m1 object with the bigarr array
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());

        int[][] veryBig = new int[1000][1000];
        _m3.init(veryBig);
        assertEquals(veryBig.length, _m3.getWidth());
        assertEquals(veryBig[0].length, _m3.getHeight());

        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1);

        Pixel2D p2 = new Index2D(342,242);
        _m3.fill(p2,1);


        int[][] mapNull = new int[][]{};
        Map2D mapNull1 = null;
        assertThrows(RuntimeException.class, () -> mapNull1.init(mapNull));

        int[][] _map_3 = {{0,1,0}, {1,0,1}, {0,1}};
        assertThrows(RuntimeException.class, () -> _m3.init(_map_3));

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testEquals() {
        assertEquals(_m0,_m1);
        assertEquals(_m0,_m3);
        assertNotEquals(_m1,_m2);
        _m3.setPixel(2,2,17);
        assertNotEquals(_m0,_m3);
        assertNotEquals(_map_3_3,_m3);

        Map2D _m3_4 = new Map(_map_3_3); _m3_4.setCyclic(false);
        assertNotEquals(_map_3_3,_m3_4);

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void getMap() {
        int[][] m0 = _m0.getMap();
        _m1.init(m0);
        assertEquals(_m0,_m1);

        int[][] m3 = _m3.getMap();
        _m1.init(m3);
        assertEquals(_m3,_m1);

        int[][] m2 = _m2.getMap();
        _m1.init(m2);
        assertNotEquals(_m2,_m1);//failed in Cyclic
    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void getWidth() {
        assertEquals(6,_m1.getWidth());
        Map2D _3 = new Map(_map_3_3);
        assertEquals(3,_3.getWidth());
    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void getHeight() {
        assertEquals(5,_m1.getHeight());
        Map2D _3 = new Map(_map_3_3);
        assertEquals(3,_3.getHeight());
    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void getPixel() {
        assertEquals(0,_m3.getPixel(2,1));
        Map2D _3 = new Map(_map_3_3);
        assertEquals(0,_3.getPixel(2,2));
        assertThrows(RuntimeException.class, () -> _m3.getPixel(7,9));

    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void setPixel() {
        _m3.setPixel(2,1,3);
       assertEquals(3, _m3.getPixel(2,1));
        Map2D _3 = new Map(_map_3_3);
        assertThrows(RuntimeException.class, () -> _3.setPixel(6,7,2));
    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testfindSingleNeighbourPath() {
        Pixel2D[] should = {new Index2D(2, 1)};
        Pixel2D point = new Index2D(1, 1);
        Map meMap = new Map(_map);
        Pixel2D[] res = meMap.findingTheSingleNeighborsPath(point, 1);
        assertArrayEquals(should, res);

    }

        @Test
    @Timeout(value = 1, unit = SECONDS)
    void testfindSingleNeighbourFill() {
        Pixel2D[] should = {new Index2D(5,0), new Index2D(1,0), new Index2D(0,4), new Index2D(0,1)};
        Pixel2D point = new Index2D(0, 0);
        Map meMap = new Map(_map);
        Pixel2D[] res = meMap.findingTheSingleNeighborsFill(point,1);
        assertArrayEquals(should,res);

        Index2D[] should2 = {new Index2D(0,0), new Index2D(0,2),};
        Index2D point2 = new Index2D(0,1);
        Index2D[] res2 =meMap.findingTheSingleNeighborsFill(point2,1);
        assertArrayEquals(should2,res2);


        Index2D[] should3 = {};
        Pixel2D point3 = new Index2D(5, 1);
        Index2D[] res3 =meMap.findingTheSingleNeighborsFill(point3,0);
        assertArrayEquals(should3,res3);

        Map meMap1 = new Map(myMap);
        Index2D point4  = new Index2D(2,3);
        Index2D[] res4 = meMap1.findingTheSingleNeighborsFill(point4,9);
        Pixel2D[] should4 = { new Index2D(1,3),new Index2D(2,2)};
        assertArrayEquals(should4,res4);

        Index2D[] res5 = meMap1.findingTheSingleNeighborsFill(point4,2);
        Pixel2D[] should5 = {};
        assertArrayEquals(should5,res5);

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testBuildMap() {
        Map meMap = (Map) _m0;
        Pixel2D point = new Index2D(0, 0);
        int[][] copy = meMap.buildMap(point,0);
        int[][] should = {{0,1,2,2,1},{1,-1,3,-1,2},{2,-1,-1,-1,3},{3,-1,5,-1,4},{2,3,4,4,3},{1,-1,3,-1,2}};
        assertArrayEquals(should,copy);

        int[][] arr = new int[10][10];
        Map map = new Map(arr); map.setCyclic(false);
        map.fill(point,3);
        int[][] copy1 = map.buildMap(point,2);
        Map cop = new Map(copy1);
        int v = cop.getPixel(9,9);
        assertEquals(18,v);


        //for(int[] row : copy) System.out.println(Arrays.toString(row));

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillTheAllRelevantNeighbors() {
        Map meMap1 = new Map(myMap); meMap1.setCyclic(false);
        Index2D point  = new Index2D(2,4);
        List<Index2D> should = new ArrayList<>();
        should.add(new Index2D(2,4));
        should.add(new Index2D(1, 4));
        should.add(new Index2D(3, 4));

        List<Pixel2D> allPoints = meMap1.fillTheAllRelevantNeighbors(point,3);
        Assertions.assertArrayEquals(should.toArray(),allPoints.toArray());

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFill0() {
        Pixel2D p1 = new Index2D(0,0);
        int f0 = _m0.fill(p1,2);
        assertEquals(f0,21);

        int[][] veryBig = new int[400][400];
        Map2D copyMap = new Map(veryBig);
        for (int i = 150 ; i < 200; i++) {
            for (int j = 0; j < copyMap.getHeight(); j++) {
                copyMap.setPixel(i,j,2);
            }
        }

        assertEquals(140000,copyMap.fill(new Index2D(0,0),1));

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFill1() {
        Pixel2D p1 = new Index2D(0,1);
        _m0.setPixel(p1,0);
        int f0 = _m0.fill(p1,2);
        assertEquals(f0,9);

        _m0.setCyclic(false);
        int f2 = _m0.fill(p1,3);
        assertEquals(f2,8);

        Pixel2D p2 = new Index2D(3,4);
        Map2D myMap1 = new Map(myMap);
        myMap1.setPixel(p2,6);
        int f1 = myMap1.fill(p2,7);
        assertEquals(f1,5);

        Map2D myMap2 = new Map(myMap);myMap2.setCyclic(false);
        myMap2.setPixel(p2,6);
        int f3 = myMap2.fill(p2,7);
        assertEquals(f3,3);

        Pixel2D p3 = new Index2D(21,4);
        assertThrows(RuntimeException.class, () -> _m0.fill(p3,2) );
        assertThrows(RuntimeException.class, () -> _m0.fill(p1,-5) );

        Pixel2D p5 = new Index2D(3,4);
        Map2D myMap3 = new Map(myMap);
        myMap3.setPixel(p2,8);
        int f5 = myMap3.fill(p5,7);
        assertEquals(2,f5);

        int[][] veryBig = new int[700][700];
        Map2D copyMap = new Map(veryBig); copyMap.setCyclic(false);
        for (int i = 150 ; i < 200; i++) {
            for (int j = 0; j < copyMap.getHeight(); j++) {
                copyMap.setPixel(i,j,2);
            }
        }

        assertEquals(105000,copyMap.fill(new Index2D(0,0),1));


    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPathDist(){
        Pixel2D p1 = new Index2D(3,2);
        Pixel2D p2 = new Index2D(1,2);
        Pixel2D p3 = new Index2D(0,0);
        Pixel2D p4 = new Index2D(20,0);
        Map meMap = (Map) _m0;
        Map meMap1 = (Map) _m2;
        int result = meMap.shortestPathDist(p1, p2, 0);

        assertEquals(4, result);
        result = meMap1.shortestPathDist(p3, p1, 0);
        assertEquals(7, result);


        assertThrows(RuntimeException.class, () -> meMap1.shortestPathDist(p3, p1, -5) );
        assertThrows(RuntimeException.class, () -> meMap1.shortestPathDist(p3, p4, 0) );

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testAllDistance() {
        Pixel2D p1 = new Index2D(3,2);
        Pixel2D p2 = new Index2D(1,0);
        Pixel2D p3 = new Index2D(4,4);
        Map2D m00 = _m0.allDistance(p1, 0);
        assertEquals(6, m00.getPixel(p2));
        assertEquals(3, m00.getPixel(p3));

        Map meMap = (Map) _m0;
        Pixel2D point = new Index2D(0, 0);
        Map2D copy = meMap.allDistance(point,0);
        int[][] should = {{0,1,2,2,1},{1,-1,3,-1,2},{2,-1,-1,-1,3},{3,-1,5,-1,4},{2,3,4,4,3},{1,-1,3,-1,2}};
        Map2D should1 = new Map( should);
        assertArrayEquals(should1.getMap(),copy.getMap());

        Map2D big = new Map(new int[600][600]);
        int[][] big1 = new int[600][600];
        Map newBig1 = new Map(big1);
        big.setPixel( new Index2D(253,198),2);
        int f0 = big.fill(new Index2D(0,0),1);
        assertEquals(359999,f0);


        newBig1.setPixel( new Index2D(253,198),2);

        int f1 = newBig1.fill(new Index2D(0,0),1);
        assertEquals(359999,f1);

        Map2D newBig = big.allDistance(new Index2D(0,0),2);
        int[][] newBig2 = newBig1.buildMap(new Index2D(0,0),2);
        Map newBig3 = new Map(newBig2);
        assertArrayEquals(newBig.getMap(),newBig3.getMap());

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testShortestPath() {
        Pixel2D p1 = new Index2D(3,2);
        Pixel2D p2 = new Index2D(1,2);
        Pixel2D[] path = _m0.shortestPath(p1, p2, 0);
        assertEquals(5, path.length);

        path = _m2.shortestPath(p1, p2, 0);
        assertEquals(11, path.length);


        Pixel2D[] path1 = _m0.shortestPath(p1, p1, 0);
        for (int i = 0; i <path1.length ; i++) {
            assertEquals(p1,path1[i]);
        }

        Pixel2D p5 = new Index2D(1,1);
        assertThrows(RuntimeException.class, () -> _m0.shortestPath(p1, p5, 0));

        path = myMap2.shortestPath(p5, p1, 6);
        assertEquals(4, path.length);

        int[][] testmat = {{1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}, {0, 0, 0, 0, 0}, {1, 1, 0, 1, 1}, {1, 1, 0, 1, 1}};
        Map m1= new Map(testmat);
        m1.setCyclic(false);
        Pixel2D p7 = new Index2D(0,0);
        Pixel2D p8 = new Index2D(4,4);
        assertThrows(RuntimeException.class, () -> m1.shortestPath(p7, p8, 0));

        assertThrows(RuntimeException.class, () -> _m0.shortestPath(p1, new Index2D(100,20), 0));
        assertThrows(RuntimeException.class, () -> _m2.shortestPath(p1, p2, -5));

        Pixel2D p9 = new Index2D(2,1);

        assertThrows(RuntimeException.class, () -> _m2.shortestPath(p7, p9, 0));

        _m2.setPixel(p2,3);

        assertThrows(RuntimeException.class, () -> _m2.shortestPath(p7, p2, 3));

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void ShortestPath() {
        Pixel2D p1 = new Index2D(3,2);
        Pixel2D p2 = new Index2D(1,2);
        Pixel2D p3 = new Index2D(0,0);
        Pixel2D p4 = new Index2D(5,4);

        Pixel2D[] point = {p3,p2,p4,p1};
        Pixel2D[] point1 = {p3,p2,p4,p4,p1};
        Pixel2D[] point2 = {p3,p2,p4,p1,p3};

        Pixel2D[] path = _m0.shortestPath(point, 0);
        Pixel2D[] path1 = _m0.shortestPath(point1, 0);
        Pixel2D[] path2 = _m0.shortestPath(point2, 0);

        assertEquals(12, path.length);
        assertEquals(12, path1.length);
        assertEquals(17, path2.length);

        int[][] map = new int[100][100];
        Map2D copyMap = new Map(map); copyMap.setCyclic(false);
        copyMap.fill(p3,3);
        Pixel2D p5 = new Index2D(0,99);
        Pixel2D p6 = new Index2D(99,99);
        Pixel2D p7 = new Index2D(99,0);
        Pixel2D[] point3 = {p3,p5,p6,p7};
        Pixel2D[] path3 = copyMap.shortestPath(point3, 0);
        assertEquals(298, path3.length);
        copyMap.setCyclic(true);
        Pixel2D[] path4 = copyMap.shortestPath(point3, 0);
        assertEquals(4, path4.length);

    }


    @Test
    @Timeout(value = 1, unit = SECONDS)
    void numberOfConnectedComponents(){
        assertEquals(1,_m0.numberOfConnectedComponents(0));
        assertEquals(3,_m0.numberOfConnectedComponents(1));
        Map meMap = new Map(_map_3_3);
        assertEquals(2,meMap.numberOfConnectedComponents(0));
        meMap.setCyclic(false);

        assertEquals(4,meMap.numberOfConnectedComponents(0));
    }
}