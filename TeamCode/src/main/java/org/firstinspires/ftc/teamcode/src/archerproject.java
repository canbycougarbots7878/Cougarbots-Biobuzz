package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

@TeleOp(name="archer project", group="Basic")
public class archerproject extends LinearOpMode {
    SmartDriveBase drive_base = null;
    int step = 0;


    public void DoVictoryDance(){
        drive_base.turn(76);
        drive_base.turn(-55);
        drive_base.turn(360);
        drive_base.turn(-360);
    }
    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);
        drive_base.setTarget(new SparkFunOTOS.Pose2D(0,0,0));
        waitForStart();
        drive_base.driveToTarget();
        if (distance_to_target() < 1 && step==0){
            drive_base.setTarget(new SparkFunOTOS.Pose2D(100,0,0));
            drive_base.driveToTarget();
            step = 1;
        }
        if (distance_to_target() < 1 && step==1){
            drive_base.setTarget(new SparkFunOTOS.Pose2D(100,100,0));
            drive_base.driveToTarget();
            step = 2;
        }
        if (distance_to_target() < 1 && step==2){
            drive_base.setTarget(new SparkFunOTOS.Pose2D(-100,100,0));
            drive_base.driveToTarget();
            step = 3;
        }
        if (distance_to_target() < 1 && step==3){
            drive_base.setTarget(new SparkFunOTOS.Pose2D(-100,-100,0));
            drive_base.driveToTarget();
            step = 4;
        }
        if (distance_to_target() < 1 && step==4){
            drive_base.setTarget(new SparkFunOTOS.Pose2D(0,0,0));
            drive_base.driveToTarget();
            step = 5;
        }
        if (distance_to_target() < 1 && step==5){
            telemetry.addLine("Done");
            step = 6;
            DoVictoryDance();
            telemetry.addLine("Doing Victory Dance");
        }
        SparkFunOTOS.Pose2D pose = drive_base.positioning.getPose();
        telemetry.addData("x", pose.x);
        telemetry.addData("y", pose.y);
        telemetry.addData("h", pose.h);
        telemetry.addData("distance to target", distance_to_target());
        telemetry.addData("Step:", step);
        telemetry.update();
    }
    public double distance_to_target(){
        SparkFunOTOS.Pose2D pose = drive_base.positioning.getPose();
        double dx = drive_base.getTarget().x - pose.x;
        double dy = drive_base.getTarget().y - pose.y;
        return sqrt(dx*dx + dy*dy);

    }
}

