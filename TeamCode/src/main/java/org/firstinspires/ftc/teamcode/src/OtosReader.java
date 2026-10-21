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
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Positioning;

import java.util.concurrent.TimeUnit;

@TeleOp(name="Otos Reader", group="Basic")
public class OtosReader extends LinearOpMode {
    Positioning positioning;
    @Override
    public void runOpMode() {
        positioning = new Positioning(hardwareMap);

        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D pose = positioning.getPose();

            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h", pose.h);
            telemetry.update();
        }
    }
}
