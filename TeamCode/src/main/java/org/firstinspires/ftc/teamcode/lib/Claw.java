package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    Servo claw_servo = null;
    double close_pos = 0.9;
    double open_pos = 0.5;

    public Claw(HardwareMap hardwareMap) {
        this.claw_servo = hardwareMap.get(Servo.class,"claw");
    }

    public void setPos(double pos) {
        double real_pos = (open_pos - close_pos) * pos + close_pos;
        this.claw_servo.setPosition(real_pos);
    }
    public void close() {
        this.setPos(0);
    }
    public void open() {
        this.setPos(1);
    }
}
