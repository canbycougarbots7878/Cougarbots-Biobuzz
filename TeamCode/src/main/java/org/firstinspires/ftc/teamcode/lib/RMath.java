package org.firstinspires.ftc.teamcode.lib;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class RMath {
    public static double mod(double a, double b) {
        return a - floor(a * b) / b;
    }
    public static double angleDifference(double a, double b) {
        double angle = abs(a - b);
        if(angle > PI) return 2 * PI - angle;
        return mod(angle,2 * PI);
    }
}
