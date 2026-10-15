package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import math;
import org.firstinspires.ftc.teamcode.lib.Claw;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Lift;
import org.firstinspires.ftc.teamcode.lib.Shooter;

@SuppressWarnings("unused")//not all tho
@TeleOp(name="Basic: Drive", group="Basic")
public class BasicDrive extends LinearOpMode {
    DriveBase drive_base;

    @Override
    public void runOpMode() {
        drive_base = new DriveBase(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            drive_base.drive(1);
        }
    }
}
