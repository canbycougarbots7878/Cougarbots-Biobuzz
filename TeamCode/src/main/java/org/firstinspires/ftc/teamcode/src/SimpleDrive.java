package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.lib.DriveBase;

@TeleOp(name="Basic: Drive", group="Basic")
public class SimpleDrive extends LinearOpMode {
    DriveBase drive_base = null;

    @Override
    public void runOpMode() {
        drive_base = new DriveBase(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            drive_base.omniMoveController(gamepad1, 0.4);
        }
    }
}
