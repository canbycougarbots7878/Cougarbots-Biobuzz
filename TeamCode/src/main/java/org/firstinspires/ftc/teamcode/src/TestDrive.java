package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

//"Please work" - Garrett
@TeleOp(name="Test: Drive", group="Basic")
public class TestDrive extends LinearOpMode {
    SmartDriveBase drive_base = null;
    DcMotor main_spinner = null;
    CRServo servo = null;
    double SPINNER_VELOCITY = 1150;
    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);
        main_spinner = hardwareMap.get(DcMotor.class, "mainspinner");
        servo = hardwareMap.get(CRServo.class, "intake");


        waitForStart();
        while (opModeIsActive()) {
            double forward = - gamepad1.left_stick_y;
            double right = gamepad1.left_stick_x;
            double clockwise = - gamepad1.right_stick_x;

            drive_base.globalMove(forward,right);
            if (gamepad1.rightBumperWasPressed()) {
                setSpinnerPower(SPINNER_VELOCITY);
            }else {
                stopSpinners();
            }
            if (gamepad1.xWasPressed()) {
                servo.setPower(.5);
            }else{
                servo.setPower(0);
            }
        }

    }
    private void setSpinnerPower(double power) {
        main_spinner.setPower(power);

    }
    private void stopSpinners() {
        setSpinnerPower(0);
    }
}
