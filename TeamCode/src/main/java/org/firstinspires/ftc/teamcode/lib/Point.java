package org.firstinspires.ftc.teamcode.lib;

public class Point {
    double x;
    double y;

    Point() {
        this.x = 0;
        this.y = 0;
    }
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double distanceTo(Point other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx*dx+dy*dy);
    }
    double angleTo(Point other) {
        return Math.atan2(this.y-other.y,this.x-other.y);
    }
}
