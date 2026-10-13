package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

@TeleOp(name="Basic: Drive", group="Basic")
public class SimpleDrive extends LinearOpMode {
    SmartDriveBase drive_base = null;

    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            double forward = - gamepad1.left_stick_y;
            double right = gamepad1.left_stick_x;
            double clockwise = - gamepad1.right_stick_x;

            drive_base.globalMove(forward,right);
        }
    }
}
