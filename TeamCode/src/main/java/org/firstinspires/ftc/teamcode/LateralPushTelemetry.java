//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.MecanumDrive;
@TeleOp(name = "Eli's Lateral Test")
//Replace "Template" with your new program's file name.
public final class LateralPushTelemetry extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        DcMotor PerpEncoder = hardwareMap.get(DcMotor.class, "Fright");
        DcMotor Par0Encoder = hardwareMap.get(DcMotor.class, "Fleft");
        DcMotor Par1Encoder = hardwareMap.get(DcMotor.class, "Bleft");

        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {


                double TicksPerp = PerpEncoder.getCurrentPosition();
                double TicksPar0 = Par0Encoder.getCurrentPosition();
                double TicksPar1 = Par1Encoder.getCurrentPosition();


                telemetry.addData("Perpendicular Ticks", TicksPerp);
                telemetry.addData("Parallel 0 Ticks", TicksPar0);
                telemetry.addData("Parallel 1 Ticks", TicksPar1);
                telemetry.update();
            }
        }
    }
}
