package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous

public final class TestAutoBackToStart extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .splineTo(new Vector2d(12,12), Math.toRadians(90))
                        .splineTo(new Vector2d(24,0), Math.toRadians(-90))
                        .splineTo(new Vector2d(12,-12), Math.toRadians(90))
                        .splineTo(new Vector2d(12,0), Math.toRadians(90))
                        .strafeTo(new Vector2d(0,0))
                        .turnTo(0)
                        .build());
    }
}
