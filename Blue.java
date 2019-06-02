/* Copyright (c) 2017 FIRST. All rights reserved.
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

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@Autonomous(name="BLUE", group="Linear Opmode")

public class Blue extends LinearOpMode {

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
        leftColorArm=hardwareMap.get(Servo.class,"leftColorArm");
        rightColorArm=hardwareMap.get(Servo.class,"rightColorArm");
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
        
        
        //put right arm in postion to read color
        for(double pos = LEFT_COLOR_IN; pos > LEFT_COLOR_OUT; pos = pos - .01){
            leftColorArm.setPosition(pos);
            sleep(20);
        }
        sleep(1500);
       
        // The variables red and blue are being used to direct us to
        // the correct ball, based on its color. 
        red = blueColor.red();
        blue = blueColor.blue();
        if ((red + blue) >= 40){
            // We can tell what color the ball is.
            if (red > blue){
                //We see blue so we are going to drive forward
                telemetry.addData("Ball color is", "RED!");
                telemetry.update();
                driveForward(.25,1000);
                //ARM UP
                leftColorArm.setPosition(RIGHT_COLOR_IN);
                sleep(1000);
                //DRIVE TO SAFE ZONE 
                driveForward(.25,1750);
            }
            else {
                //WE SEE RED SO WE TURN TO KNOCK OFF BALL THEN DRIVE OFF PLATFORM
                telemetry.addData("Ball Color is", "BLUE!");
                telemetry.update();
                //KNOCK OFF BALL
                turnRight(-.25, 250);
                //ARM UP
                leftColorArm.setPosition(RIGHT_COLOR_IN);
                sleep(500);
                //streighten up
                turnRight(.25, 250);
                //DRIVE TO SAFE ZONE
                sleep(500);
                driveForward(.25, 1750);
            }
        }
        else {
            telemetry.addData("Ball Color is", "IDK!");
            telemetry.update();
            //IF DID NOT READ COLOR PUT ARM UP AND PARK IN SAFE ZONE
            leftColorArm.setPosition(RIGHT_COLOR_IN);
            sleep(1000);
            //DRIVE TO SAFE ZONE
            driveForward(.25, 1750);
        }
     
   
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
