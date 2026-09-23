package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
//import math;
import org.firstinspires.ftc.teamcode.lib.Claw;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Lift;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

@SuppressWarnings("unused")//not all tho
@TeleOp(name="Basic: Control", group="Basic")
public class ExampleProgram extends LinearOpMode {
    public boolean clawopen = true;
    SmartDriveBase drive_base = null;
    Lift lift = null;
    Claw claw = null;
    SparkFunOTOS otos = null;
    @Override
    public void runOpMode() {
        drive_base = new SmartDriveBase(hardwareMap);
        lift = new Lift(hardwareMap);
        claw = new Claw(hardwareMap);
        otos = hardwareMap.get(SparkFunOTOS.class, "otos");

        waitForStart();
        while (opModeIsActive()) {

            if(gamepad1.dpad_up) {
                lift.move(1);
            }
            else if(gamepad1.dpad_down) {
                lift.move(-1);
            }
            else {
                lift.stop();
            }

            if(gamepad1.a) {
                drive_base.pointTowards(0);
            }
            else {
                drive_base.moveWithController(gamepad1,0.4);
            }

            if(gamepad1.rightBumperWasPressed()) { //claw
                if (clawopen) {
                    claw.close(); //CloseClaw.close
                    clawopen = false;
                } else {
                    claw.open(); //opencclaw.open
                    clawopen = true;
                }

            }

            SparkFunOTOS.Pose2D pose = drive_base.positioning.getPose();
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();
        }
    }
}
