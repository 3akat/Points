package com.bedulin.dots.temp;

import android.graphics.PointF;

public class Node extends PointF {
    private float g, h, f;  //g = from start; h = to end, f = both together
    private boolean pass;
    private Node parent;

    public Node(float x, float y) {
        this.x = x;
        this.y = y;
        this.pass = true;
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
        hash = 71 * hash + (int)this.x;
        hash = 71 * hash + (int)this.y;
        return hash;
    }

    public boolean setPass(boolean pass) {
        this.pass = pass;
        return pass;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getG() {
        return g;
    }

    public float getH() {
        return h;
    }

    public float getF() {
        return f;
    }

    public boolean isPass() {
        return pass;
    }

    public Node getParent() {
        return parent;
    }
}
