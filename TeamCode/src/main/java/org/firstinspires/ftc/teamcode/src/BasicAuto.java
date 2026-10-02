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
//supercalifragalisticexpialidocious
//hippopotomonstrosesquippedaliophobia
//antidisestablishmentarianism


@Autonomous(name="Basic Auto", group="Basic")
public class BasicAuto extends LinearOpMode {
    DriveBase drive_base = null;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() {
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.resetTracking();
        waitForStart();
        drive_base.strafe(-0.35);


        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D pose = Otos.getPosition();

            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();
            if (distance(pose.x, 0, pose.y, 0) < 1 ){
                telemetry.addLine("at (0,0)");
            }
          if (pose.x < -39.7981244655776) {
              drive_base.stop();
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
