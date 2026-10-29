package org.firstinspires.ftc.teamcode.src;




import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

/*----------------------------Unused Import Statements-----------------------------------------\\
import org.firstinspires.ftc.teamcode.lib.Positioning;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.sql.Time;
*/

/*
This program moves the robot in all 4 cardinal directions and then checks if the X and Y values on
the OTOS changed correctly (Based on this diagram):


                        Y
                        |
                 -X ----+---- X
                        |
                       -Y
*/
@SuppressWarnings("unused")
@Autonomous(name="Pre-match Checkup", group="Basic")
public class PreMatchCheckup extends LinearOpMode {
    DriveBase drive_base = null;
    int ChecksFailed = 0;
    int stage = 0;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() throws InterruptedException {
        // ---------------------------Setup--------------------------------------\\
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.setAngularUnit(AngleUnit.DEGREES);
        Otos.setLinearUnit(DistanceUnit.CM);
        SparkFunOTOS.Pose2D starting_pose = new SparkFunOTOS.Pose2D(0,0, 90);
        Otos.setPosition(starting_pose);
        Otos.resetTracking();
        SparkFunOTOS.Pose2D pose = Otos.getPosition();


        waitForStart();
        while (opModeIsActive()) {
            // ----------------------OTOS Position Tracking--------------------------\\

            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();

            //----------------------------Check Positive Y---------------------------------\\
            if (stage == 0) {
                drive_base.drive(0.5);
                TimeUnit.MILLISECONDS.sleep(500);
                pose = Otos.getPosition();
                drive_base.stop();
                if (pose.y > 2) { //Checks if Y went up
                    telemetry.addLine("+Y value correct");
                    telemetry.update();
                    stage = 1;
                } else {
                    telemetry.addLine("+Y value is wrong");
                    telemetry.update();
                    ChecksFailed += 1;
                    stage = 1;
                }
            }
            //---------------------------Check Negative Y-----------------------------------\\
            if (stage == 1) {
                drive_base.drive(-0.5);
                TimeUnit.SECONDS.sleep(1);
                pose = Otos.getPosition();
                drive_base.stop();
                if (pose.y < -2) { //Checks if y decreased
                    telemetry.addLine("-Y value correct");
                    telemetry.update();
                    stage = 2;
                } else {
                    telemetry.addLine("-Y value is wrong");
                    telemetry.update();
                    ChecksFailed += 1;
                    stage = 2;
                }
            }
            //----------------------------Check Positive X----------------------------------\\
            if (stage == 2) {
                drive_base.strafe(0.5);
                TimeUnit.MILLISECONDS.sleep(500);
                pose = Otos.getPosition();
                drive_base.stop();
                if (pose.x > 2) { //Checks if x increased
                    telemetry.addLine("+X value correct");
                    telemetry.update();
                    stage = 3;
                } else {
                    telemetry.addLine("+X value is wrong");
                    telemetry.update();
                    ChecksFailed += 1;
                    stage = 3;
                }
            }
            //----------------------------Check Negative X-------------------------------\\
            if (stage == 3) {
                drive_base.strafe(-0.5);
                TimeUnit.MILLISECONDS.sleep(1000);
                pose = Otos.getPosition();
                drive_base.stop();
                if (pose.x < -2) { //Checks if x decreased
                    telemetry.addLine("-X value correct");
                    telemetry.update();
                    stage = 4;
                } else {
                    telemetry.addLine("-X value is wrong");
                    telemetry.update();
                    ChecksFailed += 1;
                    stage = 4;
                }
                TimeUnit.SECONDS.sleep(3);
            }
            //-----------------------------Final Check-----------------------------------\\
            if (stage == 4) {
                stage += 1;
                if (ChecksFailed == 0) {
                    telemetry.clearAll();
                    telemetry.addLine("All Checks Successful");
                } else {
                    telemetry.addData("Checks Failed:", ChecksFailed);
                }
                TimeUnit.SECONDS.sleep(2);
                drive_base.strafe(0.5);
                TimeUnit.MILLISECONDS.sleep(500);
                drive_base.stop();
            }
        }

    }

}



