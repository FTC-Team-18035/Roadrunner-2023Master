//The following program is a template for RoadRunner programs in autonomous.
//This package and the following imports are required.
package org.firstinspires.ftc.teamcode;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
@Disabled
@Autonomous
//Replace "Template" with your new program's file name.
public final class JamesAutoTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        //The Pose2d function sets where your robot is going to start its trajectory from in X, Y, and heading (in radians or use "Math.toRadians" and input degrees).
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        waitForStart();
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        //ALL FOLLOWING DISTANCE UNITS ARE IN INCHES!
                        //Travels forward and back after turning to face the specified point (positive is forwards).
                        //.lineToY(24)
                        //Travels side to side after turning to face the specified point (positive is left).
                        //.lineToX(-24)
                        .strafeTo(new Vector2d(24, 0))
                        .strafeTo(new Vector2d(24, -24))
                        //Turns a specified number of radians, or use "Math.toRadians(degrees)" (positive is counter-clockwise).
                        .turn(Math.toRadians(360))
                        //Waits a specified amount of time
                        .waitSeconds(5)
                        //Defines a curved path to a specified point, ending an the OPTIONAL heading.
                        .splineTo(new Vector2d(12,-12), Math.toRadians(0))
                        //Executes the predefined trajectory.
                        .build());
    }
}