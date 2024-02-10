//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous
//Replace "Template" with your new program's file name.
public final class LateralPushTelemetry extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        DcMotor Par0 = hardwareMap.get(DcMotor.class, "Fleft");
        DcMotor Par1 = hardwareMap.get(DcMotor.class, "Bleft");
        DcMotor Perp = hardwareMap.get(DcMotor.class, "IntakeMotor");

        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {


                double Par0T = Par0.getCurrentPosition();
                double Par1T = Par1.getCurrentPosition();
                double PerpT = Perp.getCurrentPosition();

                telemetry.update();
                telemetry.addData("Par 0 ticks:", Par0T);
                telemetry.addData("Par 1 ticks:", Par1T);
                telemetry.addData("Perp ticks", PerpT);

            }
        }
    }
}
