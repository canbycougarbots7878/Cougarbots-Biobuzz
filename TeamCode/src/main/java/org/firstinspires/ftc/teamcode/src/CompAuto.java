package org.firstinspires.ftc.teamcode.src;

import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
/* Unused Imports (For now)
import com.qualcomm.robotcore.util.ElapsedTime;
 */
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.teamcode.lib.SmartDriveBase;

//archer is better


//pnuemonoultramicroscopicsilicovolcanoconiosis
//supercalifragalisticexpialidocious
//hippopotomonstrosesquippedaliophobia
//antidisestablishmentarianism
@SuppressWarnings("unused")
@Autonomous(name="Competition Autonomous", group="Basic")
public class CompAuto extends LinearOpMode {
    SmartDriveBase drive_base = null;
    boolean target1 = false;
    int step = 0;
    boolean target2 = false;
    @Override
    public void runOpMode() throws InterruptedException {
    //---------------------------------Setup------------------------------------------------------\\
        drive_base = new SmartDriveBase(hardwareMap);
        waitForStart();
        drive_base.setTarget(new SparkFunOTOS.Pose2D(28,0,0));

        while (opModeIsActive()) {
        //---------------Position Tracking--------------------------------------------------------//

            if (step == 0){
                drive_base.driveToTarget();
                telemetry.addLine("Driving to checkpoint 1/3");
                if (drive_base.distanceToTarget() < 4) {
                    step = 1;
                    drive_base.setTarget(-28,0);
                }
            }
            if (step == 1) {
                drive_base.driveToTarget();
                telemetry.addLine("Driving to checkpoint 2/3");
                if (drive_base.distanceToTarget() < 4) {
                    step = 2;
                    drive_base.setTarget(-28,200);
                }
            }
            if (step == 2) {
                drive_base.driveToTarget();
                telemetry.addLine("Driving to checkpoint 3/3");
                if (drive_base.distanceToTarget() < 4) {
                    step = 3;
                    drive_base.setTarget(-76,200);
                }
            }
            if (step == 3){
                telemetry.addLine("Driving Complete");
                drive_base.stop();
            }

            drive_base.positioning.telemetryPose(telemetry);
            telemetry.update();

        }

    }
    public double distance(double x1, double x2, double y1, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        return sqrt(dx*dx + dy*dy);
        /* AKA: distance  = sqrt(dx*dx + dy*dy);
              return distance
        */
    }

}
