/* Copyright (c) 2019 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;

import java.util.List;

/*
 * This OpMode illustrates the basics of TensorFlow Object Detection,
 * including Java Builder structures for specifying Vision parameters.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list.
 */
@Disabled
@Autonomous(preselectTeleOp = "Main TeleOP")
// UNTESTED program vor vision based autonomous starting from the BLUE WING side.
public class AudienceRedVISION extends LinearOpMode {

    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    // TFOD_MODEL_ASSET points to a model file stored in the project Asset location,
    // this is only used for Android Studio when using models in Assets.
    private static final String TFOD_MODEL_ASSET = "TeamProp2024-Take2.tflite";
    // TFOD_MODEL_FILE points to a model file stored onboard the Robot Controller's storage,
    // this is used when uploading models directly to the RC using the model upload interface.
    private static final String TFOD_MODEL_FILE = "/sdcard/FIRST/tflitemodels/TeamProp2024-Take2.tflite";
    // Define the labels recognized in the model for TFOD (must be in training order!)
    private static final String[] LABELS = {
            "BlueProp",
            "RedProp",
    };

    public String label;//Saves the name of the detected object (In this case "Pixel")
    public double objectDistanceX;//Saves the X of the detected object
    public double objectDistanceY;//Saves the Y of the detected object

    ElapsedTime timer = new ElapsedTime();
    public int DetetcionLeft = 100;
    public int DetectionMiddle = 250;
    public int DetectionRight = 350;

    public MecanumDrive drive;
    RevBlinkinLedDriver lights;
    //public DcMotor motor1, motor2; //Testing motors
    /**
     * The variable to store our instance of the TensorFlow Object Detection processor.
     */
    private TfodProcessor tfod;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;
    // @Override
    public void runOpMode() {

        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        initTfod();
        // motor1 = hardwareMap.dcMotor.get("motor1");//Initialization of test motor 1
        //motor2 = hardwareMap.dcMotor.get("motor2");//Initialization of test motor 2
        lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");

        // Wait for the DS start button to be touched.
        telemetry.addData("DS preview on/off", "3 dots, Camera Stream");
        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
        if (opModeIsActive()) {
            timer.reset();
            while (opModeIsActive()) {

                double currentTime = timer.seconds();
                telemetryTfod(currentTime);
                telemetry.addData("Time", currentTime);
                telemetry.addData("Label", label);
                // Push telemetry to the Driver Station.
                telemetry.update();
                // Save CPU resources; can resume streaming when needed.
                if (gamepad1.dpad_down) {
                    visionPortal.stopStreaming();
                } else if (gamepad1.dpad_up) {
                    visionPortal.resumeStreaming();
                }

                // Share the CPU.
                sleep(20);


            }
        }

        // Save more CPU resources when camera is no longer needed.
        //visionPortal.close();

    }   // end runOpMode()

