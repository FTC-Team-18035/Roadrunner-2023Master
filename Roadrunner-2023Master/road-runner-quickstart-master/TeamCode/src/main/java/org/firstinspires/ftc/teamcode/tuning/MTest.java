package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;
@Autonomous
public final class MTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

            waitForStart();
//I added the code between these comments
//drives forward 50", turns 90 degrees, drives forward 95"
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .lineToX(50)
                            .turnTo(Math.toRadians(90))
                            .strafeTo(new Vector2d(50, -95))
                            .build());

//I added the code between these comments

        } else {
            throw new AssertionError();
        }
    }
}
