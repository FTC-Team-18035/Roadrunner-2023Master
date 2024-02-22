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
public final class BackdropRed3Spike extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //The Pose2d function sets where your robot is going to start its trajectory from in X, Y, and heading (in radians or use "Math.toRadians" and input degrees).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        //.waitSeconds(5) add this in to coordinate autonomous
                        .strafeTo(new Vector2d(-43.5,0))   //moves backwards 49.5"
                        .strafeTo(new Vector2d(-43.5, 11))
                        .build());

        drive.ActivateIntake(-.70);
        sleep(1000);
        drive.ActivateIntake(0);

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-43.5,11,0))
                        .strafeTo(new Vector2d(-43.5,28)) //moves left 83"
                        .waitSeconds(.1)
                        .turnTo(Math.toRadians(-90)) //turns 90 degrees clockwise
                        .strafeTo(new Vector2d(-17, 28))    //moves left 16"
                        .strafeTo(new Vector2d(-17, 38))    //moves towards backdrop
                        .build());

        drive.MoveLift(100);
        sleep(500);
        drive.RotateArm(-90);
        sleep(1000);
        drive.MoveLift(1950);
        sleep(1000);
        drive.RotateArm(880);
        sleep(500);
        drive.Claw1.setPosition(1);
        sleep(400);
        drive.Claw2.setPosition(1);
        sleep(500);
        drive.RotateArm(-90);
        sleep(1500);
        drive.MoveLift(100);
        sleep(1000);
        drive.RotateArm(0);
        sleep(500);
        drive.MoveLift(0);

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-17,38,Math.toRadians(-90))) //If it still turns reset to 0 (Heading)
                        .strafeTo(new Vector2d(-17,34))
                        .waitSeconds(.5)
                        .strafeTo(new Vector2d(-46, 34))
                        .build());


    }
}
