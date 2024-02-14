package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "Purple Pixel Place Test", preselectTeleOp = "MainTeleOp")
public class PurplePixelSkeletonDelivery extends LinearOpMode {
    int Parking = 0; //0 = right side of the backdrop. 1 = left side of the backdrop
    public void runOpMode(){
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        if(gamepad1.a){ Parking = 1; }
        else if(gamepad1.b){ Parking = 0; }
        telemetry.addData("Parking Position", Parking);
        telemetry.update();
        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .strafeTo(new Vector2d(0, 0))//Distance to spike mark
                        .build());

        drive.ActivateIntake(.5);
        sleep(1000);
        drive.ActivateIntake(0);

        Actions.runBlocking(                                //Make sure these are the last position it was
                drive.actionBuilder(new Pose2d(0, 0, 0))
                        .turn(Math.toRadians(-90))
                        .strafeTo(new Vector2d(0, 0))//The position of in front of the backdrop
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
        if(Parking == 1){
            Actions.runBlocking(                        //Make last position the robot was
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeTo(new Vector2d(0, 0))//Position of the left of the backdrop
                            .build());
        }
        else {
            Actions.runBlocking(                        //Make last position the robot was
                    drive.actionBuilder(new Pose2d(0, 0, 0))
                            .strafeTo(new Vector2d(0, 0))//Position of the right of the backdrop
                            .build());
        }
    }
}
