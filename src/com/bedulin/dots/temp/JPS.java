package com.bedulin.dots.temp;

import com.bedulin.dots.ui.views.GameFieldView;

/**
 * @author Clint Mullins
 * @referenced Javascript version of JPS by aniero / https://github.com/aniero
 */
public class JPS {
    GameFieldView mGameFieldView;
    Grid grid;
    private float xMax;
    private float yMax;
    private float xIsland;
    private float yIsland;
    private float startX;
    private float startY;
    private float endX;
    private float endY;  //variables for reference grid
    private float[] tmpXY;
    private float[][] neighbors;
    private float ng;
    private Node cur;
    private Node[] successors, possibleSuccess;

    /**
     * Initializer; sets up variables, creates reference grid and actual grid, gets start and end points, initiates search
     */
    public JPS(GameFieldView gameFieldView, Node startPos, Node endPos) {
        mGameFieldView = gameFieldView;

        grid = new Grid(gameFieldView);  //preMadeGrid is passed in because there CAN BE ONLY ONE GRID

        this.startX = startPos.x;   //the start point x value
        this.startY = startPos.y;      //the start point y value

        this.endX = endPos.x;      //the end point x value
        this.endY = endPos.y;      //the end point y value

    }

    /**
     * Orchestrates the Jump Point Search; it is explained further in comments below.
     */
    public boolean search() {
        grid.getNode(startX, startY).updateGHFP(0, 0, null);
        grid.heapAdd(grid.getNode(startX, startY));  //Start node is added to the heap
        while (true) {
            cur = grid.heapPopNode();              //the current node is removed from the heap.
            if (cur.getX() == endX && cur.getY() == endY) {        //if the end node is found
                //Path Found!
                mGameFieldView.setPath(grid.pathCreate(cur));    //the path is then created
                return true;
            }
            possibleSuccess = identifySuccessors(cur);  //get all possible successors of the current node
            for (int i = 0; i < possibleSuccess.length; i++) {     //for each one of them
                if (possibleSuccess[i] != null) {                //if it is not null
                    grid.heapAdd(possibleSuccess[i]);        //add it to the heap for later use (a possible future cur)
                }
            }
            if (grid.heapSize() == 0) {                        //if the grid size is 0, and we have not found our end, the end is unreachable
                //No Path
                return false;
            }
        }
    }

    /**
     * returns all nodes jumped from given node
     *
     * @param node
     * @return all nodes jumped from given node
     */
    public Node[] identifySuccessors(Node node) {
        successors = new Node[8];                //empty successors list to be returned
        neighbors = getNeighborsPrune(node);    //all neighbors after pruned
        for (int i = 0; i < neighbors.length; i++) { //for each of these neighbors
            tmpXY = jump(neighbors[i][0], neighbors[i][1], node.getX(), node.getY()); //get next jump point
            if (tmpXY[0] != -1) {                                //if that point is not null( {-1,-1} )
                float x = tmpXY[0];
                float y = tmpXY[1];
                ng = (grid.toPointApprox(x, y, node.getX(), node.getY()) + node.getG());   //get the distance from start
                if (grid.getNode(x, y).getF() <= 0 || grid.getNode(x, y).getG() > ng) {  //if this node is not already found, or we have a shorter distance from the current node
                    grid.getNode(x, y).updateGHFP(grid.toPointApprox(x, y, node.getX(), node.getY()) + node.getG(), grid.toPointApprox(x, y, endX, endY), node); //then update the rest of it
                    successors[i] = grid.getNode(x, y);  //add this node to the successors list to be returned
                }
            }
        }
        return successors;  //finally, successors is returned
    }