    /**
     * Initialize the TensorFlow Object Detection processor.
     */
    private void initTfod() {

        // Create the TensorFlow processor by using a builder.
        tfod = new TfodProcessor.Builder()

                // With the following lines commented out, the default TfodProcessor Builder
                // will load the default model for the season. To define a custom model to load,
                // choose one of the following:
                .setModelAssetName(TFOD_MODEL_ASSET)
                //   Use setModelFileName() if you have downloaded a custom team model to the Robot Controller.
                //.setModelAssetName(TFOD_MODEL_ASSET)
                //.setModelFileName(TFOD_MODEL_FILE)

                // The following default settings are available to un-comment and edit as needed to
                // set parameters for custom models.
                .setModelLabels(LABELS)
                //.setIsModelTensorFlow2(true)
                //.setIsModelQuantized(true)
                //.setModelInputSize(300)
                //.setModelAspectRatio(16.0 / 9.0)

                .build();

        // Create the vision portal by using a builder.
        VisionPortal.Builder builder = new VisionPortal.Builder();

        // Set the camera (webcam vs. built-in RC phone camera).
        if (USE_WEBCAM) {
            builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"));
        } else {
            builder.setCamera(BuiltinCameraDirection.BACK);
        }

        // Choose a camera resolution. Not all cameras support all resolutions.
        //builder.setCameraResolution(new Size(640, 480));

        // Enable the RC preview (LiveView).  Set "false" to omit camera monitoring.
        //builder.enableLiveView(true);

        // Set the stream format; MJPEG uses less bandwidth than default YUY2.
        //builder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        // Choose whether or not LiveView stops if no processors are enabled.
        // If set "true", monitor shows solid orange screen if no processors enabled.
        // If set "false", monitor shows camera view without annotations.
        //builder.setAutoStopLiveView(false);

        // Set and enable the processor.
        builder.addProcessor(tfod);

        // Build the Vision Portal, using the above settings.
        visionPortal = builder.build();

        // Set confidence threshold for TFOD recognitions, at any time.
        tfod.setMinResultConfidence(0.40f);

        // Disable or re-enable the TFOD processor at any time.
        //visionPortal.setProcessorEnabled(tfod, true);

    }   // end method initTfod()

