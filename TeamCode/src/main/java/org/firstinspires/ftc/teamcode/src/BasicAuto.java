package org.firstinspires.ftc.teamcode.src;


import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

import java.util.concurrent.TimeUnit;
//pnuemonoultramicroscopicsilicovolcanoconiosis

@SuppressWarnings("unused")
@Autonomous(name="Basic Auto", group="Basic")
public class BasicAuto extends LinearOpMode {
    DriveBase drive_base = null;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() throws InterruptedException {
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        waitForStart();
        /*drive_base.drive(0.05);
        drive_base.strafe(-0.35);
        TimeUnit.SECONDS.sleep(2);
        drive_base.stop();
        drive_base.drive(-0.1);
        TimeUnit.MILLISECONDS.sleep(500);
        drive_base.stop();
        */


        SparkFunOTOS.Pose2D pose = Otos.getPosition();
        drive_base.strafe(-0.35);
        while (pose.x < 40.5) {
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();
        }
        drive_base.stop();
        while (opModeIsActive()) {

            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();
            if (distance(pose.x, 0, pose.y, 0) < 1 ){
                telemetry.addLine("at (0,0)");
            while (pose.x < -40.5) {
                telemetry.addData("x",pose.x);
                telemetry.addData("y",pose.y);
                telemetry.addData("h",pose.h);
                telemetry.update();
                drive_base.strafe(-0.35);
            }

            }

        }

    }
    public double distance(double x1, double x2, double y1, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        final double distance = sqrt(dx*dx + dy*dy);

        return distance;
    }
}
