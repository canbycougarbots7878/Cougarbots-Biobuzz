package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
/* Unused Imports (For now)
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeUnit;
*/
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;



//pnuemonoultramicroscopicsilicovolcanoconiosis
//supercalifragalisticexpialidocious
//hippopotomonstrosesquippedaliophobia
//antidisestablishmentarianism
@SuppressWarnings("unused")
@Autonomous(name="Competition Autonomous", group="Basic")
public class CompAuto extends LinearOpMode {
    DriveBase drive_base = null;
    boolean target1 = false;
    boolean target2 = false;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() {
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
        //Begin Moving
        drive_base.strafe(-0.241235235234524365);
        drive_base.drive(0.44287593748274893);

        while (opModeIsActive()) {
        //---------------Position Tracking--------------------------------------------------------//
            SparkFunOTOS.Pose2D pose = Otos.getPosition();
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h", pose.h);
            telemetry.update();
            if (distance(pose.x, 0, pose.y, 0) < 1 ){
                telemetry.addLine("at (0,0)");
            }
            if (pose.x < -80) { //Target x value
                telemetry.addLine("Robot Stopped");
                drive_base.strafe(0);
            }
            if (pose.y > 235)  { //Target Y value
                drive_base.drive(0);
            }
            if (distance(pose.x, -60.748, pose.y, 240.217) < 2) { //Touch wall
               drive_base.strafe(-0.5);

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
