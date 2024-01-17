    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.hardware.PWMOutput;
    import com.qualcomm.robotcore.util.ElapsedTime;
    @TeleOp(name = "UPDATED Main TeleOP")

    // This opmode has framework for all systems included

    public class MainTeleOp_TEST_Updated extends LinearOpMode {
        // Final variables (Meaning they don't change)
        private PWMOutput Lights;
        static final double COUNTS_PER_MOTOR_REV = 288;    // eg: TETRIX Motor Encoder
        static final double DRIVE_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
        static final double WHEEL_DIAMETER_INCHES = .5;     // For figuring circumference
        static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
        static final int MAX_LIFT_HEIGHT = 4285;

        //Creates Timer cvariables to keep track of time passed
        private ElapsedTime ClawTime = new ElapsedTime();    // sets up timer functions
        private ElapsedTime LiftTime = new ElapsedTime();
        private ElapsedTime DroneTime = new ElapsedTime();
        private ElapsedTime IntakeTime = new ElapsedTime();
        private ElapsedTime IntakeServoTime = new ElapsedTime();
        private ElapsedTime ReverseIntakeTime = new ElapsedTime();
        private ElapsedTime ReverseDriveTime = new ElapsedTime();
        private ElapsedTime runtime = new ElapsedTime();    // sets up a timer function
        private ElapsedTime runtime2 = new ElapsedTime();    // sets up a timer function

        //Variables (meaning they can change)
        private double frontLeftPower = 0;     // declare motor power variable
        private double backLeftPower = 0;      // declare motor power variable
        private double frontRightPower = 0;    // declare motor power variable
        private double backRightPower = 0;     // declare motor power variable
        private double denominator = 1;        // declare motor power calculation variable
        private int precision = 2;          // chassis motor power reduction factor 1=full 2=1/2 power 4=1/4 power
        private double liftPower = 1;        // declare lift motor power variable *******
        private boolean isClosed1 = false;      // Claw state variable
        private boolean isClosed2 = false;      // Claw state variable
        private boolean E_DoubleClose = false;  // Claw state variable
        private int ClawInput = 0;          // Claw button case variable
        private int LiftTarget = 0;         // Lift target position variable
        private boolean BeganPressed = false;
        private boolean IntakeRunning = false;
        private boolean ArmActive = true;
        private boolean ReverseDriveActive = false;

        private boolean JustStarted = true;

        public double y, x, rx;
        RevBlinkinLedDriver lights;

        @Override
        public void runOpMode() throws InterruptedException {
            // Declare our motors
            // Make sure your ID's match your configuration
            Lights = hardwareMap.get(PWMOutput.class, "Lights");
            DcMotor Fleft = hardwareMap.dcMotor.get("Fleft");//Front left wheel
            DcMotor Bleft = hardwareMap.dcMotor.get("Bleft");//Back left wheel
            DcMotor Fright = hardwareMap.dcMotor.get("Fright");//Front right wheel
            DcMotor Bright = hardwareMap.dcMotor.get("Bright");//Back right wheel
            Servo Claw1 = hardwareMap.servo.get("Claw1");//Claw 1
            Servo Claw2 = hardwareMap.servo.get("Claw2");//Claw 2

            Servo Drone = hardwareMap.servo.get("Drone");//Drone
            DcMotor LeftLiftMotor = hardwareMap.dcMotor.get("Lift1");    //Left lift
            DcMotor RightLiftMotor = hardwareMap.dcMotor.get("Lift2");   //Right lift
            DcMotor ArmRotationMotor = hardwareMap.dcMotor.get("ArmRotationMotor");//Arm Rotation

            DcMotor IntakeMotor = hardwareMap.dcMotor.get("IntakeMotor");//Intake

            lights = hardwareMap.get(RevBlinkinLedDriver.class, "Lights");//LED lights
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);//Sets the default to green on initialization

            // Reverse the right side motors

             Bleft.setDirection(DcMotorSimple.Direction.REVERSE);//Reverses the direction the motor turns
            // Fleft.setDirection(DcMotorSimple.Direction.REVERSE);
            Fright.setDirection(DcMotorSimple.Direction.REVERSE);//Reverses the direction the motor turns
            //Bright.setDirection(DcMotorSimple.Direction.REVERSE);

            IntakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);//Reverses the direction the motor turns

