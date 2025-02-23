package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    final private double MAX = 1;
    final private double MID = 0.3;
    final private double MID2 = 0.5;
    final private double MIN = 0;
    public Servo servoClawL;
    public Servo servoClawR;
    public Servo clawJoint;

    public Claw(HardwareMap map) {
        servoClawL = map.servo.get("ClawL");
        servoClawR = map.servo.get("ClawR");
        clawJoint = map.servo.get("ClawJ");
        servoClawR.setPosition(MIN);
        servoClawL.setPosition(MAX);
        clawJoint.setPosition(MIN);
    }

    public void frontClose() {

        if (servoClawR.getPosition() == MID) {
            servoClawR.setPosition(MIN);
            servoClawL.setPosition(MAX);

        } else {
            servoClawR.setPosition(MID);
            servoClawL.setPosition(1 - MID);
        }
    }

    public void backClose() {
        if (servoClawR.getPosition() == MID) {
            servoClawR.setPosition(MAX);
            servoClawL.setPosition(MIN);

        } else {
            servoClawR.setPosition(MID);
            servoClawL.setPosition(MID);
        }
    }

    public void openJoint() {
        if (clawJoint.getPosition() == MID2) {
            clawJoint.setPosition(MIN);

        } else {
            clawJoint.setPosition(MID2);
        }
    }

    public void closeJoint() {
        clawJoint.setPosition(MIN);
    }
}
