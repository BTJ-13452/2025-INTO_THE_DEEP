package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClimbSystem {

    final private double MIN_CLIMB = 0;
    final private double MIN_TAIL = 0.6;
    final private double MAX_CLIMB = 1;
    final private double MAX_TAIL = 0.2;

    public Servo servoClimb;
    public Servo servoTail;

    public ClimbSystem(HardwareMap map) {
        servoClimb = map.servo.get("Hung");
        servoClimb.setPosition(MAX_CLIMB);
        servoTail = map.servo.get("Hung2");
        servoTail.setPosition(MAX_TAIL);
    }

    public void startClimb() {
        if (servoClimb.getPosition() == MAX_CLIMB) {
            servoClimb.setPosition(MIN_CLIMB);
        } else {
            servoClimb.setPosition(MAX_CLIMB);
        }
    }

    public void openTail() {
        if (servoTail.getPosition() == MAX_TAIL) {

            servoTail.setPosition(MIN_TAIL);

        } else {
            servoTail.setPosition(MAX_TAIL);
        }
    }

}
