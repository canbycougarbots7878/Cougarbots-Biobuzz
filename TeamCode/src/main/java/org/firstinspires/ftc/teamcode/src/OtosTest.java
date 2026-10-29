package org.firstinspires.ftc.teamcode.src;
import static java.lang.Math.sqrt;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
/*----------------------------Unused Import Statements-----------------------------------------\\
import org.firstinspires.ftc.teamcode.lib.Positioning;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.sql.Time;
*/

@SuppressWarnings("unused")
@Autonomous(name="OtosTest", group="Basic")
public class OtosTest extends LinearOpMode {
    DriveBase drive_base = null;

    SparkFunOTOS Otos = null;
    @Override
    public void runOpMode() throws InterruptedException {
    // ---------------------------Setup--------------------------------------\\
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.setAngularUnit(AngleUnit.DEGREES);
        Otos.setLinearUnit(DistanceUnit.CM);
        SparkFunOTOS.Pose2D starting_pose = new SparkFunOTOS.Pose2D(0,0, 90);
        Otos.setPosition(starting_pose);
        Otos.resetTracking();

        waitForStart();

        while (opModeIsActive()) {
    // ----------------------OTOS Position Tracking--------------------------\\
            SparkFunOTOS.Pose2D pose = Otos.getPosition();
            telemetry.addData("x",pose.x);
            telemetry.addData("y",pose.y);
            telemetry.addData("h",pose.h);
            telemetry.update();
            if (distance(pose.x, 0, pose.y, 0) < 1 ){
                telemetry.addLine("at (0,0)");

            }

        }

    }
    //------------------------------Distance Formula-------------------------\\
    public double distance(double x1, double x2, double y1, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        final double distance = sqrt(dx*dx + dy*dy);

        return distance;



    }

}
