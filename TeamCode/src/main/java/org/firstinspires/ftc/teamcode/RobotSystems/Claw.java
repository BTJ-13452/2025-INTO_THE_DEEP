package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Claw {

    public boolean isInside = false;
    final private double MAX = -0.35;
    final private double MAXOUT = 0.8;
    final private double MID = 0.3;
    final private double MID2 = 0.5;
    final private double MIN = 0;
    public CRServo servoClawL;
    public CRServo servoClawR;
    public Servo clawJoint;
    public DistanceSensor distanceSensor;

    public Claw(HardwareMap map) {
        servoClawL = map.get(CRServo.class, "ClawL");
        servoClawR = map.get(CRServo.class, "ClawR");
        clawJoint = map.servo.get("ClawJ");
        distanceSensor = map.get(DistanceSensor.class, "distanceSensor");

    }

    public void frontCloseIN() {
        if (servoClawR.getPower() == MIN) {
            servoClawR.setPower(-MAX);
            servoClawL.setPower(MAX);
        } else {
           stop();
        }
    }

    public void frontCloseOUT() {
        if (servoClawR.getPower() == MIN) {
            servoClawR.setPower(-MAXOUT);
            servoClawL.setPower(MAXOUT);
        } else {
           stop();
        }
    }

    public void stop() {
        servoClawR.setPower(MIN);
        servoClawL.setPower(MIN);
    }

//    public void backClose() {
//        if (servoClawR.getPosition() == MID) {
//            servoClawR.setPosition(MAX);
//            servoClawL.setPosition(MIN);
//
//        } else {
//            servoClawR.setPosition(MID);
//            servoClawL.setPosition(MID);
//        }
//    }

    public void openJoint() {
        if (clawJoint.getPosition() == MID2) {
            clawJoint.setPosition(MIN);

        } else {
            clawJoint.setPosition(MID2);
        }
    }

    public void closeJoint() {
        clawJoint.setPosition(MID2);
    }

    public void activateIN() {
        servoClawR.setPower(-MAX);
        servoClawL.setPower(MAX);
    }

    public void activateOUT() {
        servoClawR.setPower(-MAXOUT);
        servoClawL.setPower(MAXOUT);
    }

        public Boolean isDS_see(){
        return distanceSensor.getDistance(DistanceUnit.CM) < 4.5;
        }
}

