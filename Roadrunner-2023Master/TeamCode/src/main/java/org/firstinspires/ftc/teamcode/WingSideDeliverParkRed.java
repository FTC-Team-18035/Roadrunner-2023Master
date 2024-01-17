//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
//Replace "Template" with your new program's file name.
public final class WingSideDeliverParkRed extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //The Pose2d function sets where your robot is going to start its trajectory from in X, Y, and heading (in radians or use "Math.toRadians" and input degrees).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeTo(new Vector2d(44,0))   //moves forward 49.5"
                        .waitSeconds(.5)
                        .strafeTo(new Vector2d(44,-86)) //moves right 86"
                        .waitSeconds(.5)
                        .turnTo(Math.toRadians(90)) //turns 90 degrees counterclockwise
                        .strafeTo(new Vector2d(28, -86))    //moves left 16"
                        .build());

                drive.MoveLift(100);
                sleep(1000);
                drive.RotateArm(-90);
                sleep(2000);
                drive.MoveLift(1950);
                sleep(2000);
                drive.RotateArm(880);
                sleep(1000);

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeTo(new Vector2d(-3,0))   //moves forward 49.5"
                        .build());


                drive.Claw1.setPosition(1);
                sleep(400);
                drive.Claw2.setPosition(1);
                sleep(100);
                drive.actionBuilder(new Pose2d(0,0,0))
                        .strafeTo(new Vector2d(3,0))
                        .build();
                sleep(400);
                drive.RotateArm(-90);
                drive.MoveLift(100);
                drive.RotateArm(0);
                drive.MoveLift(0);


    }
}
