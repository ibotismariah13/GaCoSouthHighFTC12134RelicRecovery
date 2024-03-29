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
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


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

@TeleOp(name="Stick Drive", group="Linear Opmode")

public class TeleOpFRC extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor leftArm = null;
    private DcMotor rightArm = null; 
    private DcMotor twoArmLeft = null;
    private DcMotor twoArmRight = null; 
    
    

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "leftDrive");
        rightDrive = hardwareMap.get(DcMotor.class, "rightDrive");
        leftArm = hardwareMap.get(DcMotor.class, "leftArm");
        rightArm = hardwareMap.get(DcMotor.class, "rightArm");
        twoArmLeft = hardwareMap.get(DcMotor.class, "twoArmLeft");
        twoArmRight = hardwareMap.get(DcMotor.class, "twoArmRight");
        

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        leftArm.setDirection(DcMotor.Direction.FORWARD);
        rightArm.setDirection(DcMotor.Direction.REVERSE);
        twoArmLeft.setDirection(DcMotor.Direction.FORWARD);
        twoArmRight.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        //double tgtPower = 0;
        while (opModeIsActive()) {

        //tgtPower = -this.gamepad1.left_stick_y;
        
        
        
        //checks to see if button is pressed 
            //if(gamepad1.y) {
                
            //Moves arm to low position 
                //armLeftS.setPosition(0);
                //armRightS.setPosition(1);
            //}   else if (gamepad1.x) {
                
            //Moves arm to medium position
              //  armLeftS.setPosition(0.5);
              //  armRightS.setPosition(0.5);
           // }   else if (gamepad1.a) {
                
            //Moves arm to high position 
              //  armLeftS.setPosition(1);
              //  armRightS.setPosition(0);
           // }
            
            
            
            // Setup a variable for each drive wheel to save power level for telemetry
            double leftPower;
            double rightPower;
            double leftArmPower;
            double rightArmPower; 
            double twoArmLeftPower;
            double twoArmRightPower;
             

            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            double drive =  -gamepad1.left_stick_y;
            double turn  =  -gamepad1.right_stick_x;
            leftPower    = Range.clip(drive + turn, -1.0, 1.0) ;
            rightPower   = Range.clip(drive - turn, -1.0, 1.0) ;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            leftArmPower  = -gamepad1.left_stick_x ;
            rightArmPower = -gamepad1.left_stick_x ;
            twoArmLeftPower = -gamepad1.right_stick_y;
            twoArmRightPower = -gamepad1.right_stick_y;

            //Send calculated power to wheels
            
            //Powers the drivetrain 
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);
            
            //Sends power to the first set of motors for arm control 
            leftArmPower = leftArmPower/2;
            rightArmPower= rightArmPower/2;
            
            leftArm.setPower(leftArmPower);
            rightArm.setPower(rightArmPower);
            
            //Sends power to the second set of motors for arm control 
            twoArmLeftPower = twoArmLeftPower/2;
            twoArmRightPower = twoArmRightPower/2;
            twoArmLeft.setPower(twoArmLeftPower);
            twoArmRight.setPower(twoArmRightPower);
            
            //Resets Ecoders 
            twoArmLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            twoArmRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            
            while(gamepad1.right_bumper)
            {
                
                twoArmLeft.setPower(1);
                twoArmRight.setPower(1);
                twoArmLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                twoArmRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                
            }
            
            /*while (!gamepad1.right_bumper) {
                int position = twoArmLeft.getCurrentPosition();
                
                twoArmLeft.setTargetPosition(position);
                twoArmRight.setTargetPosition(position);
                
                twoArmRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                twoArmLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                
            }
            */
            
            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.update();
        }
    }
}
