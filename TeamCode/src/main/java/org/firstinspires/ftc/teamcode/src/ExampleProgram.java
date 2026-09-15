package org.firstinspires.ftc.teamcode.src;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import math;
import org.firstinspires.ftc.teamcode.lib.Claw;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.Lift;

@SuppressWarnings("unused")//not all tho
@TeleOp(name="Basic: Control", group="Basic")
public class ExampleProgram extends LinearOpMode {
    public boolean clawopen = true;
    DriveBase drive_base = null;
    Lift lift = null;
    Claw claw = null;
    SparkFunOTOS otos = null;
    @Override
    public void runOpMode() {
        drive_base = new DriveBase(hardwareMap);
        lift = new Lift(hardwareMap);
        claw = new Claw(hardwareMap);
        otos = hardwareMap.get(SparkFunOTOS.class, "otos");

        waitForStart();
        while (opModeIsActive()) {
            drive_base.omniMoveController(gamepad1,0.4);

            if(gamepad1.dpad_up) {
                lift.move(1);
            }
            else if(gamepad1.dpad_down) {
                lift.move(-1);
            }
            else {
                lift.stop();
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
            telemetry.update();
        }
    }
}