    /**
     * jump method recursively searches in the direction of parent (px,py) to child, the current node (x,y).
     * It will stop and return its current position in three situations:
     * <p/>
     * 1) The current node is the end node. (endX, endY)
     * 2) The current node is a forced neighbor.
     * 3) The current node is an intermediate step to a node that satisfies either 1) or 2)
     *
     * @param x  (int) current node's x
     * @param y  (int) current node's y
     * @param px (int) current.parent's x
     * @param py (int) current.parent's y
     * @return (int[]={x, y}) node which satisfies one of the conditions above, or null if no such node is found.
     */
    public float[] jump(float x, float y, float px, float py) {
        float[] jx = {-1, -1}; //used to later check if full or null
        float[] jy = {-1, -1}; //used to later check if full or null
        float dx = (x - px) / Math.max(Math.abs(x - px), 1); //because parents aren't always adjacent, this is used to find parent -> child direction (for x)
        float dy = (y - py) / Math.max(Math.abs(y - py), 1); //because parents aren't always adjacent, this is used to find parent -> child direction (for y)
        dx *= mGameFieldView.getCellSize();
        dy *= mGameFieldView.getCellSize();
        if (!grid.walkable(x, y)) { //if this space is not grid.walkable, return a null.
            return tmpInt(-1, -1); //in this system, returning a {-1,-1} equates to a null and is ignored.
        }
        if (x == this.endX && y == this.endY) {   //if end point, return that point. The search is over! Have a beer.
            return tmpInt(x, y);
        }
        if (dx != 0 && dy != 0) {  //if x and y both changed, we are on a diagonally adjacent square: here we check for forced neighbors on diagonals
            if ((grid.walkable(x - dx, y + dy) && !grid.walkable(x - dx, y)) || //we are moving diagonally, we don't check the parent, or our next diagonal step, but the other diagonals
                    (grid.walkable(x + dx, y - dy) && !grid.walkable(x, y - dy))) {  //if we find a forced neighbor here, we are on a jump point, and we return the current position
                return tmpInt(x, y);
            }
        } else { //check for horizontal/vertical
            if (dx != 0) { //moving along x
                if ((grid.walkable(x + dx, y + mGameFieldView.getCellSize()) && !grid.walkable(x, y + mGameFieldView.getCellSize())) || //we are moving along the x axis
                        (grid.walkable(x + dx, y - mGameFieldView.getCellSize()) && !grid.walkable(x, y - mGameFieldView.getCellSize()))) {  //we check our side nodes to see if they are forced neighbors
                    return tmpInt(x, y);
                }
            } else {
                if ((grid.walkable(x + mGameFieldView.getCellSize(), y + dy) && !grid.walkable(x + mGameFieldView.getCellSize(), y)) ||  //we are moving along the y axis
                        (grid.walkable(x - mGameFieldView.getCellSize(), y + dy) && !grid.walkable(x - mGameFieldView.getCellSize(), y))) {     //we check our side nodes to see if they are forced neighbors
                    return tmpInt(x, y);
                }
            }
        }

        if (dx != 0 && dy != 0) { //when moving diagonally, must check for vertical/horizontal jump points
            jx = jump(x + dx, y, x, y);
            jy = jump(x, y + dy, x, y);
            if (jx[0] != -1 || jy[0] != -1) {
                return tmpInt(x, y);
            }
        }
        if (grid.walkable(x + dx, y) || grid.walkable(x, y + dy)) { //moving diagonally, must make sure one of the vertical/horizontal neighbors is open to allow the path
            return jump(x + dx, y + dy, x, y);
        } else { //if we are trying to move diagonally but we are blocked by two touching corners of adjacent nodes, we return a null
            return tmpInt(-1, -1);
        }
    }

    /**
     * Encapsulates x,y in an int[] for returning. A helper method for the jump method
     *
     * @param x (int) point's x coordinate
     * @param y (int) point's y coordinate
     * @return ([]int) bundled x,y
     */
    public float[] tmpInt(float x, float y) {
        float[] tmpIntsTmpInt = {x, y};
        return tmpIntsTmpInt;
    }

    /**
     * Returns nodes that should be jumped based on the parent location in relation to the given node.
     *
     * @param node (Node) node which has a parent (not the start node)
     * @return (ArrayList<Node>) list of nodes that will be jumped
     */
    public float[][] getNeighborsPrune(Node node) {
        Node parent = node.getParent();    //the parent node is retrieved for x,y values
        float x = node.getX();
        float y = node.getY();
        float px, py, dx, dy;
        float[][] neighbors = new float[5][2];
        //directed pruning: can ignore most neighbors, unless forced
        if (parent != null) {
            px = parent.getX();
            py = parent.getY();
            //get the normalized direction of travel
            dx = (x - px) / Math.max(Math.abs(x - px), 1);
            dy = (y - py) / Math.max(Math.abs(y - py), 1);
            dx *= mGameFieldView.getCellSize();
            dy *= mGameFieldView.getCellSize();

            //search diagonally
            if (dx != 0 && dy != 0) {
                if (grid.walkable(x, y + dy)) {
                    neighbors[0] = (tmpInt(x, y + dy));
                }
                if (grid.walkable(x + dx, y)) {
                    neighbors[1] = (tmpInt(x + dx, y));
                }
                if (grid.walkable(x, y + dy) || grid.walkable(x + dx, y)) {
                    neighbors[2] = (tmpInt(x + dx, y + dy));
                }
                if (!grid.walkable(x - dx, y) && grid.walkable(x, y + dy)) {
                    neighbors[3] = (tmpInt(x - dx, y + dy));
                }
                if (!grid.walkable(x, y - dy) && grid.walkable(x + dx, y)) {
                    neighbors[4] = (tmpInt(x + dx, y - dy));
                }
            } else {
                if (dx == 0) {
                    if (grid.walkable(x, y + dy)) {
                        if (grid.walkable(x, y + dy)) {
                            neighbors[0] = (tmpInt(x, y + dy));
                        }
                        if (!grid.walkable(x + mGameFieldView.getCellSize(), y)) {
                            neighbors[1] = (tmpInt(x + mGameFieldView.getCellSize(), y + dy));
                        }
                        if (!grid.walkable(x - mGameFieldView.getCellSize(), y)) {
                            neighbors[2] = (tmpInt(x - mGameFieldView.getCellSize(), y + dy));
                        }
                    }
                } else {
                    if (grid.walkable(x + dx, y)) {
                        if (grid.walkable(x + dx, y)) {
                            neighbors[0] = (tmpInt(x + dx, y));
                        }
                        if (!grid.walkable(x, y + mGameFieldView.getCellSize())) {
                            neighbors[1] = (tmpInt(x + dx, y + mGameFieldView.getCellSize()));
                        }
                        if (!grid.walkable(x, y - mGameFieldView.getCellSize())) {
                            neighbors[2] = (tmpInt(x + dx, y - mGameFieldView.getCellSize()));
                        }
                    }
                }
            }
        } else {//return all neighbors
            return grid.getNeighbors(node); //adds initial nodes to be jumped from!
        }

        return neighbors; //this returns the neighbors, you know
    }
}
