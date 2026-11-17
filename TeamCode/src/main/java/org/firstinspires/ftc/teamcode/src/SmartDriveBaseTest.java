package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Positioning;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;

import java.util.concurrent.TimeUnit;

@TeleOp(name="Smart Drive Base Test", group="Basic")
public class SmartDriveBaseTest extends LinearOpMode {
    SmartDriveBase drive_base;

    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            AprilTagClusterDetection scoring = drive_base.positioning.lookForAprilTagCluster("BLUE SCORING");
            if(scoring != null) {
                double x = scoring.ftcPose.x;
                double bearing = scoring.ftcPose.bearing;
                //drive_base.strafe(falloff(-x,10) * 0.6);
                drive_base.turn(falloff(bearing,45) * 0.6);
            }
            else {
                drive_base.turn(0.4);
            }
            drive_base.globalMoveWithController(gamepad1, false);

            drive_base.positioning.telemetryAprilTag(telemetry);
            telemetry.update();
        }
    }
    public double falloff(double x, double t) {
        double magnitude = Math.abs(x);
        double direction = Math.signum(x);
        return Math.min(1,magnitude/t) * direction;
    }
}