//            LeftLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            RightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);//Reverses the direction the motor turns

            RightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//Resets the position so it sets it's current position to 0
            LeftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//Resets the position so it sets it's current position to 0
            ArmRotationMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);//Resets the position so it sets it's current position to 0

            LeftLiftMotor.setTargetPosition(0);//Makes sure it starts at the set 0
            RightLiftMotor.setTargetPosition(0);//Makes sure it starts at the set 0
            ArmRotationMotor.setTargetPosition(0);//Makes sure it starts at the set 0

            LeftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//Sets the mode so we can say to move the motor a certain amount of ticks
            RightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//Sets the mode so we can say to move the motor a certain amount of ticks
            ArmRotationMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);//Sets the mode so we can say to move the motor a certain amount of ticks

            // Set motor zero power behavior

            Fleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped
            Fright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped
            Bleft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locks when stopped
            Bright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped

            LeftLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped
            RightLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped

            ArmRotationMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);//Sets the motor to be locked when stopped

            Claw1.setPosition(0);//Closes the claw
            Claw2.setPosition(0);//Closes the claw
            Drone.setPosition(1);//Sets the drone launcher
            boolean discoMode = false;
            int frequency = 1/2125/1000000;
            waitForStart();//Waits for us to hit play before continuing
            while (opModeIsActive()) {//Loops through everything until we hit stop

                if (gamepad1.right_trigger < .75) {
                    y = gamepad1.left_stick_y; // Remember, this is reversed!
                    x = -gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
                    rx = -gamepad1.left_stick_x; // Measures turning
                    ReverseDriveTime.reset();//Resets the time since the driving was reversed
                    ReverseDriveActive = false;//Sets if the robot's driving is reversed or not
                    Lights.setPulseWidthOutputTime(2125);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.GREEN);
                }


                //Reversed driving
                else if (gamepad1.right_trigger >= .75) {

                    y = -gamepad1.left_stick_y; // Remember, this is reversed!
                    x = -gamepad1.right_stick_x * 1.1; // Counteract imperfect strafing
                    rx = gamepad1.left_stick_x;//Measures turning
                    ReverseDriveTime.reset();//Resets the time since the driving was reversed
                    ReverseDriveActive = true;//Sets it the robot's driving is reversed or not
                    JustStarted = false;//Tells the robot that it has been reversed before
                    //lights.;
                    Lights.setPulseWidthOutputTime(2125);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
                }
                if (gamepad1.left_stick_button == true) {
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.fromNumber(2125));
                    Lights.setPulseWidthOutputTime(2125);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);

                }
                else {

                }
                telemetry.update();//Updates the telemetry on the driver hub

                // calculate motor movement math and adjust according to lift height or manual precision mode selection

                denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

                // check for Turbo or Precision Mode

                if (gamepad1.left_bumper) {
                    precision = 1;              // set speed to full power - TRUBO MODE
                } else if (gamepad1.right_bumper) {
                    precision = 4;              // set speed to 1/4 power - PRECISION MODE
                } else {
                    precision = 2;              // reset default speed to half power
                }

                // calculate motor power

                denominator = denominator * precision;
                frontLeftPower = (y + x + rx) / denominator;
                backLeftPower = (y - x + rx) / denominator;
                frontRightPower = (y - x - rx) / denominator;
                backRightPower = (y + x - rx) / denominator;

                // checks the value of ClawInput

                switch (ClawInput) {
                    //If the answer is 0 it runs the code here
                    case 0:
                        if (gamepad2.b && ClawTime.milliseconds() > 300 && isClosed1) {//This checks to see if it has been more than 300 milliseconds since the "a" button has been pressed
                            ClawTime.reset();//This resets the timer to 0
                            Claw1.setPosition(1);//this opens claw 1
                            isClosed1 = false;//Tells the computer if the claw is closed or not false = not and true = is
                            E_DoubleClose = false;//Tells the computer if the emergency claw button was pressed
                            ClawInput++;//This adds one to the variable to keep track of times the "a" button has been pressed
                        }
                        //If the answer is 1 it runs the code here
                    case 1:
                        if (gamepad2.b && ClawTime.milliseconds() > 300 && isClosed2) {//This checks to see if it has been more than 300 milliseconds since the "a" button has been pressed
                            ClawTime.reset();//This resets the timer to 0
                            Claw2.setPosition(1);//this opens claw 2
                            isClosed2 = false;//Tells the computer if the claw is closed or not false = not and true = is
                            E_DoubleClose = false;//Tells the computer if the emergency claw button was pressed
                            ClawInput++;//This adds one to the variable to keep track of times the "a" button has been pressed
                        }
                        //If the answer is 4 it runs the code here
                    case 2:
                        if (gamepad2.b && ClawTime.milliseconds() > 300) {//This checks to see if the "a" button has been pressed on gamepad1
                            ClawTime.reset();//This starts the timer since the "a" button was just pressed
                            Claw1.setPosition(0);//this closes claw 1
                            Claw2.setPosition(0);//this closes claw 2
                            isClosed1 = true;//Tells the computer if the claw is closed or not false = not and true = is
                            isClosed2 = true;//Tells the computer if the claw is closed or not false = not and true = is
                            E_DoubleClose = true;//Tells the computer if the emergency claw button was pressed
                            ClawInput = 0;//This adds one to the variable to keep track of times the "a" button has been pressed
                        }
                }
                if (gamepad2.right_bumper && !E_DoubleClose && ClawTime.milliseconds() > 300) {
                    Claw1.setPosition(0);//This closes claw 1
                    Claw2.setPosition(0);//This closes claw 2
                    isClosed1 = true;//Tells the computer if the claw is closed or not false = not and true = is
                    isClosed2 = true;//Tells the computer if the claw is closed or not false = not and true = is
                    E_DoubleClose = true;//Tells the computer if the emergency claw button was pressed
                    ClawInput = 0;//This resets cClawInput back to 0
                    ClawTime.reset();//This resets the time in between claw use
                } else if (gamepad2.right_bumper && E_DoubleClose && ClawTime.milliseconds() > 300) {
                    Claw1.setPosition(1);//This opens claw 1
                    Claw2.setPosition(1);//This opens claw 2
                    isClosed1 = false;//Tells the computer if the claw is closed or not false = not and true = is
                    isClosed2 = false;//Tells the computer if the claw is closed or not false = not and true = is
                    ClawInput = 2;//This resets ClawInput back to 2
                    E_DoubleClose = false;//Tells the computer if the emergency claw button was pressed
                    ClawTime.reset();//This resets the time in between claw use
                }

