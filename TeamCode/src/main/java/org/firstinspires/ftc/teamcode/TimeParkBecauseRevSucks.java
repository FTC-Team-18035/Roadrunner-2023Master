package org.firstinspires.ftc.teamcode;

import android.animation.FloatEvaluator;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.security.Provider;

@Autonomous(name = "Driving Backwards Park", preselectTeleOp = "MainTeleOP")
public class TimeParkBecauseRevSucks extends LinearOpMode {
    DcMotor Fleft, Fright, Bright, Bleft;

    Servo Claw1, Claw2;

    @Override
    public void runOpMode(){
        Fleft = hardwareMap.get(DcMotorEx.class, "Fleft");
        Fright = hardwareMap.get(DcMotorEx.class, "Fright");
        Bright = hardwareMap.get(DcMotorEx.class, "Bright");
        Bleft = hardwareMap.get(DcMotorEx.class, "Bleft");

        Bleft.setDirection(DcMotorSimple.Direction.REVERSE);
        Fright.setDirection(DcMotorSimple.Direction.REVERSE);

        Fleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Fright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Bright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Bleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        Fleft.setPower(.5);
        Fright.setPower(.5);
        Bright.setPower(.5);
        Bleft.setPower(.5);
        sleep(2000);
        Fleft.setPower(0);
        Fright.setPower(0);
        Bright.setPower(0);
        Bleft.setPower(0);
    }

}
