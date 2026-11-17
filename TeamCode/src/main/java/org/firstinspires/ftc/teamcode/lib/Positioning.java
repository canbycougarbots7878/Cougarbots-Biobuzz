package org.firstinspires.ftc.teamcode.lib;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.ftccommon.internal.manualcontrol.parameters.ImuParameters;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagClusterDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagSingleDetection;

import java.util.List;
import java.util.Objects;

public class Positioning {
    SparkFunOTOS otos = null;
    IMU imu = null;

    AprilTagProcessor aprilTag;
    VisionPortal visionPortal;
    List<AprilTagDetection> aprilTagDetections;
    ElapsedTime timeSinceLastScan;

    public Positioning(HardwareMap hardwareMap) {
        otos = hardwareMap.get(SparkFunOTOS.class, "otos");
        otos.resetTracking();
        otos.calibrateImu();
        otos.setLinearUnit(DistanceUnit.CM);
        otos.setAngularUnit(AngleUnit.RADIANS);
        otos.setOffset(new SparkFunOTOS.Pose2D(-0.0762,0,0));
        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters params = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
                )
        );
        imu.initialize(params);

        initAprilTag(hardwareMap);
        timeSinceLastScan = new ElapsedTime();
        scanForApriltags();
    }

    // OTOS functions
    public void telemetryPose(Telemetry telemetry) {
        SparkFunOTOS.Pose2D pose = getPose();
        telemetry.addData("X",pose.x);
        telemetry.addData("Y",pose.y);
        telemetry.addData("H",pose.h);
    }

    public SparkFunOTOS.Pose2D getPose() {
        SparkFunOTOS.Pose2D pose = otos.getPosition();
        return pose;
    }

    public void setPose(SparkFunOTOS.Pose2D pose) {
        otos.setPosition(pose);
    }

    // IMU functions

    public YawPitchRollAngles getOrientation() {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        YawPitchRollAngles orientation_radians = new YawPitchRollAngles(
                AngleUnit.RADIANS,
                orientation.getYaw(AngleUnit.RADIANS),
                orientation.getPitch(AngleUnit.RADIANS),
                orientation.getRoll(AngleUnit.RADIANS),
                orientation.getAcquisitionTime()
        );
        return orientation_radians;
    }

    // Apriltag functions

    public void telemetryAprilTag(Telemetry telemetry) {
        List<AprilTagDetection> currentDetections = getAprilTagDetections();
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

    public void scanForApriltags() {
        aprilTagDetections = aprilTag.getDetections();
        timeSinceLastScan.reset();
    }

    public List<AprilTagDetection> getAprilTagDetections() {
        if(timeSinceLastScan.milliseconds() > 10) {
            // Expired scans
            scanForApriltags();
        }
        return aprilTagDetections;
    }

    public int getAprilTagCount() {
        return getAprilTagDetections().size();
    }

    public AprilTagClusterDetection lookForAprilTagCluster(String name) {
        List<AprilTagDetection> detections = getAprilTagDetections();
        for (AprilTagDetection detection : detections) {
            if (detection instanceof AprilTagClusterDetection) {
                AprilTagClusterDetection clusterDet = (AprilTagClusterDetection) detection;
                if(Objects.equals(clusterDet.metadata.name, name)) {
                    return clusterDet;
                }
            }
        }
        return null;
    }

    private void initAprilTag(HardwareMap hardwareMap) {
        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
    }
}