//DEACTIVATED
                // check for lift movement input

                if (gamepad2.a && LiftTime.seconds() > 1.0) {//If A is pressed and Lift time is greater and 1 saying that it has been more than 1 second since the lift moved continue
                    ArmActive = true;//Sets the arm to be active so it will move
                    LiftTarget = 2000;//Sets the lift target to 2000 so the motors will rotate until it reaches 2000 ticks
                    LiftTime.reset();//Resets LiftTime to 0 seconds
                } else if (gamepad2.x && LiftTime.seconds() > 1.0 && ArmActive) {//If the if statement above is not met then it will check this. If X is pressed and it has been more than 1 seconds since the lift moved and the arm is active it will continue                   LiftTarget = 10;
                    LiftTarget  = 10;//Sets the lift target to 10 so the motors will rotate back until it reaches 10 ticks
                    LiftTime.reset();//Resets LiftTime to 0 seconds
                }
               /* else if (gamepad2.x && LiftTime.seconds() > 1.0){
                    LiftTarget = 200;
                    LiftTime.reset();
                }*/
               /* else if (gamepad2.y && LiftTime.seconds() > 1.0) {
                    ArmActive = false;
                    ArmRotationMotor.setTargetPosition(-138);
                    ArmRotationMotor.setPower(1);
                    LiftTarget = 2800;
                    LiftTime.reset();
                }*/
               /* if(gamepad2.left_trigger == 1){
                    LeftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    RightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    LeftLiftMotor.setTargetPosition(10);
                    RightLiftMotor.setTargetPosition(10);
                    LeftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    RightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }*/

                // if((gamepad2.dpad_up) && (LiftTarget + 10) < MAX_LIFT_HEIGHT){
                //  LiftTarget = LiftTarget + 1;
                // }
                if (gamepad2.dpad_up && LiftTarget < MAX_LIFT_HEIGHT - 10) {//If the dpad up is pressed and the lift target is not above the max height
                    LiftTarget = LiftTarget + 10;//This adds lift target +10 to the current lift target having the lift move up slowly
                }
                if (gamepad2.dpad_down && LiftTarget >= 10) {//If the dpad down is pressed and the lift target is higher than 10
                    LiftTarget = LiftTarget - 10;//This subtracts lift target -10 from the current lift target having the lift move up slowly
                }

                // issue lift power for movement
                if (RightLiftMotor.getCurrentPosition() > 100 && RightLiftMotor.getCurrentPosition() < 1900 && ArmActive) {//if the lift position is > 100 and the lift position is < 1900 and the arm is active
                    ArmRotationMotor.setTargetPosition(-180);//Swings the arm backwards to not hit the back of the robot
                    ArmRotationMotor.setPower(1);//Sets the power so the arm will actually turn
                } else if (RightLiftMotor.getCurrentPosition() >= 1950 && ArmActive) {//If the lift position is > or = to 1950 and the arm is active
                    ArmRotationMotor.setTargetPosition(880);//This swings the arm forwards to delivery position
                    ArmRotationMotor.setPower(1);//Sets the power so the arm will actually turn
                } else if (ArmActive){//If none of the if statements have all of their conditions met and the arm is active
                    ArmRotationMotor.setTargetPosition(0);//Sets the arm back to intake position
                    ArmRotationMotor.setPower(1);//Sets the power so the arm will actually turn
                }
                else{//If none of the if statements above have all of their conditions met then this runs
                    ArmRotationMotor.setTargetPosition(200);//lowers the arm so it is out of the way
                    ArmRotationMotor.setPower(1);//Sets the power so the arm will actually turn
                }

                //Lift Power and movement
                if (!(LiftTarget > MAX_LIFT_HEIGHT)) {//If the lift target height is < the max height
                    RightLiftMotor.setTargetPosition(LiftTarget);//Sets the right lift motor to turn until it's ticks = lift target
                    LeftLiftMotor.setTargetPosition(LiftTarget);//Sets the left lift motor to turn until it's ticks = lift target
                    RightLiftMotor.setPower(liftPower);//Sets the power to the Right lift motor
                    LeftLiftMotor.setPower(liftPower);//Sets the power to the left lift motor
                }


                // Drone code

                if(gamepad1.left_trigger >= .6 && gamepad2.left_trigger >= .6){//if both drivers are pressing the left trigger all the way
                    Drone.setPosition(0);//Launches the drone
                    discoMode = true;

            }
                // Intake code
                if(discoMode = true){
                    Lights.setPulseWidthOutputTime(2125);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
                    lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_WITH_GLITTER);
                }
                else {

                }
                if (gamepad1.a && !IntakeRunning && IntakeTime.seconds() > 0.5) {//If the A button is pressed and intake is not running and it has been .5 seconds since the intake was shut off
                    IntakeMotor.setPower(1);//Turns the intake on
                    IntakeRunning = true;//Tells the computer that the intake is running
                    IntakeTime.reset();//Sets the time since the intake was activated to 0 seconds
                } else if (gamepad1.a && IntakeRunning && IntakeTime.seconds() > 0.5) {//if the A button is pressed and the intake is running and it has been .5 seconds since it was turned on
                    IntakeMotor.setPower(0);//Turns the intake off
                    IntakeRunning = false;//Tells the computer that the intake is off
                    IntakeTime.reset();//Sets the time since the intake was shut off to 0 seconds
                }


                if (gamepad1.x) {                // Evaluates x button pushed
                    if (ReverseIntakeTime.seconds() > 0.25) {       // If the button has been held for 1/4 second
                        IntakeMotor.setPower(-1);              // The intake rollers are reversed
                        IntakeRunning = true;                  // The intake status is marked as running
                    } else {                       // If the button has not been held for 1/4 second
                        IntakeMotor.setPower(0);              // The intake rollers are stopped
                        IntakeRunning = false;                  // The intake status is marked as stopped
                    }
                } else {                    // If x button has not been pushed
                    ReverseIntakeTime.reset();               // The timer is reset
                }


                // issue motor power

                Fleft.setPower(frontLeftPower);//Sets the front left wheel's power
                Bleft.setPower(backLeftPower);//Sets the back left wheel's power
                Fright.setPower(frontRightPower);//Sets the front right wheel's power
                Bright.setPower(backRightPower);//Sets the back right wheel's power


                telemetry.addData("Right Lift Height", RightLiftMotor.getCurrentPosition());//Displays the ticks of the right lift motor on the driver hub
                telemetry.update();//Updates the telemetry so we have live readings of the lift height
            }

        }
    }

