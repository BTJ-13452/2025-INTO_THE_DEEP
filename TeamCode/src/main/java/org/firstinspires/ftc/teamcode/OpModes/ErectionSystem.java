package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.vision.VisionPortal;

/**
 * The ElevatorErectionSystem class represents the robot's elevator erection system.
 */
public class ErectionSystem {

    final String RIGHT_MOTOR_CONFIGURE = "rm";
    final String LEFT_MOTOR_CONFIGURE = "lm";

    final double POWER = 1;

    DcMotor motorRight;
    DcMotor motorLeft;
    public TouchSensor erectionMagnet;

    /**
     * Constructor for the System_ElevatorErection class.
     *
     * @param map The HardwareMap object used to initialize hardware components.
     */
    public ErectionSystem(HardwareMap map) {
        // Motor initialization
        motorRight = map.get(DcMotor.class, RIGHT_MOTOR_CONFIGURE);
        motorLeft = map.get(DcMotor.class, LEFT_MOTOR_CONFIGURE);

        erectionMagnet = map.touchSensor.get("erectionMagnet");

        // Motor zero power behavior
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set motors to zero position
        CloseElevator();

        // Motor run modes and encoder setup
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        motorRight.setTargetPosition(0);
        motorLeft.setTargetPosition(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

//    public ElevatorErectionSystem(HardwareMap map) {
//        Lm = map.dcMotor.get("lm");
//        Rm = map.dcMotor.get("rm");
//        Lm.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        magneticSwitch = map.get(TouchSensor.class, "erectionSwitch");
//
//        Lm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        Rm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        Lm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Rm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//    }


    public void raisedErection() {
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motorLeft.setPower(POWER);
        motorRight.setPower(POWER);

    }

    public void lowerErection() {
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (!erectionMagnet.isPressed()) {
            motorLeft.setPower(-POWER);
            motorRight.setPower(-POWER);
        }else {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }

    /**
     * Stops the elevator and resets motor run modes and positions.
     */
    public void stopErection() {
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRight.setTargetPosition(motorRight.getCurrentPosition());
        motorLeft.setTargetPosition(motorLeft.getCurrentPosition());

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorRight.setPower(0);
        motorLeft.setPower(0);
    }

    public void CloseElevator() {
        final boolean[] isPassedTime = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(3000);
                isPassedTime[0] = true;
            }
        }).start();

        while (!isPassedTime[0] && !erectionMagnet.isPressed()) {
            motorRight.setPower(-0.7);
            motorLeft.setPower(-0.7);
        }
        motorRight.setPower(0);
        motorLeft.setPower(0);
    }
}