package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import math;
import org.firstinspires.ftc.teamcode.lib.Claw;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Lift;
import org.firstinspires.ftc.teamcode.lib.Shooter;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

@SuppressWarnings("unused")
@TeleOp(name="Auto Drive", group="Basic")
public class AutoDrive extends LinearOpMode {
    SmartDriveBase drive_base;
    boolean attarget1 = false;
    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);
        drive_base.setTarget(new SparkFunOTOS.Pose2D(0,100,0));
        waitForStart();
        while (opModeIsActive()) {
            drive_base.driveToTarget();

            ;if (distance_to_target()<1 && !attarget1){
                drive_base.setTarget(new SparkFunOTOS.Pose2D(0,0,0));
                attarget1 = true;
            }
            if (distance_to_target()<1 && attarget1){
                drive_base.setTarget(new SparkFunOTOS.Pose2D(0,100,0));
                drive_base.driveToTarget();
                attarget1 = false;
            }
            SparkFunOTOS.Pose2D pose = drive_base.positioning.getPose();
            telemetry.addData("x", pose.x);
            telemetry.addData("y", pose.y);
            telemetry.addData("h", pose.h);
            telemetry.addData("distance to target", distance_to_target());
            telemetry.addData("at target 1",attarget1);
            telemetry.update();
        }
    }
    public double distance_to_target(){
        SparkFunOTOS.Pose2D pose = drive_base.positioning.getPose();
            double dx = drive_base.getTarget().x - pose.x;
            double dy = drive_base.getTarget().y - pose.y;
            final double distance = sqrt(dx*dx + dy*dy);

            return distance;

    }
}
