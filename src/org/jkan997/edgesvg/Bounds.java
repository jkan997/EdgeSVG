/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg;

/**
 *
 * @author jakaniew
 */
public class Bounds {

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void addPoint(int x, int y) {
        if (x < minX) {
            minX = x;
        }
        if (x > maxX) {
            maxX = x;

        }
        if (y < minY) {
            minY = y;
        }
        if (y > maxY) {
            maxY = y;

        }


    }
    
    public int getWidth(){
        return maxX-minX;
    }
    
    public int getHeight(){
        return maxY-minY;
    }

    public void addPoint(float x, float y) {
        addPoint(Math.round(x), Math.round(y));
    }

    @Override
    public String toString() {
        return "Bounds{" + "minX=" + minX + ", minY=" + minY + ", maxX=" + maxX + ", maxY=" + maxY + '}';
    }
    
    
}
