package org.firstinspires.ftc.teamcode.lib;

import static java.lang.Math.abs;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

@SuppressWarnings("unused")
public class DriveBase {
    public DcMotor front_right;
    public DcMotor front_left;
    public DcMotor back_right;
    public DcMotor back_left;
    public double power_factor; // All wheel powers will be scaled by this factor

    private double forward = 0;
    private double right = 0;
    private double clockwise = 0;

    // Constructor
    public DriveBase(HardwareMap hardwareMap)  {
        this.front_right = hardwareMap.get(DcMotor.class, "frontright");
        this.front_left = hardwareMap.get(DcMotor.class, "frontleft");
        this.back_right = hardwareMap.get(DcMotor.class, "backright");
        this.back_left = hardwareMap.get(DcMotor.class, "backleft");

        // Reverse left motors so all motors spin same direction
        this.front_left.setDirection(DcMotorSimple.Direction.REVERSE);
        this.back_left.setDirection(DcMotorSimple.Direction.REVERSE);

        this.power_factor = 1.0;
    }

    // Raw wheel control
    private void setWheelPowers(double Front_Right_Power, double Front_Left_Power, double Back_Right_Power, double Back_Left_Power) {
        this.front_right.setPower(Front_Right_Power);
        this.front_left.setPower(Front_Left_Power);
        this.back_right.setPower(Back_Right_Power);
        this.back_left.setPower(Back_Left_Power);
    }
    private void updateMovement() {
        double fl = forward + right - clockwise;
        double fr = forward - right + clockwise;
        double bl = forward - right - clockwise;
        double br = forward + right + clockwise;

        double max = Math.max(1.0, Math.max(abs(fl),
                Math.max(abs(fr), Math.max(abs(bl), abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        this.setWheelPowers(fr * power_factor, fl * power_factor, br * power_factor, bl * power_factor);
    }

    public void move(double forward, double right, double clockwise) {
        this.forward = forward;
        this.right = right;
        this.clockwise = clockwise;
        this.updateMovement();
    }
    public void drive(double forward) {
        this.forward = forward;
        this.updateMovement();
    }
    public void strafe(double right) {
        this.right = right;
        this.updateMovement();
    }
    public void turn(double clockwise) {
        this.clockwise = clockwise;
        this.updateMovement();
    }
    public void stop() {
        move(0,0,0);
    }
    public void moveWithController(Gamepad gamepad, double speed_mod) {
        this.forward = - gamepad.left_stick_y * speed_mod;
        this.right = gamepad.left_stick_x * speed_mod;
        this.clockwise = - gamepad.right_stick_x * speed_mod;
        this.updateMovement();
    }
}
