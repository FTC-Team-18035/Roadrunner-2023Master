package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Actions;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Scan Master Class Test")
public class ScanAndRoadRunnerTest extends LinearOpMode {
    //Testing
	int Parking = 0; //0 = right side of backdrop and 1 = left side of backdrop
	
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        Scan scan = new Scan();

        scan.Initialize();

	if(gamepad1.a){ Parking = 1; }
        else if(gamepad1.b){ Parking = 0; }
        telemetry.addData("Parking Position", Parking);
        telemetry.update();

        waitForStart();
        while (opModeIsActive()) {
            scan.telemetryTfod();
            if (scan.location == "Left") {
                drive.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE_GREEN);
                // Actions.runBlocking(
                //       drive.actionBuilder(new Pose2d(0, 0, 0))
                //             .build());
            } else if (scan.location == "Middle") {
                drive.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);
            } else if (scan.location == "Right") {
                drive.lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            }
        }
    }
}
