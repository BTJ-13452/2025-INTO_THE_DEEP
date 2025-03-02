
package org.firstinspires.ftc.teamcode.OpModes;


import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * The ElevatorSystem class represents the robot's elevator system.
 */
public class ElevatorTest {
    boolean opmodisactive = true;
    final int FIRST_RANGE = 550;
    final int SECOND_RANGE = 4000;
    public DcMotor elevator;
    public DcMotor elevator2;
    private TouchSensor elevatorMagnet;
    // private TouchSensor magneticSwitch;
    private final double Speed = 1;
    private final int MAX_HEIGHT = 1000;
    private final int POWER = 1;

    /**
     * Constructs a new ElevatorSystem.
     *
     * @param map The HardwareMap for accessing hardware devices.
     */
    public ElevatorTest(HardwareMap map) {
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
        CloseElevator();
    }

    /**
     * Extends the elevator.
     */
    public void raisedAndlowerElevator(double multiplier, Boolean isErectionMIsPressd) {
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if ((multiplier > 0 && !elevatorMagnet.isPressed()) || (multiplier <= 0 && (!isErectionMIsPressd || elevator.getCurrentPosition() >= -1538))) {

            elevator.setPower(POWER * multiplier);

        } else {
            elevator.setPower(0);
        }
    }

    public void OpenElevator(int ticks) {
        elevator.setTargetPosition(-ticks);
        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator.setPower(-POWER);
    }

    public void upperElevator(int isErectionMIsPressd) {
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (elevator.getCurrentPosition() >= -3075) {
            if ((isErectionMIsPressd >= FIRST_RANGE) || elevator.getCurrentPosition() >= -1538 )  {
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
    public void lowerElevator() {
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
    public void StopElevator() {
        elevator.setPower(0);
        elevator2.setPower(0);
    }

    public int getTicks() {
        return elevator.getCurrentPosition();
    }

    /**
     * Closes the elevator until the magnetic switch is pressed.
     */
    public void CloseElevator() {
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        final boolean[] isPassedTime = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(3000);
                isPassedTime[0] = true;
            }
        }).start();
        while (!isPassedTime[0] && !elevatorMagnet.isPressed()) {
            elevator.setPower(Speed);
            elevator2.setPower(Speed);
        }
        elevator.setPower(0);
        elevator2.setPower(0);
    }

    public void CloseElevator2() {
        while (opmodisactive) {
            elevator.setPower(1);
            elevator2.setPower(1);
        }
        elevator.setPower(0);
        elevator2.setPower(0);
    }

    public void elevatorbyticks(int ticks) {
        elevator.setTargetPosition(-ticks);
        elevator2.setTargetPosition(-ticks);

        elevator.setPower(POWER);
        elevator2.setPower(POWER);

        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        elevator2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
