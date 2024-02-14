package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
@Autonomous(name = "Scan Master Class Test")
public class ScanAndRoadRunnerTest extends LinearOpMode {
//Testing
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        Scan scan = new Scan();

        scan.Initialize();
        scan.telemetryTfod();
        waitForStart();

            if(scan.location == "Left") {
               drive.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);
                // Actions.runBlocking(
                 //       drive.actionBuilder(new Pose2d(0, 0, 0))
                   //             .build());
            }
            else if(scan.location == "Middle"){
                drive.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            }
            else if(scan.location == "Right"){
                drive.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }
        }
    }
