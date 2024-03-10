//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@TeleOp(name = "Eli's Lateral Test")
//Replace "Template" with your new program's file name.
public final class LateralPushTelemetry extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        final IMU imu;
        waitForStart();
        imu = hardwareMap.get(IMU.class, "imu");
        if (opModeIsActive()) {
            imu.resetYaw();
            while (opModeIsActive()) {
                double Yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
                telemetry.addData("Yaw:", Yaw);
                double Pitch = imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
                telemetry.addData("Pitch:", Pitch);
                double Roll = imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES);
                telemetry.addData("Roll:", Roll);
                telemetry.update();
            }
        }
    }
}
