package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    public DcMotor lift_motor;

    public Lift(HardwareMap hardwareMap) {
        this.lift_motor = hardwareMap.get(DcMotor.class, "lift");
    }

    public void move(double direction) {
        // Positive: up
        // Negative: down
        this.lift_motor.setPower(direction);
    }
    public void stop() {
        this.lift_motor.setPower(0);
    }
}