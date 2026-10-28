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

import java.util.concurrent.TimeUnit;

@TeleOp(name="Smart Drive Base Test", group="Basic")
public class SmartDriveBaseTest extends LinearOpMode {
    SmartDriveBase drive_base;

    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D pose = drive_base.positioning.getPose();
            if(gamepad1.dpad_up) {
                drive_base.setTarget(0,40);
            }
            else if(gamepad1.dpad_down) {
                drive_base.setTarget(0,-40);
            }
            else if(gamepad1.dpad_right) {
                drive_base.setTarget(40,0);
            }
            else if(gamepad1.dpad_left) {
                drive_base.setTarget(-40,0);
            }
            else {
                drive_base.setTarget(0,0);
            }
            drive_base.driveToTarget();

            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h", pose.h);
            telemetry.update();
        }
    }
    public double falloff(double x, double t) {
        return Math.min(1,Math.abs(x)/t);
    }
}
