package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
/* Unused Imports (For now)
import com.qualcomm.robotcore.util.ElapsedTime;
 */
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

//archer is better


//pnuemonoultramicroscopicsilicovolcanoconiosis
//supercalifragalisticexpialidocious
//hippopotomonstrosesquippedaliophobia
//antidisestablishmentarianism
@SuppressWarnings("unused")
@Autonomous(name="Competition Autonomous", group="Basic")
public class CompAuto extends LinearOpMode {
    DriveBase drive_base = null;
    boolean target1 = false;
    int step = 0;
    boolean target2 = false;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() throws InterruptedException {
    //---------------------------------Setup------------------------------------------------------\\
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.setAngularUnit(AngleUnit.DEGREES);
        Otos.setLinearUnit(DistanceUnit.CM);
        SparkFunOTOS.Pose2D starting_pose = new SparkFunOTOS.Pose2D(0,0, 90);
        //reset OTOS Position:
        Otos.setPosition(starting_pose);
        Otos.resetTracking();

        waitForStart();
        drive_base.strafe(0.4);

        while (opModeIsActive()) {
        //---------------Position Tracking--------------------------------------------------------//
            SparkFunOTOS.Pose2D pose = Otos.getPosition();
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h", pose.h);
            telemetry.update();

            if (pose.x > 28 && step == 0){
                drive_base.stop();
                TimeUnit.SECONDS.sleep(5);
                //shoot code goes here
                step = 1;
                drive_base.strafe(-0.4);
            }
            if (pose.x < -28 && step== 1){
                drive_base.stop();
                step = 2;
                drive_base.drive(0.4);
            }
            if (pose.y > 200.1 && step == 2){
                drive_base.stop();
                step = 3;
                drive_base.strafe(-0.4);
            }
            if (pose.x < -76 && step == 3){
                drive_base.stop();
                step = 4;
            }
            if (distance(-81, pose.x, 272, pose.y)< 10){
                telemetry.addLine("At End Archer is better");
            }
        }

    }
    public double distance(double x1, double x2, double y1, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        return sqrt(dx*dx + dy*dy);
        /* AKA: distance  = sqrt(dx*dx + dy*dy);
              return distance
        */
    }

}