    /**
     * Add telemetry about TensorFlow Object Detection (TFOD) recognitions.
     */
    private void telemetryTfod(double currentTime) {

        List<Recognition> currentRecognitions = tfod.getRecognitions();
        telemetry.addData("# Objects Detected", currentRecognitions.size());

        // Step through the list of recognitions and display info for each one.
        for (Recognition recognition : currentRecognitions) {
            double x = (recognition.getLeft() + recognition.getRight()) / 2;
            double y = (recognition.getTop() + recognition.getBottom()) / 2;
            if (recognition.getConfidence() >= .10) {
                label = recognition.getLabel();//Saves the Object's "label" to our variable label to make it public
            }
            objectDistanceX = x;//Saves the object's X position to our objectDistanceX to make it public
            objectDistanceY = y;//Saves the object's Y position to our objectDistanceY to make it public
            telemetry.addData("", " ");
            telemetry.addData("Image", "%s (%.0f %% Conf.)", recognition.getLabel(), recognition.getConfidence() * 100);
            telemetry.addData("- Position", "%.0f / %.0f", x, y);
            telemetry.addData("- Size", "%.0f x %.0f", recognition.getWidth(), recognition.getHeight());
        }
            if (label == "BlueProp") {//Checks to see if something has been detected (If nothing has been label is empty ""
                //Spike mark 2
                if (objectDistanceX >= 10 && objectDistanceX <= 450) {//The thought was if the robot move left far enough this would become false
                    visionPortal.close();
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(0, 0, 0))
                                    .waitSeconds(4) //add this in to coordinate autonomous
                                    .strafeTo(new Vector2d(-43.5, 0))   //moves backwards 49.5"
                                    .build());

                    drive.ActivateIntake(-.60);
                    sleep(1000);
                    drive.ActivateIntake(0);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(-43.5, 0, 0))
                                    .strafeTo(new Vector2d(-43.5, -83)) //moves left 83"
                                    .waitSeconds(.1)
                                    .turnTo(Math.toRadians(90)) //turns 90 degrees clockwise
                                    .strafeTo(new Vector2d(-28, -83))    //moves left 16"
                                    .build());

                    drive.MoveLift(100);
                    sleep(500);
                    drive.RotateArm(-90);
                    sleep(1000);
                    drive.MoveLift(1450);
                    sleep(1000);
                    drive.RotateArm(880);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(-28, -83, Math.toRadians(90)))
                                    .waitSeconds(1)
                                    .strafeTo(new Vector2d(-28, -87))    //moves towards backdrop
                                    .build());

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
                            drive.actionBuilder(new Pose2d(-28, -87, Math.toRadians(90))) //If it still turns reset to 0 (Heading)
                                    .strafeTo(new Vector2d(-28, -84))
                                    .waitSeconds(.5)
                                    .strafeTo(new Vector2d(-46, -84))
                                    .build());
                    requestOpModeStop();
                    //Spike mark 3
                } else if (objectDistanceX > 450 && objectDistanceX < 600) {//This is supposed to check if we are far enough forward towards the pixel but never became true
                    visionPortal.close();
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(0, 0, 0))
                                    .waitSeconds(4) //add this in to coordinate autonomous
                                    .strafeTo(new Vector2d(-43.5, 0))   //moves backwards 49.5"
                                    .strafeTo(new Vector2d(-43.5, 11))
                                    .build());

                    drive.ActivateIntake(-.6); //upped speed from .7 bc it needed to shoot farther over here
                    sleep(1000);
                    drive.ActivateIntake(0);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(-43.5, 11, 0))
                                    .strafeTo(new Vector2d(-43.5, -73)) //moves left 83"
                                    .waitSeconds(.1)
                                    .turnTo(Math.toRadians(90)) //turns 90 degrees clockwise
                                    .strafeTo(new Vector2d(-34, -73))    //moves left 16"
                                    .build());

                    drive.MoveLift(100);
                    sleep(500);
                    drive.RotateArm(-90);
                    sleep(1000);
                    drive.MoveLift(1450);
                    sleep(1000);
                    drive.RotateArm(880);
                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(-34, -73, Math.toRadians(90)))
                                    .waitSeconds(1)
                                    .strafeTo(new Vector2d(-34, -83))    //moves towards backdrop
                                    .build()
                    );
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
                            drive.actionBuilder(new Pose2d(-34, -83, Math.toRadians(90))) //If it still turns reset to 0 (Heading)
                                    .strafeTo(new Vector2d(-34, -78)) //was (-35, 84)
                                    .waitSeconds(.5)
                                    .strafeTo(new Vector2d(-50, -78))
                                    .build());
                    requestOpModeStop();

                }
            }
            else if(currentTime > 3){//Spike mark 1
                    visionPortal.close();
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.VIOLET);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(0, 0, 0))
                                    .waitSeconds(2) //add this in to coordinate autonomous
                                    .strafeTo(new Vector2d(-23,0)) //moves backwards 49.5" was .43.5
                                    .turn(Math.toRadians(-90))
                                    .build());

                    drive.ActivateIntake(-.6);
                    sleep(1000);
                    drive.ActivateIntake(0);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(-23,0,Math.toRadians(-90))) //was -43.5
                                    .strafeTo(new Vector2d(-23, 3))
                                    .turn(Math.toRadians(90))
                                    .strafeTo(new Vector2d(-43.5, 3))
                                    .strafeTo(new Vector2d(-43.5,-83)) //moves left 83"
                                    .waitSeconds(.1)
                                    .turnTo(Math.toRadians(90)) //turns 90 degrees clockwise
                                    .strafeTo(new Vector2d(-21, -83))    //moves left 23"
                                    .build());

                    drive.MoveLift(100);
                    sleep(500);
                    drive.RotateArm(-90);
                    sleep(1000);
                    drive.MoveLift(1450);
                    sleep(1000);
                    drive.RotateArm(880);

                    Actions.runBlocking(
                            drive.actionBuilder(new Pose2d(-21, -83, Math.toRadians(90)))
                                    .waitSeconds(1)
                                    .strafeTo(new Vector2d(-21, -87))    //moves towards backdrop
                                    .build());

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
                            drive.actionBuilder(new Pose2d(-21,-87,Math.toRadians(90))) //If it still turns reset to 0 (Heading)
                                    .strafeTo(new Vector2d(-21,-84))
                                    .waitSeconds(.5)
                                    .strafeTo(new Vector2d(-46, -84))
                                    .build());

                    requestOpModeStop();
                }

                //The X and Y never really dropped below 200. Or went over 300// end for() loop

    }
}// end method telemetryTfod()


// end class
