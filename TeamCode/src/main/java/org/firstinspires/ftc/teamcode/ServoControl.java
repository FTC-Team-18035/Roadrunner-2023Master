package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode demonstrates how to control a servo proportionally to the gamepad 2 right stick X value.
 */
@TeleOp(name = "ServoControl", group = "TeleOp")
public class ServoControl extends OpMode {

    // Define servo object
    private Servo servo;

    // Define scaling factor for servo position
    private final double SERVO_SCALE_FACTOR = 2;

    @Override
    public void init() {
        // Get the servo object from the hardware map
        servo = hardwareMap.servo.get("servo");
        servo.setPosition(0);
    }

    @Override
    public void loop() {
        double rightStickX = gamepad2.right_stick_x;
        if(gamepad1.a == true){
            servo.setPosition(.25);
        }
        else if(gamepad1.x == true){
            servo.setPosition(.5);
        }
        else if(gamepad1.y == true){
            servo.setPosition(.75);
        }
        else if(gamepad1.b == true){
            servo.setPosition(1);
        }
        else if(gamepad1.right_bumper == true){
            servo.setPosition(0);
        }
        // Scale the right stick value to the servo's range
        // double servoPosition = Range.scale(rightStickX, -1.0, 1.0, -1.0, 1.0) * SERVO_SCALE_FACTOR;

        // Set the servo position smoothly with transition
        // servo.setPosition(servoPosition);

        // Telemetry for debugging
        // telemetry.addData("Servo Position:", servoPosition);
    }
}
