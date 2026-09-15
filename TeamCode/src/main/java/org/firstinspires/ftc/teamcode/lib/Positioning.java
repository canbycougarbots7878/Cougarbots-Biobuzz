package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Positioning {
    SparkFunOTOS otos = null;

    public Positioning(HardwareMap hardwareMap) {
        otos = hardwareMap.get(SparkFunOTOS.class, "otos");
    }

    public SparkFunOTOS.Pose2D getPose() {
        SparkFunOTOS.Pose2D pose = otos.getPosition();
        return pose;
    }
}
