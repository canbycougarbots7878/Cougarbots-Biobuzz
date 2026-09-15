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
            drive_base.omniMove();
        }
    }
}
