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
public final class EfficentGantryMotionTest extends LinearOpMode {
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));

        drive.Claw2.setPosition(0);
        drive.Claw1.setPosition(0);

        waitForStart();

        drive.MoveLift(100);
        sleep(500);
        drive.RotateArm(-90);
        sleep(600); //delay after initial backswing
        drive.MoveLift(1600);
        sleep(800);
        drive.RotateArm(880);
        sleep(800); //was 1000

        drive.Claw2.setPosition(1);
        drive.Claw1.setPosition(1);
        sleep(500); //was 1000

        drive.RotateArm(-90);
        sleep(500);
        drive.MoveLift(100);
        sleep(1000);
        drive.RotateArm(0);
        sleep(500);
        drive.MoveLift(0);
    }
}
