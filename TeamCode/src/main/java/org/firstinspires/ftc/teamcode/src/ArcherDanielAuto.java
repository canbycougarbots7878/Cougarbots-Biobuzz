package org.firstinspires.ftc.teamcode.src;


import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import java.util.concurrent.TimeUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

/*  GOALS -
       1. Reach target position: (-20, 50)   yes
       2. Check and print something if target was reached    yes
       3. Add comments to improve readability + be able to explain program to someone else   yes

*/
@SuppressWarnings("unused")
@Autonomous(name="Archer&&DanielAuto", group="Basic")
public class ArcherDanielAuto extends LinearOpMode {
    DriveBase drive_base = null;
    int step = 0;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() throws InterruptedException {
        //sets up THE "OTOS" and drive base
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.setAngularUnit(AngleUnit.DEGREES);
        Otos.setLinearUnit(DistanceUnit.CM);
        Otos.resetTracking();

        waitForStart();

        drive_base.drive(0.55); //makes robot move forward

        while (opModeIsActive()) {
            //gets the position of robot and prints it
            SparkFunOTOS.Pose2D pose = Otos.getPosition();
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();

            if (pose.y > 33 && pose.x < 2 && pose.x > -2 && step == 0){ //when the robot reaches target y then checks if the x hasn't changed a significant amount then it stops and is strafes left
                drive_base.stop();
                drive_base.strafe(-0.55);
                step = 1;

            }
            if (pose.x < -80 && step == 1){ //when robot reaches target x then it stops
                drive_base.stop();
                step = 2;
                TimeUnit.SECONDS.sleep(5);
                //Add shoot code here
                drive_base.strafe(0.55);

            }
            if (pose.x > 28) { //when robot reaches target x then it stops
                drive_base.stop();
            }
            if (distance(pose.x, 34, pose.y, 37) < 3 ){ //if robot is close to -20,50 then it says "At Destination"
                telemetry.addLine("At finish");

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
