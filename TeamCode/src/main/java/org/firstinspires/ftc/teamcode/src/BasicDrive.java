package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import math;
import org.firstinspires.ftc.teamcode.lib.Claw;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Lift;
import org.firstinspires.ftc.teamcode.lib.Shooter;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

@SuppressWarnings("unused")//not all tho
@TeleOp(name="Basic: Drive", group="Basic")
public class BasicDrive extends LinearOpMode {
    SmartDriveBase drive_base;
    boolean global = false;

    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            if(global) {
                drive_base.globalMoveWithController(gamepad1,0.4);
            }
            else {
                drive_base.moveWithController(gamepad1,0.4);
            }

            if(gamepad1.xWasPressed()) {
                global = !global; // Toggle global movement
            }

            if(global) {
                telemetry.addLine("Global Move");
            }
            else {
                telemetry.addLine("Local Move");
            }
            telemetry.update();
        }
    }
}
