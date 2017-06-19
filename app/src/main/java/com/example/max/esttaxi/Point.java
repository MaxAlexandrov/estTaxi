package com.example.max.esttaxi;

/**
 * Created by max on 5/25/17.
 * By point on the map
 */

public class Point {
    private double mXPoint = 0;
    private double mYPoint = 0;

    public Point(double cXPoint, double cYPoint){
        this.mXPoint = cXPoint;
        this.mYPoint = cYPoint;
    }

    public double getmXPoint() {
        return mXPoint;
    }

    public void setmXPoint(double mXPoint) {
        this.mXPoint = mXPoint;
    }

    public double getmYPoint() {
        return mYPoint;
    }

    public void setmYPoint(double mYPoint) {
        this.mYPoint = mYPoint;
    }
}
