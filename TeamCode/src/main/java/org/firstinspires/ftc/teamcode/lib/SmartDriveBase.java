package org.firstinspires.ftc.teamcode.lib;

import static java.lang.Math.*;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class SmartDriveBase extends DriveBase {
    public Positioning positioning = null;
    SparkFunOTOS.Pose2D target = null;

    public SmartDriveBase(HardwareMap hardwareMap) {
        super(hardwareMap);
        positioning = new Positioning(hardwareMap);
        target = positioning.getPose();
    }

    public void globalMove(double forward, double right) {
        YawPitchRollAngles angles = positioning.getOrientation();
        double yaw = angles.getYaw();
        double x = right * Math.cos(yaw) - forward * Math.sin(yaw);
        double y = right * Math.sin(yaw) + forward * Math.cos(yaw);
        drive(y);
        strafe(x);
    }

    public SparkFunOTOS.Pose2D getTarget() {
        return this.target;
    }
    public void setTarget(SparkFunOTOS.Pose2D new_target) {
        this.target = new_target;
    }

    public void driveToTarget() {
        SparkFunOTOS.Pose2D pose = positioning.getPose();
        double dx = target.x - pose.x;
        double dy = target.y - pose.y;
        globalMove(dy,dx);
    }

    @Override
    public void stop() {
        super.stop();
        target = positioning.getPose();
    }

    public void pointTowards(double heading) {
        SparkFunOTOS.Pose2D pose = positioning.getPose();
        double difference = RMath.angleDifference(heading,pose.h);
        double power = - difference * 0.5;
        turn(power);
    }
}
