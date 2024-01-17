package org.firstinspires.ftc.teamcode.tuning;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;
@Autonomous
public final class MTest2Red extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

            waitForStart();
//I added the code between these comments
//strafe 42" right
            Actions.runBlocking(
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeTo(new Vector2d(0, -42))
                            .build());

//I added the code between these comments

        } else {
            throw new AssertionError();
        }
    }
}
