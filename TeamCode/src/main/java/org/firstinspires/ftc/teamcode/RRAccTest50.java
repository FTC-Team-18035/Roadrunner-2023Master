
package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous
public final class RRAccTest50 extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeTo(new Vector2d(-44.37434279705573,0))
                        .turnTo(Math.toRadians(-90))
                        .strafeTo(new Vector2d(-44.37434279705573, -12.8286014721346))
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
                drive.actionBuilder(new Pose2d(0, 0, 0))

                        .build());
    }
}
