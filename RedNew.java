package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import java.util.Locale;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Autonomous

public class RedNew extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor glyphArm1 = null;
    private DcMotor glyphArm2 = null;
    private Servo rightColorArm = null;
    private Servo leftColorArm = null;
    private Servo leftGrabber = null;
    private Servo rightGrabber = null;
    private ColorSensor blueColor;
    private ColorSensor redColor;
    int red;
    int blue;
    int leftPower;
    int rightPower;
    final double RIGHT_COLOR_IN = 0.9;
    final double RIGHT_COLOR_OUT = 0.0;
    final double LEFT_COLOR_IN = 0.9;
    final double LEFT_COLOR_OUT = 0.0;
    final double LEFT_BLOCK_GRAB_CLOSED = 1;
    final double LEFT_BLOCK_GRAB_OPEN = 0;
    final double RIGHT_BLOCK_GRAB_CLOSED = 0;
    final double RIGHT_BLOCK_GRAB_OPEN = 1;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack  = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        glyphArm1 = hardwareMap.get(DcMotor.class, "glyphArm1");
        glyphArm2 = hardwareMap.get(DcMotor.class, "glyphArm2");
        leftColorArm=hardwareMap.get(Servo.class,"leftColorArm" );
        rightColorArm=hardwareMap.get(Servo.class,"rightColorArm" );
        leftGrabber=hardwareMap.get(Servo.class,"leftGrabber");
        rightGrabber=hardwareMap.get(Servo.class,"rightGrabber");
        redColor = hardwareMap.get(ColorSensor.class, "redColor");
        blueColor = hardwareMap.get(ColorSensor.class, "blueColor");
        
    
        
        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
    
        
        // set servos  to intial posistion 
        leftColorArm.setPosition(LEFT_COLOR_IN);
        rightColorArm.setPosition(RIGHT_COLOR_IN);
        leftGrabber.setPosition(LEFT_BLOCK_GRAB_OPEN);
        rightGrabber.setPosition(RIGHT_BLOCK_GRAB_OPEN);
        
        //sets the power for the glyph arms
        glyphArm1.setPower(0);
        glyphArm2.setPower(0);
        
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();
    
        //Close the grabbers and lift the Glyph
        leftGrabber.setPosition(LEFT_BLOCK_GRAB_CLOSED);
        rightGrabber.setPosition(RIGHT_BLOCK_GRAB_CLOSED);
          sleep(250);
        glyphArm1.setPower(-.75);
        glyphArm2.setPower(-.75);
        
        sleep(500);
        
        glyphArm1.setPower(0);
        glyphArm2.setPower(0);
    
    
        //put right arm in postion to read color
        for(double pos = RIGHT_COLOR_IN; pos > RIGHT_COLOR_OUT; pos = pos - .01){
            rightColorArm.setPosition(pos);
            sleep(20);
        }
        sleep(1500);
       
        // The variables red and blue are being used to direct us to
        // the correct ball, based on its color. 
        red = redColor.red();
        blue = redColor.blue();
        if ((red + blue) >= 40){
            // We can tell what color the ball is.
            if (blue > red){
                //We see blue so we are going to drive forward
                telemetry.addData("Ball color is", "BLUE!");
                telemetry.update();
                driveForward(.25,1000);
                //ARM UP
                rightColorArm.setPosition(RIGHT_COLOR_IN);
                sleep(1000);
                //DRIVE TO SAFE ZONE 
                driveForward(.25,1900);
            }
            else {
                //WE SEE RED SO WE TURN TO KNOCK OFF BALL THEN DRIVE OFF PLATFORM
                telemetry.addData("Ball Color is", "RED!");
                telemetry.update();
                //KNOCK OFF BALL
                turnRight(.25, 250);
                //ARM UP
                rightColorArm.setPosition(RIGHT_COLOR_IN);
                sleep(500);
                //streighten up
                turnRight(-.25, 250);
                //DRIVE TO SAFE ZONE
                sleep(500);
                driveForward(.25, 1900);
            }
        }
        else {
            telemetry.addData("Ball Color is", "IDK!");
            telemetry.update();
            //IF DID NOT READ COLOR PUT ARM UP AND PARK IN SAFE ZONE
            rightColorArm.setPosition(RIGHT_COLOR_IN);
            sleep(1000);
            //DRIVE TO SAFE ZONE
            driveForward(.25, 1900);
        }
      turnRight(-.25, 450);
   leftGrabber.setPosition(LEFT_BLOCK_GRAB_OPEN);
    rightGrabber.setPosition(RIGHT_BLOCK_GRAB_OPEN);
    sleep(150);
   driveForward(-.25, 500);
    driveForward(.25, 750);
     driveForward(-.25, 500);
     driveForward(.25, 750);
   
    }
    
    // DRIVE FORWARD/BACK
    void    driveForward(double speed, int timeMs){
        rightBack.setPower(speed);
        rightFront.setPower(speed);
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        sleep(timeMs);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftFront.setPower(0);
        leftBack.setPower(0);
        
    }
    
    // DRIVE RIGHT
    void    driveRight(double speed, int timeMs){
        rightBack.setPower(speed);
        rightFront.setPower(-speed);
        leftFront.setPower(speed);
        leftBack.setPower(-speed);
        sleep(timeMs);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftFront.setPower(0);
        leftBack.setPower(0);
    }
    
    // TURN RIGHT
    void    turnRight(double speed, int timeMs){
        rightBack.setPower(-speed);
        rightFront.setPower(-speed);
        leftFront.setPower(speed);
        leftBack.setPower(speed);
        sleep(timeMs);
        rightBack.setPower(0);
        rightFront.setPower(0);
        leftFront.setPower(0);
        leftBack.setPower(0);
    }
    
}
    // todo: write your code here
