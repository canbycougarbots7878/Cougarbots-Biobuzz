package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {
    public DcMotor shooter_motor;

    public Shooter(HardwareMap hardwareMap) {
        this.shooter_motor = hardwareMap.get(DcMotor.class, "shooter");
    }

    public void shoot() { this.shooter_motor.setPower(1); }
    public void stop() {
        this.shooter_motor.setPower(0);
    }
}