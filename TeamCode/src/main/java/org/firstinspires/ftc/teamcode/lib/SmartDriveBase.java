package org.firstinspires.ftc.teamcode.lib;

import static java.lang.Math.*;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SmartDriveBase extends DriveBase {
    public Positioning positioning = null;
    SparkFunOTOS.Pose2D origin = null;

    public SmartDriveBase(HardwareMap hardwareMap) {
        super(hardwareMap);
        positioning = new Positioning(hardwareMap);
        origin = positioning.getPose();
    }

    public void smartDrive() {
        double dir_x = sin(origin.h);
        double dir_y = cos(origin.h);

        SparkFunOTOS.Pose2D current = positioning.getPose();

        double act_x = current.x - origin.x;
        double act_y = current.y - origin.y;
    }

    @Override
    public void stop() {
        super.stop();
        origin = positioning.getPose();
    }

    public void pointTowards(double heading) {
        SparkFunOTOS.Pose2D pose = positioning.getPose();
        double difference = RMath.angleDifference(heading,pose.h);
        double power = - difference * 0.5;
        turn(power);
    }
}
