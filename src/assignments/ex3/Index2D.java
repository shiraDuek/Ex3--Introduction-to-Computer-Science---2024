package assignments.ex3;

import java.io.Serializable;

import static java.lang.Integer.parseInt;

/**
 * This Index2D class is used to handle two-dimensional points.
 * It offers methods for creating points, accessing their coordinates, calculating distances between points,
 * converting points to strings, and checking if two points are equal.
 * Index2D class implementing Pixel2D interface and Serializable interface.
 * @author Shirs duek
 * @ID 325175115
 */

public class Index2D implements Pixel2D, Serializable{
    private int _x, _y;

    /**
     * Default constructor initializing both x and y to 0.
     */
    public Index2D() {this(0,0);}

    /**
     * Parameterized constructor allowing initialization of x and y with provided values.
     */
    public Index2D(int x, int y) {_x=x;_y=y;}

    /**
     * Constructor initializing x and y with the coordinates of the given Pixel2D object t.
     */
    public Index2D(Pixel2D t) {this(t.getX(), t.getY());}

    /**
     *
     * Constructor that takes a string in the format "x,y" and initializes x and y with the parsed integers from the string.
     * Throws IllegalArgumentException if the string is not in the correct format.
     */
    public Index2D(String pos) {
        try {
            String[] a = pos.split(",");
            _x = parseInt(a[0]);
            _y = parseInt(a[1]);
        }
        catch(IllegalArgumentException e) {
            System.err.println("ERR: got wrong format string for Index2D init, got:"+ pos +"  should be of format: x,y");
            throw(e);
        }
    }

        ////////////////////

    /**
     *
     * Method to get the x-coordinate.
     */
    @Override
    public int getX() {
        return _x;
    }


    /**
     *
     * Method to get the y-coordinate.
     */
    @Override
    public int getY() {
        return _y;
    }

    /**
     *
     * Method to calculate the distance between the current Index2D object and a given Pixel2D object t.
     * @throws RuntimeException if t is null.
     *
     */
    public double distance2D(Pixel2D t) throws RuntimeException {
        if (t==null) {throw new RuntimeException("Pixel2D t is null"); }
        if(this.equals(t)) return 0;
        double dx = this._x - t.getX();
        double dy = this._y - t.getY();
        double d = (dx*dx+dy*dy);
        return Math.sqrt(d);
    }

    /**
     *
     * @return Method to return a string representation of the object in the format "x,y".
     */
    @Override
    public String toString() {
        return this._x+","+this._y;
      }


    /**
     *
     * Method to check if two Index2D objects are equal based on their x and y values.
     *
     */
    @Override
    public boolean equals(Object t) {
        if(t==null || !(t instanceof Index2D)) {return false;}
        Index2D p2 = (Index2D) t;
        return ( (_x==p2._x) && (_y==p2._y));
    }
}
