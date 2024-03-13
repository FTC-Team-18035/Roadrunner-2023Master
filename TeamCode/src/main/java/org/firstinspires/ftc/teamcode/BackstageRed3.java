//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Disabled
@Autonomous
//Replace "Template" with your new program's file name.
public final class BackstageRed3 extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //The Pose2d function sets where your robot is going to start its trajectory from in X, Y, and heading (in radians or use "Math.toRadians" and input degrees).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        drive.PPD(0);
        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        //.waitSeconds(5) add this in to coordinate autonomous
                        .strafeTo(new Vector2d(0, 22))
                        .strafeTo(new Vector2d(-32,22))
                        .turn(Math.toRadians(204))
                        .build());

        drive.PPD(1);
        sleep(1000);

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-32, 22, Math.toRadians(180)))
                        .strafeTo(new Vector2d(-30, 30))
                        .turn(Math.toRadians(105))
                        .build());

        drive.MoveLift(100);
        sleep(500);
        drive.RotateArm(-90);
        sleep(600); //delay after initial backswing
        drive.MoveLift(1200);
        sleep(800);
        drive.RotateArm(880);
        sleep(800); //was 1000

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-30, 30, Math.toRadians(-90)))
                        .strafeTo(new Vector2d(-19, 43))
                        .build());

        drive.Claw2.setPosition(1);
        drive.Claw1.setPosition(1);
        sleep(500); //was 1000

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-19, 43, Math.toRadians(-90)))
                        .strafeTo(new Vector2d(-19, 36))
                        .strafeTo(new Vector2d(1, 36))
                        .build());

        drive.RotateArm(-90);
        sleep(500);
        drive.MoveLift(100);
        sleep(1000);
        drive.RotateArm(0);
        sleep(500);
        drive.MoveLift(0);
        sleep(500);
    }
}
