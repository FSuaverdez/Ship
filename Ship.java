/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Point;

/**
 *
 * @author JMDC
 */
public class Ship {

    private Point[] p;
    private String type;
    private int orientation;
    private int hitCount = 0;

    public Ship(String type, int orientation, Point[] p) {
        this.type = type;
        this.orientation = orientation;
        this.p = p;
    }

    public boolean hit(Point p) {
        for (int i = 0; i < this.p.length; i++) {
            if (this.p[i].getY() == p.getY() && this.p[i].getX() == p.getX()) {
                hitCount++;
                return true;
            }
        }
        return false;
    }

    public String type() {
        return this.type;
    }

    public boolean sunk() {
        if (this.p.length == hitCount) {
            return true;
        }
        return false;
    }

    public void addToGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (int k = 0; k < p.length; k++) {
                    if (i == p[k].getY() && j == p[k].getX()) {
                        grid[i][j] = this.type.charAt(0);

                    }
                }
            }
        }
    }

    public boolean clashes(Point[] points) {

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < p.length; j++) {
                if (points[i].getY() == p[j].getY() && points[i].getX() == p[j].getX()) {
                    return true;
                }
            }

        }
        return false;
    }
}
