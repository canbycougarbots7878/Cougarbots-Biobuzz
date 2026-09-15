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
    public void setWheelPowers(double Front_Right_Power, double Front_Left_Power, double Back_Right_Power, double Back_Left_Power) {
        this.front_right.setPower(Front_Right_Power);
        this.front_left.setPower(Front_Left_Power);
        this.back_right.setPower(Back_Right_Power);
        this.back_left.setPower(Back_Left_Power);
    }
    public void stop() {
        setWheelPowers(0,0,0,0);
    }
    public void turn(double speed) {
        double fl = front_left.getPower() - speed;
        double fr = front_right.getPower() + speed;
        double bl = front_left.getPower() - speed;
        double br = front_left.getPower() + speed;
        setWheelPowers(fr,fl,br,bl);
    }
    public void omniMove(double Forward, double Right, double Rotate) {
        // THIS HAS BEEN FINICKY
        double fl = Forward + Right - Rotate;
        double fr = Forward - Right + Rotate;
        double bl = Forward - Right - Rotate;
        double br = Forward + Right + Rotate;

        // normalize so no value exceeds 1
        double max = Math.max(1.0, Math.max(abs(fl),
                Math.max(abs(fr), Math.max(abs(bl), abs(br)))));

        fl /= max;
        fr /= max;
        bl /= max;
        br /= max;

        this.setWheelPowers(fr * power_factor, fl * power_factor, br * power_factor, bl * power_factor);
    }
    public void omniMoveController(Gamepad gamepad, double speed_mod) {
        double forward = - gamepad.left_stick_y * speed_mod;
        double strafe = gamepad.left_stick_x * speed_mod;
        double turn = - gamepad.right_stick_x * speed_mod;
        this.omniMove(forward,strafe,turn);
    }
}
