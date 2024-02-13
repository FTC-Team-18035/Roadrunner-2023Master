//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


@Autonomous
//Replace "Template" with your new program's file name.
public final class WingSideAutonomous extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //The Pose2d function sets where your robot is going to start its trajectory from in X, Y, and heading (in radians or use "Math.toRadians" and input degrees).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();
        double location = 2;
        //run if found at position 2
        if(location == 2) {
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeTo(new Vector2d(-43.32281809, 0)) // 49 inches backwards
                            //reverse intake at .5 power
                            .strafeTo(new Vector2d(-43.32281809,79.22288262))
                            .turn(Math.toRadians(-89))
                            .strafeTo(new Vector2d(-43.32281809+25.6249204,79.22288262))
                            .build());

            drive.MoveLift(100);
            sleep(500);
            drive.RotateArm(-90);
            sleep(1000);
            drive.MoveLift(1950);
            sleep(1000);
            drive.RotateArm(880);
            sleep(500);


            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(-43.32281809+25.6249204, 79.22288262, -89))
                            .strafeTo(new Vector2d(-43.32281809+25.6249204, 79.22288262+5))
                            .build());

            drive.Claw1.setPosition(1);
            sleep(400);
            drive.Claw2.setPosition(1);
            sleep(500);

            /* drive.RotateArm(-90);
            sleep(1500);
            drive.MoveLift(100);
            sleep(1000);
            drive.RotateArm(0);
            sleep(500);
            drive.MoveLift(0);
             */
        }
    }
}
