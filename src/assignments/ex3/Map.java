package assignments.ex3;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a 2D map as a "screen" or a raster matrix or maze over integers.
 * @author boaz.benmoshe
 *
 *
 * The Map class is designed to handle two-dimensional maps, where each index stores a positive numerical value.
 * Its primary features include efficient map management, enabling operations like reading values and modifying cells.
 * By adhering to the guidelines of the Map2D interface, it ensures consistent methods for manipulating maps and retrieving their size.
 * Leveraging map traversal and cell analysis algorithms, the class can navigate paths and extract pertinent data from the map.
 * Its key capabilities encompass pathfinding and map analysis, providing functions such as identifying optimal routes,
 * learning obstacle distributions, and modifying map structures and values. With a focus on efficiency, flexibility,
 * and performance, the class excels in managing various map-related tasks.
 * @author Shirs duek
 * @ID 325175115
 *
 */
public class Map implements Map2D, Serializable {
	private int[][] _map;

	private boolean _cyclicFlag = true;

	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 *
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
		init(w, h, v);
	}

	/**
	 * Constructs a square map (size*size).
	 *
	 * @param size
	 */
	public Map(int size) {
		this(size, size, 0);
	}

	/**
	 * Constructs a map from a given 2D array.
	 *
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

	/**
	 * Construct a 2D w*h matrix of integers.
	 *
	 * @param w the width of the underlying 2D array.
	 * @param h the height of the underlying 2D array.
	 * @param v the init value of all the entries in the 2D array.
	 */
	@Override
	public void init(int w, int h, int v) {
		_map = new int[w][h];
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				_map[i][j] = v;
			}
		}


		////////////////////
	}

	/**
	 * Constructs a 2D raster map from a given 2D int array (deep copy).
	 *
	 * @param arr a 2D int array.
	 * @throws RuntimeException if arr == null or if the array is empty or a ragged 2D array.
	 */

	@Override
	public void init(int[][] arr) throws RuntimeException {

		if (arr == null) throw new RuntimeException("array is null");


		boolean isEmpty = true;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length != 0) isEmpty = false;
		}
		if (isEmpty) throw new RuntimeException("array is empty");


		int rowLength = arr[0].length;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].length != rowLength) throw new RuntimeException("array is ragged");
		}


		int Width = arr.length;
		int Height = arr[0].length;

		_map = new int[Width][Height];

		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				_map[i][j] = arr[i][j];
			}
		}
		////////////////////
	}

	/**
	 * Computes a deep copy of the underline 2D matrix.
	 *
	 * @return a deep copy of the underline matrix.
	 */
	@Override
	public int[][] getMap() {

		int Width = _map.length;
		int Height = _map[0].length;

		int[][] ans = new int[Width][Height];

		for (int i = 0; i < Width; i++) {
			for (int j = 0; j < Height; j++) {
				ans[i][j] = _map[i][j];
			}
		}
		return ans;
	}

	/**
	 * Compares this Map object with another object for equality.
	 *
	 * @param ob the object to be compared
	 * @return true if the two objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object ob) {
		if (this == ob) return true;
		if (ob == null || this.getClass() != ob.getClass()) return false;

		Map map1 = (Map) ob;

		if (map1.getHeight() != this.getHeight() || map1.getWidth() != this.getWidth()) return false;
		if (map1.isCyclic() != this.isCyclic()) return false;

		for (int i = 0; i < this.getWidth(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				if (map1.getMap()[i][j] != this.getMap()[i][j]) return false;
			}
		}
		return true;
	}

	/**
	 * The width of the underlying 2D matrix.
	 *
	 * @return the width of this 2D map (first coordinate)
	 */
	@Override
	public int getWidth() {
		return _map.length;
	}

	/**
	 * The height of the underlying 2D matrix.
	 *
	 * @return the height of this 2D map (second coordinate).
	 */
	@Override
	public int getHeight() {
		return _map[0].length;
	}

	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the [x][y] (int) value of the map[x][y].
	 * @throws RuntimeException
	 */
	@Override
	public int getPixel(int x, int y) throws RuntimeException {
		if (!isInside(x, y)) {
			throw new RuntimeException("Pixel2D is outside");
		}
		return _map[x][y];
	}

	/**
	 * @param p the x,y coordinate
	 * @return the [p.x][p.y] (int) value of the map.
	 */
	@Override
	public int getPixel(Pixel2D p) {
		return this.getPixel(p.getX(), p.getY());
	}

	/**
	 * Set the [x][y] coordinate of the map to v.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param v the value that the entry at the coordinate [x][y] is set to.
	 * @throws RuntimeException if the coordinates are outside the bounds of the matrix or if the value v is negative.
	 */
	@Override
	public void setPixel(int x, int y, int v) throws RuntimeException {
		if (!isInside(x, y)) {
			throw new RuntimeException("Pixel2D is outside");
		}
		if (v < 0) {
			throw new RuntimeException("v need to be positive");
		}
		_map[x][y] = v;
	}

	/**
	 * Set the [x][y] coordinate of the map to v.
	 *
	 * @param p the coordinate in the map.
	 * @param v the value that the entry at the coordinate [p.x][p.y] is set to.
	 */
	@Override
	public void setPixel(Pixel2D p, int v) {
		setPixel(p.getX(), p.getY(), v);
	}


	/**
	 * Fill the connected component of p in the new color (new_v).
	 * Uses flood fill algorithm.
	 * Reference: https://en.wikipedia.org/wiki/Flood_fill
	 *
	 * @param xy    The starting Pixel2D point for the flood fill.
	 * @param new_v The new color value to fill the map with.
	 * @return The number of pixels filled with the new color.
	 * @throws RuntimeException if the starting Pixel2D point is outside the map boundaries or if new_v is negative.
	 */
	@Override
	public int fill(Pixel2D xy, int new_v) throws RuntimeException {
		if (!isInside(xy)) {
			throw new RuntimeException("Pixel2D is outside");
		}
		if (new_v < 0) {
			throw new RuntimeException("new_v need to be positive");
		}
		int ans = 0;

		// Perform flood fill and get the list of filled points
		List<Pixel2D> points = fillTheAllRelevantNeighbors(xy, new_v);

		return points.size();
	}


	/**
	 * Computes the distance of the shortest path (minimal number of consecutive neighbors) from p1 to p2.
	 * Note: the distance is computed using the shortest path and returns its length - 1, as the distance from a point
	 * to itself is 0, while the path contains a single point.
	 *
	 * @param p1       The starting Pixel2D point.
	 * @param p2       The destination Pixel2D point.
	 * @param obsColor The color value representing obstacles.
	 * @return The distance of the shortest path from p1 to p2.
	 * @throws RuntimeException if either p1 or p2 is outside the map boundaries or if obsColor is negative.
	 */
	public int shortestPathDist(Pixel2D p1, Pixel2D p2, int obsColor) throws RuntimeException {
		// Check if both p1 and p2 are inside the map boundaries
		if (!isInside(p1) || !isInside(p2)) {
			throw new RuntimeException("Pixel2D is outside");
		}
		// Check if obsColor is positive
		if (obsColor < 0) {
			throw new RuntimeException("obsColor need to be positive");
		}

		// Initialize the distance as -1
		int ans = -1;

		// Find the shortest path between p1 and p2
		Pixel2D[] path = shortestPath(p1, p2, obsColor);
		// If there is a path, compute its distance
		if (path != null) {
			ans = path.length - 1;
		}
		// Return the computed distance
		return ans;
	}

	@Override
	/**
	 * Compute the shortest valid path between p1 and p2.
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 * @param p1 The starting Pixel2D point.
	 * @param p2 The destination Pixel2D point.
	 * @param obsColor The color value representing obstacles.
	 * @return An array of Pixel2D objects representing the shortest path from p1 to p2.
	 * @throws RuntimeException if either p1 or p2 is outside the map boundaries, if obsColor is negative,
	 *         if p1 or p2 are painted with obsColor, or if there is no path from p1 to p2.
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor) throws RuntimeException {

		if (!isInside(p1) || !isInside(p2)) {
			throw new RuntimeException("Pixel2D is outside");
		}

		if (obsColor < 0) {
			throw new RuntimeException("obsColor need to be positive");
		}

		if (p1.equals(p2)) return new Pixel2D[]{p1};


		if (getPixel(p1) == obsColor) throw new RuntimeException("p1 is painted in obsColor");
		if (getPixel(p2) == obsColor) throw new RuntimeException("p2 is painted in obsColor");


		int[][] map = this.buildMap(p1, obsColor);


		if (map[p2.getX()][p2.getY()] == -1) {
			throw new RuntimeException("there is no path to p2");
		}


		// Initialize variables to track the minimum point and its distance
		Pixel2D min = p2;
		int distance = map[p2.getX()][p2.getY()];
		// Initialize an array to store the shortest path
		Pixel2D[] path = new Pixel2D[distance + 1];
		path[path.length - 1] = p2;

		// Iterate over the distance to find the shortest path
		for (int i = 1; i < distance + 1; i++) {
			// Find neighbors of the current minimum point
			Index2D[] neighbors = findingTheSingleNeighborsCycilc(min);

			// Find the neighbor with the minimum distance in the map
			for (int j = 0; j < neighbors.length; j++) {
				// Check if the distance to the current neighbor is less than the minimum distance so far,
				// and if the distance is greater than or equal to 0 (meaning it's a valid distance)
				if (map[neighbors[j].getX()][neighbors[j].getY()] < map[min.getX()][min.getY()]
						&& map[neighbors[j].getX()][neighbors[j].getY()] >= 0)  {

					// If conditions are met, update the minimum point to the current neighbor
					min = neighbors[j];
					break;
				}

			}
			// Add the minimum point to the path
			path[path.length - 1 - i] = min;
		}

		// Return the shortest path
		return path;
	}

	/**
	 * Finds the shortest path between a series of points, treating the points as consecutive segments.
	 *
	 * @param points   An array of Pixel2D points representing a series of points.
	 * @param obsColor The color value representing obstacles.
	 * @return An array of Pixel2D objects representing the shortest path between the series of points.
	 * @throws RuntimeException if any of the points are outside the map boundaries or if obsColor is negative.
	 */
	@Override
	public Pixel2D[] shortestPath(Pixel2D[] points, int obsColor) throws RuntimeException {
		// Check if all points are inside the map boundaries
		for (int i = 0; i < points.length; i++) {
			if (!isInside(points[i])) {
				throw new RuntimeException("Pixel2D is outside");
			}
		}
		// Check if obsColor is positive
		if (obsColor < 0) {
			throw new RuntimeException("obsColor need to be positive");
		}
		// Initialize an empty array to store the shortest path
		Pixel2D[] ans = new Pixel2D[0];

		// Initialize an empty array to store the current segment path
		Pixel2D[] path = new Pixel2D[0];

		// Initialize a variable to track the index to start appending points to 'ans'
		int between = 0;

		// Iterate over consecutive pairs of points and find the shortest path between them
		for (int i = 0; i < points.length - 1; i++) {
			// Find the shortest path between the current point and the next point
			path = shortestPath(points[i], points[i + 1], obsColor);


			// Append the points from the current segment path to the 'ans' array
			for (int j = between; j < path.length; j++) {
				ans = Arrays.copyOf(ans, ans.length + 1);
				ans[ans.length - 1] = path[j];

			}
			// Update the 'between' variable to start appending points from the next segment.
			between = 1;
		}

		// Return the shortest path between all points
		return ans;
	}

	/**
	 * Checks if a given Pixel2D point is inside the map boundaries.
	 *
	 * @param p The 2D coordinate.
	 * @return True if the point is inside the map boundaries, otherwise false.
	 */
	@Override
	public boolean isInside(Pixel2D p) {
		return isInside(p.getX(), p.getY());
	}

	/**
	 * Checks if the map is cyclic (wraps around at the edges).
	 *
	 * @return True if the map is cyclic, otherwise false.
	 */
	@Override
	public boolean isCyclic() {
		return _cyclicFlag;
	}

	/**
	 * Sets the cyclic flag for the map.
	 *
	 * @param cy The value of the cyclic flag.
	 */
	@Override
	public void setCyclic(boolean cy) {
		_cyclicFlag = cy;
	}

	/**
	 * Checks if a given 2D coordinate (x, y) is inside the map boundaries.
	 *
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @return True if the coordinates are inside the map boundaries, otherwise false.
	 */
	private boolean isInside(int x, int y) {
		return x >= 0 && y >= 0 && x < this.getWidth() && y < this.getHeight();
	}

	/**
	 * Computes the distance of all points in the map from a given source (starting) point.
	 *
	 * @param start    The source (starting) point.
	 * @param obsColor The color representing obstacles.
	 * @return A Map2D object containing the distances of all points from the source point.
	 * @throws RuntimeException if the starting point is outside the map boundaries or if obsColor is negative,
	 *                          or if the starting point is colored with obsColor.
	 */
	@Override
	public Map2D allDistance(Pixel2D start, int obsColor) throws RuntimeException {

		if (!isInside(start)) {
			throw new RuntimeException("Pixel2D is outside");
		}

		if (obsColor < 0) {
			throw new RuntimeException("obsColor need to be positive");
		}

		if (getPixel(start) == obsColor) throw new RuntimeException("start is colored with obsColor");


		Map2D ans = new Map(buildMap(start, obsColor));
		ans.setCyclic(this.isCyclic());

		return ans;
	}

	/**
	 * This method computes the number of Connected Components in the map.
	 * The obsColor is addressed as an obstacle, all other colors are assumed as valid (aka connected).
	 *
	 * @param obsColor The color value representing obstacles.
	 * @return The number of connected components in the map.
	 * @throws RuntimeException if obsColor is negative.
	 */
	@Override
	public int numberOfConnectedComponents(int obsColor) throws RuntimeException {

		if (obsColor < 0) {
			throw new RuntimeException("obsColor need to be positive");
		}

		int ans = 0;


		Map2D copyMap = new Map(this.getMap());
		copyMap.setCyclic(this.isCyclic());


		int marker = 1000;


		for (int i = 0; i < copyMap.getWidth(); i++) {
			for (int j = 0; j < copyMap.getHeight(); j++) {


				if (copyMap.getPixel(i, j) != marker && copyMap.getPixel(i, j) != obsColor) {


					copyMap.fill(new Index2D(i, j), marker);


					ans++;
				}
			}
		}

		return ans;
	}


	////////////////////// Private Methods ///////////////////////

	/**
	 * Builds a map of distances from a given starting point to all other points on the map.
	 * The algorithm is a variation of Breadth-First Search (BFS).
	 * BFS in wikipedia - https://en.wikipedia.org/wiki/Breadth-first_search
	 *
	 * @param point    The starting point.
	 * @param obsColor The color to be treated as obstacles.
	 * @return An array representing the map of distances from the starting point.
	 */
	public int[][] buildMap(Pixel2D point, int obsColor) {
		// Create a copy of the map to store distances
		int[][] mapCopy = new int[getWidth()][getHeight()];
		for (int i = 0; i < getWidth(); i++) {
			Arrays.fill(mapCopy[i], -1);
		}

		// Set the distance of the starting point to 0
		mapCopy[point.getX()][point.getY()] = 0;

		// Create a queue to store all neighbors of the starting point
		Queue<Pixel2D> allNeighbors = new LinkedList<>();
		allNeighbors.add(point); // Add the starting point to the queue

		// Initialize count to track the distance
		int count = 1;

		// Iterate until all points are processed
		while (!allNeighbors.isEmpty()) {
			int size = allNeighbors.size(); // Get the current size of the queue
			for (int i = 0; i < size; i++) {
				Pixel2D current = allNeighbors.poll();
				// Find valid neighbors of the current point
				Pixel2D[] neighbors = findingTheSingleNeighborsPath(current, obsColor);

				// Add unvisited valid neighbors to the queue and update their distance in the map
				for (int j = 0; j < neighbors.length; j++) {
					int x = neighbors[j].getX();
					int y = neighbors[j].getY();

					// Check if the neighbor is not already visited
					if (mapCopy[x][y] == -1) {
						// Update the distance of the neighbor in the map
						mapCopy[x][y] = count;

						// Add the neighbor to the queue for further processing
						allNeighbors.add(neighbors[j]);
					}
				}
			}
			// Increment distance counter
			count++;
		}

		return mapCopy;
	}

	/**
	 * Finds neighboring pixels of a given Pixel2D object with specified colors,
	 * returning an array of Index2D objects representing the neighboring pixels
	 * with colors different from the specified obstacle color.
	 *
	 * @param point    The Pixel2D object for which neighbors need to be found.
	 * @param obsColor The obstacle color that should be excluded from the neighbors.
	 * @return An array of Index2D objects representing neighboring pixels with
	 * colors different from the specified obstacle color.
	 */
	public Index2D[] findingTheSingleNeighborsPath(Pixel2D point, int obsColor) {


		Index2D[] cyclicNeighbors = findingTheSingleNeighborsCycilc(point);


		Index2D[] ans = new Index2D[0];


		for (int i = 0; i < cyclicNeighbors.length; i++) {


			if (obsColor != this.getPixel(cyclicNeighbors[i])) {


				ans = Arrays.copyOf(ans, ans.length + 1);
				ans[ans.length - 1] = new Index2D(cyclicNeighbors[i]);
			}
		}
		return ans;

	}

	/**
	 * This method finds neighboring pixels of a given Pixel2D object with specified colors,
	 * returning an array of Index2D objects representing the neighboring pixels with matching colors.
	 * @param point The Pixel2D object for which neighbors need to be found.
	 * @param color An integer value representing the color to match.
	 * @return An array of Index2D objects representing neighboring pixels with matching colors.
	 */

	public Index2D[] findingTheSingleNeighborsFill(Pixel2D point, int color) {

		Index2D[] cyclicNeighbors = findingTheSingleNeighborsCycilc(point);


		Index2D[] ans = new Index2D[0];

		for (int i = 0; i < cyclicNeighbors.length; i++) {

			boolean flag = false;

			if (color == this.getPixel(cyclicNeighbors[i])) flag = true;

			if (flag) {
				ans = Arrays.copyOf(ans, ans.length + 1);
				ans[ans.length - 1] = new Index2D(cyclicNeighbors[i]);
			}
		}

		return ans;
	}

	/**
	 * This method finds the neighboring pixels of a given Pixel2D object in a cyclic manner if enabled,
	 * returning an array of Index2D objects representing the neighboring pixels.
	 *
	 * @param point The Pixel2D object for which neighbors need to be found.
	 * @return An array of Index2D objects representing neighboring pixels.
	 */
	public Index2D[] findingTheSingleNeighborsCycilc(Pixel2D point) {
		int x = point.getX();
		int y = point.getY();

		Index2D[] pointNewX;
		if (x == 0) {
			if (isCyclic()) pointNewX = new Index2D[]{new Index2D(getWidth() - 1, y), new Index2D(1, y)};
			else pointNewX = new Index2D[]{new Index2D(1, y)};
		}
		else if (x == getWidth() - 1) {
			if (isCyclic()) pointNewX = new Index2D[]{new Index2D(x - 1, y), new Index2D(0, y)};
			else pointNewX = new Index2D[]{new Index2D(x - 1, y)};

		}
		else {
			pointNewX = new Index2D[]{new Index2D(x - 1, y), new Index2D(x + 1, y)};
		}

		Index2D[] pointNewY;

		if (y == 0) {

			if (isCyclic()) pointNewY = new Index2D[]{new Index2D(x, getHeight() - 1), new Index2D(x, 1)};
			else pointNewY = new Index2D[]{new Index2D(x, 1)};
		}

		else if (y == getHeight() - 1) {

			if (isCyclic()) pointNewY = new Index2D[]{new Index2D(x, y - 1), new Index2D(x, 0)};

			else pointNewY = new Index2D[]{new Index2D(x, y - 1)};

		}
		else pointNewY = new Index2D[]{new Index2D(x, y - 1), new Index2D(x, y + 1)};

		Index2D[] allPoints = new Index2D[0];

		for (int i = 0; i < pointNewX.length; i++) {
			allPoints = Arrays.copyOf(allPoints, allPoints.length + 1);
			allPoints[allPoints.length - 1] = new Index2D(pointNewX[i]);
		}

		for (int i = 0; i < pointNewY.length; i++) {
			allPoints = Arrays.copyOf(allPoints, allPoints.length + 1);
			allPoints[allPoints.length - 1] = new Index2D(pointNewY[i]);
		}

		return allPoints;
	}

	/**
	 * This method fills all relevant neighboring pixels of a given Pixel2D object with the specified new color,
	 * and returns a list of Index2D objects representing all filled pixels.
	 * Uses flood fill algorithm.
	 * Reference: https://en.wikipedia.org/wiki/Flood_fill
	 * @param point    The Pixel2D object representing the starting point for the fill operation.
	 * @param newColor The new color value to fill the pixels with.
	 * @return A list of Index2D objects representing all filled pixels.
	 */
	public List<Pixel2D> fillTheAllRelevantNeighbors(Pixel2D point, int newColor) {
		// Initialize a list to store all points that were filled.
		List<Pixel2D> allPoints = new ArrayList<>();

		// Retrieve the original color of the starting point.
		int originalColor = _map[point.getX()][point.getY()];


		// Create a stack to perform the fill operation.
		Stack<Pixel2D> stack = new Stack<>();
		stack.add(point); // Add the starting point to the stack.


		// Fill the starting point with the new color and add it to the list of filled points.
		_map[point.getX()][point.getY()] = newColor;
		allPoints.add(point);


		// Continue filling neighboring points until the stack is empty.
		while (!stack.isEmpty()) {
			Pixel2D currentPoint = stack.pop(); // Retrieve the current point from the stack.

			// Find neighboring points of the current point with the original color.
			Index2D[] neighbors = findingTheSingleNeighborsFill(currentPoint, originalColor);

			// Fill all neighboring points with the new color and add them to the stack and list of filled points.
			for (int i = 0; i < neighbors.length; i++) {
				Index2D neighbor = neighbors[i]; // Retrieve the current neighbor.

				// Fill the neighbor with the new color.
				_map[neighbor.getX()][neighbor.getY()] = newColor;
				// Add the neighbor to the list of filled points.
				allPoints.add(neighbor);

				// Add the neighbor to the stack to check its neighbors later.
				stack.add(neighbor);

			}
		}

		return allPoints; // Return the list of all points that were filled.
	}
}
