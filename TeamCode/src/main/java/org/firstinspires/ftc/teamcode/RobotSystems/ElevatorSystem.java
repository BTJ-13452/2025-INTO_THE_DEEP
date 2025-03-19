
package org.firstinspires.ftc.teamcode.RobotSystems;


import static android.os.SystemClock.sleep;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * The ElevatorSystem class represents the robot's elevator system.
 */
public class ElevatorSystem {
    public static final int MAX_FIRST_RANGE_POSE = 1800;
    final static int FIRST_RANGE = 550;
    public static final int HANGING_ELEVATOR_POSE = 2900;
    private static final double Speed = 1;
    private static final int POWER = 1;

    public static boolean opmodisactive = true;

    public static DcMotor elevator;
    public static DcMotor elevator2;
    private static TouchSensor elevatorMagnet;
    // private TouchSensor magneticSwitch;

    static Telemetry telemetry;

    /**
     * Constructs a new ElevatorSystem.
     *
     * @param map The HardwareMap for accessing hardware devices.
     */
    public ElevatorSystem(HardwareMap map, Telemetry telemetry) {
        elevator = map.dcMotor.get("elevatormotor");
        elevator2 = map.dcMotor.get("elevatormotor2");
        elevatorMagnet = map.touchSensor.get("elevatorMagnet");
        // magneticSwitch = map.get(TouchSensor.class, "AE");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        elevator2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        closeElevator();
        ElevatorSystem.telemetry = telemetry;
    }

    public static void raiseElevator(int elbowPosition) {
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (elevator.getCurrentPosition() >= -3075) {
            if ((elbowPosition >= FIRST_RANGE) || elevator.getCurrentPosition() >= -MAX_FIRST_RANGE_POSE) {
                elevator.setPower(-Speed);
                elevator2.setPower(-Speed);
            } else {
                elevator.setPower(0);
                elevator2.setPower(0);
            }
        } else {
            elevator.setPower(0);
            elevator2.setPower(0);
        }
    }

    /**
     * Lowers the elevator if the magnetic switch is not pressed.
     */
    public static void lowerElevator() {
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (!elevatorMagnet.isPressed()) {
            elevator.setPower(Speed);
            elevator2.setPower(Speed);
        } else {
            elevator.setPower(0);
            elevator2.setPower(0);
        }
    }

    /**
     * Stops the elevator.
     */
    public static void StopElevator() {
        elevator.setPower(0);
        elevator2.setPower(0);
    }

    public static int getTicks() {
        return elevator2.getCurrentPosition();
    }

    /**
     * Closes the elevator until the magnetic switch is pressed.
     */
    public static void closeElevator() {
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        final boolean[] isPassedTime = {false};
        new Thread(() -> {
            sleep(3000);
            isPassedTime[0] = true;
        }).start();
        while (!isPassedTime[0] && !elevatorMagnet.isPressed()) {
            elevator.setPower(Speed);
            elevator2.setPower(Speed);
        }
        elevator.setPower(0);
        elevator2.setPower(0);
    }

    public static void setElevatorPositionByTicks(int ticks) {
        elevator.setTargetPosition(-ticks);
        elevator2.setTargetPosition(-ticks);

        elevator.setPower(POWER);
        elevator2.setPower(POWER);

        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //auto actions

    public static class setElevatorPositionByTicksAction implements com.acmerobotics.roadrunner.Action {
        int pos;

        public setElevatorPositionByTicksAction(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (getTicks() > -pos + 20 || getTicks() < -pos - 20) {
                setElevatorPositionByTicks(pos);
                return true;
            } else {
                return false;
            }
        }
    }


    public static class closeElevatorAction implements com.acmerobotics.roadrunner.Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            final double ELEVATOR_SPEED = 0.3;

            elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevator2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            elevator.setPower(ELEVATOR_SPEED);
            elevator2.setPower(ELEVATOR_SPEED);
            return false;
        }
    }

}
