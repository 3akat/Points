package com.bedulin.dots.logic.search;

import com.bedulin.dots.ui.views.GameFieldView;

import java.util.ArrayList;

import static com.bedulin.dots.Constants.CELLS_IN_HEIGHT;
import static com.bedulin.dots.Constants.CELLS_IN_WIDTH;

/**
 * Holds a Node[][] 2d array "grid" for path-finding tests, all drawing is done through here.
 *
 * @author Clint Mullins
 */
public class Grid {
    private Node[][] grid;
    private float xIsland;
    private float yIsland;
    private float xMax;
    private float yMax;
    private float xMin;
    private float yMin;
    private Heap heap;
    private GameFieldView mGameFieldView;

    /**
     * This is the constuctor used for comparison. It can be passed an entire Node[][] grid.
     */
    public Grid(GameFieldView gameFieldView) {
        mGameFieldView = gameFieldView;
        xMax = CELLS_IN_WIDTH * mGameFieldView.CELL_SIZE + mGameFieldView.getShiftX();
        yMax = CELLS_IN_HEIGHT * mGameFieldView.CELL_SIZE + mGameFieldView.getShiftY();
        this.xIsland = 0;
        this.yIsland = 0;
        this.xMin = mGameFieldView.getShiftX();
        this.yMin = mGameFieldView.getShiftY();
        this.grid = mGameFieldView.getPossibleMoves();
        heap = new Heap();
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

        if (walkable(x, y - mGameFieldView.CELL_SIZE)) {
            neighbors[0] = (tmpInt(x, y - mGameFieldView.CELL_SIZE));
        }
        if (walkable(x + mGameFieldView.CELL_SIZE, y)) {
            neighbors[1] = (tmpInt(x + mGameFieldView.CELL_SIZE, y));
        }
        if (walkable(x, y + mGameFieldView.CELL_SIZE)) {
            neighbors[2] = (tmpInt(x, y + mGameFieldView.CELL_SIZE));
        }
        if (walkable(x - mGameFieldView.CELL_SIZE, y)) {
            neighbors[3] = (tmpInt(x - mGameFieldView.CELL_SIZE, y));
        }
        if (walkable(x - mGameFieldView.CELL_SIZE, y - mGameFieldView.CELL_SIZE)) {
            neighbors[4] = (tmpInt(x - mGameFieldView.CELL_SIZE, y - mGameFieldView.CELL_SIZE));
        }
        if (walkable(x + mGameFieldView.CELL_SIZE, y - mGameFieldView.CELL_SIZE)) {
            neighbors[5] = (tmpInt(x + mGameFieldView.CELL_SIZE, y - mGameFieldView.CELL_SIZE));
        }
        if (walkable(x + mGameFieldView.CELL_SIZE, y + mGameFieldView.CELL_SIZE)) {
            neighbors[6] = (tmpInt(x + mGameFieldView.CELL_SIZE, y + mGameFieldView.CELL_SIZE));
        }
        if (walkable(x - mGameFieldView.CELL_SIZE, y + mGameFieldView.CELL_SIZE)) {
            neighbors[7] = (tmpInt(x - mGameFieldView.CELL_SIZE, y + mGameFieldView.CELL_SIZE));
        }
        return neighbors;
    }

    /**
     * Tests an x,y node's passability
     *
     * @param x (int) node's x coordinate
     * @param y (int) node's y coordinate
     * @return (boolean) true if the node is obstacle free and on the map, false otherwise
     */
    public boolean walkable(float x, float y) {
        if (x <= xMax && y <= yMax)         //smaller than max
            if (x >= xMin && y >= yMin)       //larger than min
                    switch (mGameFieldView.getNextMove()) {                                                                                             //walkable
                        case GameFieldView.PLAYER_ONE_MOVE:
                            if (mGameFieldView.getPlayerOneMoves().contains(new Node(x, y)))
                                return true;
                            else
                                return false;
                        case GameFieldView.PLAYER_TWO_MOVE:
                            if (mGameFieldView.getPlayerTwoMoves().contains(new Node(x, y)))
                                return true;
                            else
                                return false;
                    }
        return false;
    }

    public ArrayList<Node> pathCreate(Node node) {
        ArrayList<Node> trail = new ArrayList<Node>();
        System.out.println("Tracing Back Path...");
        do {
            trail.add(0, node);
            node = node.getParent();
        } while (node != null);
        System.out.println("Path Trace Complete!");
        return trail;
    }

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
            int posX = Math.round((x - xMin) / mGameFieldView.CELL_SIZE);
            int posY = Math.round((y - yMin) / mGameFieldView.CELL_SIZE);

            return grid[posX][posY];
        } catch (Exception e) {
            return null;
        }
    }

    public float toPointApprox(float x, float y, float tx, float ty) {
        return (float) Math.sqrt(Math.pow(Math.abs(x - tx), 2) + Math.pow(Math.abs(y - ty), 2));
    }
}

