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
@Disabled
@Autonomous
//Replace "Template" with your new program's file name.
public final class LateralPushTelemetry extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        DcMotor PerpEncoder = hardwareMap.get(DcMotor.class, "Fright");

        waitForStart();

        if (opModeIsActive()) {

            while (opModeIsActive()) {


                double Ticks = PerpEncoder.getCurrentPosition();

                telemetry.update();
                telemetry.addData("Current Ticks", Ticks);

            }
        }
    }
}
