package com.bedulin.dots.temp;

import java.util.ArrayList;

/**
 * Holds a Node[][] 2d array "grid" for path-finding tests, all drawing is done through here.
 *
 * @author Clint Mullins
 */
public class Grid {
    private Node[][] grid;
    private float xIsland, yIsland, xMax, yMax, xMin, yMin;
    private Heap heap;
    private float mCellSize;
    private float mShiftX;
    private float mShiftY;

    /**
     * Grid is created, Land is generated in either uniform or random fashion, landscape 'Map' is created in printed.
     *
     * @param xMax    - (int) maximum x coordinate
     * @param yMax    - (int) maximum y coordinate
     * @param xIsland (int) number of islands along x axis
     * @param yIsland (int) number of islands along y axis
     */

    /**
     * This is the constuctor used for comparison. It can be passed an entire Node[][] grid.
     *
     * @param xMax    - (int) maximum x coordinate
     * @param yMax    - (int) maximum y coordinate
     * @param xIsland (int) number of islands along x axis
     * @param yIsland (int) number of islands along y axis
     * @param grid    (Node[][]) an entire grid is passed through for comparison
     */
    public Grid(float xMax, float yMax, float xIsland, float yIsland, Node[][] grid, float cellSize, float shiftX, float shiftY) {
        this.xMax = xMax;
        this.yMax = yMax;
        this.xIsland = xIsland;
        this.yIsland = yIsland;
        this.xMin = this.yMin = 0;
        this.grid = grid;
        heap = new Heap();
        mCellSize = cellSize;
        mShiftX = shiftX;
        mShiftY = shiftY;
    }


    /**
     * returns all adjacent nodes that can be traversed
     *
     * @param node (Node) finds the neighbors of this node
     * @return (int[][]) list of neighbors that can be traversed
     */
    public float[][] getNeighbors(Node node) {
        float[][] neighbors = new float[8][2];
        float x = node.getX();
        float y = node.getY();
        boolean d0 = false; //These booleans are for speeding up the adding of nodes.
        boolean d1 = false;
        boolean d2 = false;
        boolean d3 = false;

        if (walkable(x, y - 1)) {
            neighbors[0] = (tmpInt(x, y - 1));
            d0 = d1 = true;
        }
        if (walkable(x + 1, y)) {
            neighbors[1] = (tmpInt(x + 1, y));
            d1 = d2 = true;
        }
        if (walkable(x, y + 1)) {
            neighbors[2] = (tmpInt(x, y + 1));
            d2 = d3 = true;
        }
        if (walkable(x - 1, y)) {
            neighbors[3] = (tmpInt(x - 1, y));
            d3 = d0 = true;
        }
        if (d0 && walkable(x - 1, y - 1)) {
            neighbors[4] = (tmpInt(x - 1, y - 1));
        }
        if (d1 && walkable(x + 1, y - 1)) {
            neighbors[5] = (tmpInt(x + 1, y - 1));
        }
        if (d2 && walkable(x + 1, y + 1)) {
            neighbors[6] = (tmpInt(x + 1, y + 1));
        }
        if (d3 && walkable(x - 1, y + 1)) {
            neighbors[7] = (tmpInt(x - 1, y + 1));
        }
        return neighbors;
    }

//---------------------------Passability------------------------------//

    /**
     * Tests an x,y node's passability
     *
     * @param x (int) node's x coordinate
     * @param y (int) node's y coordinate
     * @return (boolean) true if the node is obstacle free and on the map, false otherwise
     */
    public boolean walkable(float x, float y) {
        if ((x < xMax && y < yMax)         //smaller than max
                && (x >= xMin && y >= yMin)       //larger than min
                && (Math.sin(Math.PI + xIsland * 2.0 * Math.PI * x / 1000.0) + Math.cos(Math.PI / 2.0 + yIsland * 2.0 * Math.PI * y / 1000.0) > -.1)) {   //walkable
            return true;
        }
        return false;
    }
//--------------------------------------------------------------------//

    public ArrayList<Node> pathCreate(Node node) {
        ArrayList<Node> trail = new ArrayList<Node>();
        System.out.println("Tracing Back Path...");
        while (node.getParent() != null) {
            try {
                trail.add(0, node);
            } catch (Exception e) {
            }
            node = node.getParent();
        }
        System.out.println("Path Trace Complete!");
        return trail;
    }
//-----------------------------------------------------------------//

//--------------------------HEAP-----------------------------------//

    /**
     * Adds a node's (x,y,f) to the heap. The heap is sorted by 'f'.
     *
     * @param node (Node) node to be added to the heap
     */
    public void heapAdd(Node node) {
        float[] tmp = {node.getX(), node.getY(), node.getF()};
        heap.add(tmp);
    }

    /**
     * @return (int) size of the heap
     */
    public int heapSize() {
        return heap.getSize();
    }

    /**
     * @return (Node) takes data from popped float[] and returns the correct node
     */
    public Node heapPopNode() {
        float[] tmp = heap.pop();
        return getNode((int) tmp[0], (int) tmp[1]);
    }
//-----------------------------------------------------------------//


    /**
     * Encapsulates x,y in an int[] for returning. A helper method for the jump method
     *
     * @param x (int) point's x coordinate
     * @param y (int) point's y coordinate
     * @return ([]int) bundled x,y
     */
    public float[] tmpInt(float x, float y) {
        float[] tmpIntsTmpInt = {x, y};  //create the tmpInt's tmpInt[]
        return tmpIntsTmpInt;         //return it
    }

    /**
     * getFunc - Node at given x, y
     *
     * @param x (int) desired node x coordinate
     * @param y (int) desired node y coordinate
     * @return (Node) desired node
     */
    public Node getNode(float x, float y) {
        try {
            int posX = Math.round((x - mShiftX) / mCellSize);
            int posY = Math.round((y - mShiftY) / mCellSize);

            return grid[posX][posY];
        } catch (Exception e) {
            return null;
        }
    }

    public float toPointApprox(float x, float y, float tx, float ty) {
        return (float) Math.sqrt(Math.pow(Math.abs(x - tx), 2) + Math.pow(Math.abs(y - ty), 2));
    }
}

