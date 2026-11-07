package org.firstinspires.ftc.teamcode.src;


import static java.lang.Math.sqrt;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.lib.DriveBase;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

@SuppressWarnings("unused")
@Autonomous(name="Secondary Competition Auto", group="Basic")
public class SecondaryCompAuto extends LinearOpMode {
    DriveBase drive_base = null;
    int step = 0;
    SparkFunOTOS Otos = null;



    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera
    @Override
    public void runOpMode() throws InterruptedException {
        //sets up THE "OTOS" and drive base
        initAprilTag();
        drive_base = new DriveBase(hardwareMap);
        Otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        Otos.calibrateImu();
        Otos.setAngularUnit(AngleUnit.DEGREES);
        Otos.setLinearUnit(DistanceUnit.CM);
        Otos.resetTracking();

        waitForStart();

        drive_base.drive(0.55); //makes robot move forward

        while (opModeIsActive()) {
            //gets the position of robot and prints it
            SparkFunOTOS.Pose2D pose = Otos.getPosition();
            telemetry.addData("x", pose.x);
            telemetry.addData("y", pose.y);
            telemetry.addData("h", pose.h);
            telemetry.update();

            if (pose.y > 28 && pose.x < 2 && pose.x > -2 && step == 0) { //when the robot reaches target y then checks if the x hasn't changed a significant amount then it stops and is strafes left
                drive_base.stop();
                drive_base.strafe(-0.55);
                step = 1;

            }
            if (pose.x < -75 && step == 1) { //when robot reaches target x then it stops
                drive_base.stop();
                step = 2;
                TimeUnit.SECONDS.sleep(5);
                visionPortal.resumeStreaming();
                List<AprilTagDetection> currentDetections = aprilTag.getDetections();
                for (AprilTagDetection detection : currentDetections) {


                    if (detection instanceof AprilTagClusterDetection) {
                        AprilTagClusterDetection singleDet = (AprilTagClusterDetection) detection;
                        drive_base.strafe(-detection.ftcPose.x);


                    }
                    //Add shoot code here
                    drive_base.strafe(0.55);

                }
                if (pose.x > 24) { //when robot reaches target x then it stops
                    drive_base.stop();
                }
                if (distance(pose.x, 30, pose.y, 33) < 3) { //if robot is close to 30,33 then it says "At Destination"
                    telemetry.addLine("At finish");

                }


            }

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
    private void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }

    }   // end method initAprilTag()
    private void telemetryAprilTag() {

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        telemetry.addData("# AprilTags Detected", currentDetections.size());

        // Step through the list of detections and display info for each one.
        for (AprilTagDetection detection : currentDetections) {
            if (detection instanceof AprilTagSingleDetection) {
                AprilTagSingleDetection singleDet = (AprilTagSingleDetection) detection;

                if (singleDet.metadata != null) {
                    telemetry.addLine(String.format("\n==== (ID %d) %s", singleDet.id, singleDet.metadata.name));
                    telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                    telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                    telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                } else {
                    telemetry.addLine(String.format("\n==== (ID %d) Unknown", singleDet.id));
                    telemetry.addLine(String.format("Center %6.0f %6.0f   (pixels)", singleDet.center.x, singleDet.center.y));
                }
            }  else {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                telemetry.addLine(String.format("\n==== Tag Cluster (%s)", clusterDet.metadata.name));
                telemetry.addLine(String.format("Percent tags found: %d", clusterDet.percentClusterFound));
                telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        telemetry.addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        telemetry.addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        telemetry.addLine("RBE = Range, Bearing & Elevation");

    }   // end method telemetryAprilTag()
}
