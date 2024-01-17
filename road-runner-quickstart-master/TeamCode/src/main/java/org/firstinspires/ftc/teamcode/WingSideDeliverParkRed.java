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
                        .strafeTo(new Vector2d(43,0))   //moves forward 49.5"
                        .waitSeconds(2.5)
                        .strafeTo(new Vector2d(43,-86)) //moves right 86"
                        .waitSeconds(2.5)
                        .turnTo(Math.toRadians(90)) //turns 90 degrees counterclockwise
                        .strafeTo(new Vector2d(27, -86))    //moves left 16"
                        //**Raise gantry and move claws to delivery position here**\\
                        .strafeTo(new Vector2d(27, -89))    //moves backwards " to get into deliver position
                        //**open claws here**
                        .build());
    }
}
