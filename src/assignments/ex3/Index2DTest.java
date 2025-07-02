package assignments.ex3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Index2DTest {
    private static Index2D p,p1;

    @BeforeEach
    void setUp(){
        p = new Index2D();
        p1 = new Index2D(3,4);
    }

    @Test
    void getX() {
        int t = p.getX();
        assertEquals(t,0);

        int t1 = p1.getX();
        assertEquals(t1,3);
    }

    @Test
    void getY() {
        int t = p.getY();
        assertEquals(t,0);

        int t1 = p1.getY();
        assertEquals(t1,4);
    }


    @Test
    void distance2D() {
        assertEquals(5,p.distance2D(p1));
        assertEquals(0,p1.distance2D(p1));
        assertThrows(RuntimeException.class, () -> p.distance2D(null));
        assertThrows(RuntimeException.class, () -> p1.distance2D(null));

    }

    @Test
    void testToString() {
        String s1 = p1.toString();
        Index2D gs1 = new Index2D(s1);
        assertEquals(p1, gs1);

        String a1 = p1.toString();
        String a  = "3,4";
        assertEquals(a,a1);

        String a2 = p1.toString();
        String a3 = "3, 4";
        assertNotEquals(a2,a3);


    }

    @Test
    void testEquals() {
        assertEquals(p1,  new Index2D(3,4));
        assertNotEquals(p1, new Index2D(3,5));
        assertNotEquals(p1, new Index2D(4,4));
        assertNotEquals(p1,p);
    }
}