package org.firstinspires.ftc.teamcode.lib;

import static java.lang.Math.*;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
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
        SparkFunOTOS.Pose2D pose = positioning.getPose();
        double yaw = pose.h;
        double x = right * cos(yaw) + forward * sin(yaw);
        double y = forward * cos(yaw) - right * sin(yaw);
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
        double m = sqrt(dx*dx+dy*dy);
        dx /= m;
        dy /= m;
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

    public void globalMoveWithController(Gamepad gamepad, double speed_mod) {
        double forward = - gamepad.left_stick_y * speed_mod;
        double right = gamepad.left_stick_x * speed_mod;
        double clockwise = - gamepad.right_stick_x * speed_mod;
        globalMove(forward,right);
        turn(clockwise);
    }
}
