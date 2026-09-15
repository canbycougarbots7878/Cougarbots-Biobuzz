package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

@SuppressWarnings("unused")
@Autonomous(name="Basic Auto", group="Basic")
public class BasicAuto extends LinearOpMode {
    DriveBase drive_base = null;
    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() {
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        waitForStart();
        while (opModeIsActive()) {
            SparkFunOTOS.Pose2D pose = Otos.getPosition();
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();
        }
    }
}
