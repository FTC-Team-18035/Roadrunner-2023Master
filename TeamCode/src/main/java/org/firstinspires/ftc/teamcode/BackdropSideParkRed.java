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
@Autonomous(name = "Backdrop Only Park Red")
//Replace "Template" with your new program's file name.
public final class BackdropSideParkRed extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //The Pose2d function sets where your robot is going to start its trajectory from in X, Y, and heading (in radians or use "Math.toRadians" and input degrees).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeTo(new Vector2d(.5,0))   //drives forward slightly to prevent scraping against the wall
                        .strafeTo(new Vector2d(0,-26))  //drives right 26"
                        .build());
    }
}
