package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import math;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

@SuppressWarnings("unused")//not all tho
@TeleOp(name="Competition: TeleOp", group="Basic")
public class CompTeleop extends LinearOpMode {
    SmartDriveBase drive_base = null;
    DcMotor intake_motor = null;
    DcMotor mover_motor = null;
    DcMotorEx launcher_motor = null;

    boolean prm_mode = true;
    double launcher_power = 0.65;

    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);
        intake_motor = hardwareMap.get(DcMotor.class, "intake");
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        mover_motor = hardwareMap.get(DcMotor.class, "mover");
        launcher_motor = hardwareMap.get(DcMotorEx.class, "launcher");
        launcher_motor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        while (opModeIsActive()) {
            if(prm_mode) {
                drive_base.globalMoveWithController(gamepad1,true);
            }
            else {
                drive_base.moveWithController(gamepad1,1.0);
            }

            if(gamepad1.xWasPressed()) {
                prm_mode = !prm_mode;
            }

            if(gamepad1.rightTriggerWasPressed()) {
                launcher_motor.setPower(launcher_power);
            }
            else if(gamepad1.rightBumperWasPressed()) {
                launcher_motor.setPower(-0.5);
            }
            else if(gamepad1.rightBumperWasReleased() || gamepad1.rightTriggerWasReleased()) {
                launcher_motor.setPower(0);
            }

            if(gamepad1.leftTriggerWasPressed()) {
                mover_motor.setPower(0.5);
                intake_motor.setPower(0.5);
            }
            else if(gamepad1.leftBumperWasPressed()) {
                mover_motor.setPower(-0.5);
                intake_motor.setPower(-0.5);
            }
            else if(gamepad1.leftBumperWasReleased() || gamepad1.leftTriggerWasReleased()) {
                mover_motor.setPower(0);
                intake_motor.setPower(0);
            }

            if(gamepad1.dpadUpWasPressed()) {
                launcher_power += 0.05;
            }
            if(gamepad1.dpadDownWasPressed()) {
                launcher_power -= 0.05;
            }

            drive_base.positioning.telemetryPose(telemetry);
            telemetry.addData("Clusters Detected",drive_base.positioning.getAprilTagCount());
            telemetry.addData("Launcher Power",launcher_power);
            telemetry.addData("Launcher Speed",launcher_motor.getVelocity());
            if(prm_mode) telemetry.addLine("Player Relative Movement");
            else telemetry.addLine("Robot Relative Movement");
            telemetry.update();
        }
    }
}
