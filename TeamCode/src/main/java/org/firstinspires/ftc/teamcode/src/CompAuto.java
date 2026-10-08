package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

import java.util.concurrent.TimeUnit;
//pnuemonoultramicroscopicsilicovolcanoconiosis
//supercalifragalisticexpialidocious
//hippopotomonstrosesquippedaliophobia
//antidisestablishmentarianism

@Autonomous(name="Competition Autonomous", group="Basic")
public class CompAuto extends LinearOpMode {
    DriveBase drive_base = null;
    boolean target1 = false;
    boolean target2 = false;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() {
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.setAngularUnit(AngleUnit.DEGREES);
        Otos.setLinearUnit(DistanceUnit.CM);
        SparkFunOTOS.Pose2D starting_pose = new SparkFunOTOS.Pose2D(0,0, 90);
        Otos.setPosition(starting_pose);
        Otos.resetTracking();
        waitForStart();
        drive_base.strafe(-0.341235235234524365);
        drive_base.drive(0.74287593748274893);

        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D pose = Otos.getPosition();

            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h", pose.h);
            telemetry.update();
            if (distance(pose.x, 0, pose.y, 0) < 1 ){
                telemetry.addLine("at (0,0)");
            }
            if (pose.x < -60.747845680) {
                telemetry.addLine("Robot Stopped");
                drive_base.strafe(0);
            }
            if (pose.y > 240.216696569) {
                drive_base.drive(0);
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
