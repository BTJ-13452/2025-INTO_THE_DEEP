package org.firstinspires.ftc.teamcode.RobotSystems;

import static android.os.SystemClock.sleep;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * The ElevatorErectionSystem class represents the robot's elevator erection system.
 */
public class ElbowSystem {

    final String RIGHT_MOTOR_CONFIGURE = "rm";
    final String LEFT_MOTOR_CONFIGURE = "lm";
    public static final int HANGING_ELBOW_POSE = 1500;

    static final double POWER = 1;

    public static DcMotor motorRight;
    public static DcMotor motorLeft;
    public static TouchSensor erectionMagnet;

    /**
     * Constructor for the System_ElevatorErection class.
     *
     * @param map The HardwareMap object used to initialize hardware components.
     */
    public ElbowSystem(HardwareMap map, boolean tele) {
        // Motor initialization
        motorRight = map.get(DcMotor.class, RIGHT_MOTOR_CONFIGURE);
        motorLeft = map.get(DcMotor.class, LEFT_MOTOR_CONFIGURE);

        erectionMagnet = map.touchSensor.get("erectionMagnet");

        // Motor zero power behavior
        motorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set motors to zero position
        if (tele) {
            closeElbow();
        }
    }

    public static void raisedAndLowerElbow(double multiplier) {
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if ((!erectionMagnet.isPressed() || multiplier >= 0) && (motorRight.getCurrentPosition() <= 1470 || multiplier <= 0)) {
            motorLeft.setPower(POWER * multiplier);
            motorRight.setPower(POWER * multiplier);
        } else {
            motorLeft.setPower(0);
            motorRight.setPower(0);
        }
    }

    public static void closeElbow() {
        final boolean[] isPassedTime = {false};
        new Thread(() -> {
            sleep(3000);
            isPassedTime[0] = true;
        }).start();

        while (!isPassedTime[0] && !erectionMagnet.isPressed()) {
            motorRight.setPower(-0.7);
            motorLeft.setPower(-0.7);
        }
        motorRight.setPower(0);
        motorLeft.setPower(0);

        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Motor run modes and encoder setup
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        motorRight.setTargetPosition(0);
        motorLeft.setTargetPosition(0);

        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public static void setPositionElbowByTicks(int ticks) {

        motorRight.setTargetPosition(ticks);
        motorLeft.setTargetPosition(ticks);

        motorLeft.setPower(POWER);
        motorRight.setPower(POWER);

        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public static int getPosition() {
        return motorRight.getCurrentPosition();
    }


    // auto actions
    public static class setPositionElbowByTicksAction implements Action {
        int pos;

        public setPositionElbowByTicksAction(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (getPosition() < pos - 20 || getPosition() > pos + 20) {
                setPositionElbowByTicks(pos);
                return true;
            } else {
                return false;
            }
        }
    }


}

