package com.bedulin.dots.logic.search;

import android.graphics.PointF;

public class Node extends PointF {
  public float g;
  public float h;
  public float f;  //g = from start; h = to end, f = both together
  public int posX;
  public int posY;
  public boolean pass;
  public Node parent;

    public Node(float x, float y, int posX, int posY) {
        this.x = x;
        this.y = y;
        this.pass = true;
        this.posX = posX;
        this.posY = posY;
    }

    public void updateGHFP(float g, float h, Node parent) {
        this.parent = parent;
        this.g = g;
        this.h = h;
        f = g + h;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Node))
            return false;
        return this.x == ((Node) o).x && this.y == ((Node) o).y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) this.x;
        hash = 71 * hash + (int) this.y;
        return hash;
    }
}
