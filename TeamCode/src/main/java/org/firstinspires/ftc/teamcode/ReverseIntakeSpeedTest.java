    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.util.ElapsedTime;

    @TeleOp(name = "Intake Speed Test")

    // This opmode has framework for all systems included

    public class ReverseIntakeSpeedTest extends LinearOpMode {
        // Final variables (Meaning they don't change)
        //public PWMOutput Lights;

        //Creates Timer cvariables to keep track of time passed

        private ElapsedTime ReverseIntakeTime = new ElapsedTime();

        //Variables (meaning they can change)
        private double frontLeftPower = 0;     // declare motor power variable
        private double backLeftPower = 0;      // declare motor power variable
        private double frontRightPower = 0;    // declare motor power variable
        private double backRightPower = 0;     // declare motor power variable
        private double denominator = 1;        // declare motor power calculation variable
        private int precision = 2;          // chassis motor power reduction factor 1=full 2=1/2 power 4=1/4 power

        private boolean BeganPressed = false;
        private boolean IntakeRunning = false;

        public double y, x, rx;
        RevBlinkinLedDriver lights;

        @Override
        public void runOpMode() throws InterruptedException {
            // Declare our motors
            // Make sure your ID's match your configuration
            // Lights = hardwareMap.get(PWMOutput.class, "Lights");
            DcMotor Fleft = hardwareMap.dcMotor.get("Fleft");//Front left wheel
            DcMotor Bleft = hardwareMap.dcMotor.get("Bleft");//Back left wheel
            DcMotor Fright = hardwareMap.dcMotor.get("Fright");//Front right wheel
            DcMotor Bright = hardwareMap.dcMotor.get("Bright");//Back right wheel

            DcMotor IntakeMotor = hardwareMap.dcMotor.get("IntakeMotor");//Intake

            lights = hardwareMap.get(RevBlinkinLedDriver.class, "lights");
            // Reverse the right side motors

             Bleft.setDirection(DcMotorSimple.Direction.REVERSE);//Reverses the direction the motor turns
            // Fleft.setDirection(DcMotorSimple.Direction.REVERSE);
            Fright.setDirection(DcMotorSimple.Direction.REVERSE);//Reverses the direction the motor turns
            //Bright.setDirection(DcMotorSimple.Direction.REVERSE);

            // Set motor zero power behavior

            Fleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped
            Fright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped
            Bleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locks when stopped
            Bright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped


            boolean discoMode = true;
            waitForStart();//Waits for us to hit play before continuing
            while (opModeIsActive()) {//Loops through everything until we hit stop

                y = gamepad1.left_stick_y; // Remember, this is reversed!
                x = -gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
                rx = -gamepad1.left_stick_x; // Measures turning


                if (gamepad1.left_stick_button == true) {
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
                }

                telemetry.update();//Updates the telemetry on the driver hub

                // calculate motor movement math and adjust according to lift height or manual precision mode selection

                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

                // check for Turbo or Precision Mode


                // calculate motor power

                denominator = denominator * precision;
                frontLeftPower = (y + x + rx) / denominator;
                backLeftPower = (y - x + rx) / denominator;
                frontRightPower = (y - x - rx) / denominator;
                backRightPower = (y + x - rx) / denominator;

                // checks the value of ClawInput


                // Intake code
                if(discoMode){
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
                }

                if (gamepad1.x)  IntakeMotor.setPower(.1);              // The intake rollers are reversed
                if(gamepad1.y)   IntakeMotor.setPower(.2);
                if(gamepad1.b)   IntakeMotor.setPower(.3);
                if(gamepad1.a)   IntakeMotor.setPower(.4);
                if(gamepad1.dpad_down)   IntakeMotor.setPower(.5);
                if(gamepad1.dpad_right)   IntakeMotor.setPower(.6);
                if(gamepad1.dpad_up)   IntakeMotor.setPower(.7);
                if(gamepad1.dpad_left)   IntakeMotor.setPower(.8);    // .5 Intake power For autonomous purple pixel delivery
                if(gamepad1.left_bumper)   IntakeMotor.setPower(.9);
                if(gamepad1.right_bumper)   IntakeMotor.setPower(1);
                if(gamepad1.right_trigger == 1)   IntakeMotor.setPower(0);
                // issue motor power

                Fleft.setPower(frontLeftPower);//Sets the front left wheel's power
                Bleft.setPower(backLeftPower);//Sets the back left wheel's power
                Fright.setPower(frontRightPower);//Sets the front right wheel's power
                Bright.setPower(backRightPower);//Sets the back right wheel's power


                telemetry.addData("Intake Speed", IntakeMotor.getPower());
                telemetry.update();//Updates the telemetry so we have live readings of the lift height
            }

        }
    }

