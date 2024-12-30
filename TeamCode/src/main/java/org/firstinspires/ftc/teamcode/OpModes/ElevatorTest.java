
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

    private DcMotor elevator;
    private TouchSensor elevatorMagnet;
   // private TouchSensor magneticSwitch;
    private final double Speed = 0.7;
    private final int MAX_HEIGHT = 1000;

    /**
     * Constructs a new ElevatorSystem.
     *
     * @param map The HardwareMap for accessing hardware devices.
     */
    public ElevatorTest(HardwareMap map) {
        elevator = map.dcMotor.get("elevatormotor");
        elevatorMagnet = map.touchSensor.get("elevatorMagnet");
       // magneticSwitch = map.get(TouchSensor.class, "AE");
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        CloseElevator();
    }

    /**
     * Extends the elevator.
     */
    public void Extension() {
        if (elevator.getCurrentPosition() > -2215) {
            elevator.setPower(-1);
        } else {
            elevator.setPower(0);
        }
    }

    public void OpenElevator (int ticks) {
        while (elevator.getCurrentPosition() > -ticks) {
            elevator.setPower(-1);
        }
        elevator.setPower(0);

    }

    /**
     * Lowers the elevator if the magnetic switch is not pressed.
     */
    public void LowerElevator() {
        if (!elevatorMagnet.isPressed()) {
            elevator.setPower(0.8);
        }
        else {
            elevator.setPower(0);
        }
    }

    /**
     * Stops the elevator.
     */
    public void StopElevator() {
        elevator.setPower(0);
    }

    public int getTicks(){
        return elevator.getCurrentPosition();
    }

    /**
     * Closes the elevator until the magnetic switch is pressed.
     */
    public void CloseElevator() {
        final boolean[] isPassedTime = {false};
        new Thread(new Runnable() {
            @Override
            public void run() {
                sleep(3000);
                isPassedTime[0] = true;
            }
        }).start();
        while (!isPassedTime[0]&& !elevatorMagnet.isPressed()) {
            elevator.setPower(Speed);
        }
        elevator.setPower(0);
    }
}
