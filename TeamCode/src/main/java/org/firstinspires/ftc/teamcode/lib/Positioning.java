package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class Positioning {
    SparkFunOTOS otos = null;
    IMU imu = null;

    public Positioning(HardwareMap hardwareMap) {
        otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        otos.resetTracking();
        otos.calibrateImu();
        otos.setLinearUnit(DistanceUnit.CM);
        otos.setAngularUnit(AngleUnit.RADIANS);
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters params = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(params);
    }

    public SparkFunOTOS.Pose2D getPose() {
        SparkFunOTOS.Pose2D pose = otos.getPosition();
        return pose;
    }

    public YawPitchRollAngles getOrientation() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        YawPitchRollAngles orientation_radians = new YawPitchRollAngles(
                AngleUnit.RADIANS,
                orientation.getYaw(AngleUnit.RADIANS),
                orientation.getPitch(AngleUnit.RADIANS),
                orientation.getRoll(AngleUnit.RADIANS),
                orientation.getAcquisitionTime()
        );
        return orientation_radians;
    }
}
