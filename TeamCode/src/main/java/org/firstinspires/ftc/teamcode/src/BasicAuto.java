package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

import java.util.concurrent.TimeUnit;
//pnuemonoultramicroscopicsilicovolcanoconiosis
//supercalifragalisticexpialidocious
//hippopotomonstrosesquippedaliophobia
//antidisestablishmentarianism


@Autonomous(name="Basic Auto", group="Basic")
public class BasicAuto extends LinearOpMode {
    SmartDriveBase drive_base = null;
    int stage = 0;

    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);
        SparkFunOTOS.Pose2D starting_pose = new SparkFunOTOS.Pose2D(0,0, 0);
        drive_base.positioning.setPose(starting_pose);
        waitForStart();
        drive_base.setTarget(new SparkFunOTOS.Pose2D(-91.6181244655776, 0, 90));

        while (opModeIsActive()) {
            if(stage == 0) {
                telemetry.addLine("Robot is moving... (0/2 checkpoints)");
                drive_base.driveToTarget();
                if(drive_base.distanceToTarget() < 4) {
                    stage++;
                    drive_base.setTarget(new SparkFunOTOS.Pose2D(-91.6181244655776, 10, 90));
                }
            }
            if(stage == 1) {
                telemetry.addLine("Robot is moving... (1/2 checkpoints)");
                drive_base.driveToTarget();
                if(drive_base.distanceToTarget() < 4) {
                    stage++;
                }
            }
            if(stage == 2) {
                telemetry.addLine("Robot is at target (2/2 checkpoints)");
                drive_base.stop();
            }
            drive_base.positioning.telemetryPose(telemetry);
            telemetry.update();
        }

    }
    public double distance(double x1, double x2, double y1, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        final double distance = sqrt(dx*dx + dy*dy);

        return distance;
    }
}
