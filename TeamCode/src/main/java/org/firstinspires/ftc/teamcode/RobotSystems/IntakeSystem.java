package org.firstinspires.ftc.teamcode.RobotSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class IntakeSystem {
    static final private double DOWN_JOINT = 1;
    static final private double MID_JOINT = 0.53;
    static final private double UP_JOINT = 0;
    public static CRServo servoIntakeL;
    public static CRServo servoIntakeR;
    public static Servo servoJoint;
    public static DistanceSensor distanceSensor;
    public static DistanceSensor distanceSensor2;
    public static ColorSensor colorSensor1;
    public static ColorSensor colorSensor2;

    public IntakeSystem(HardwareMap map) {
        servoIntakeL = map.get(CRServo.class, "ClawL");
        servoIntakeR = map.get(CRServo.class, "ClawR");
        servoJoint = map.servo.get("ClawJ");
        distanceSensor = map.get(DistanceSensor.class, "distanceSensor");
        distanceSensor2 = map.get(DistanceSensor.class, "distanceSensor2");
        colorSensor1 = map.get(ColorSensor.class, "distanceSensor");
        colorSensor2 = map.get(ColorSensor.class, "distanceSensor2");

        servoJoint.setDirection(Servo.Direction.REVERSE);

        servoJoint.setPosition(0);
        stop();
    }

    public static void activateIntake() {
        if (servoIntakeR.getPower() == 0 || servoIntakeR.getPower() == 1) {
            servoIntakeR.setPower(-1);
            servoIntakeL.setPower(1);
        } else {
            stop();
        }
    }

    public static void activateEjection() {
        if (servoIntakeR.getPower() == 0 || servoIntakeR.getPower() == -1) {
            servoIntakeR.setPower(1);
            servoIntakeL.setPower(-1);
        } else {
            stop();
        }
    }

    public static void stop() {
        servoIntakeR.setPower(0);
        servoIntakeL.setPower(0);

    }


    public static void changeJointPos() {
        if (servoJoint.getPosition() == MID_JOINT) {
            servoJoint.setPosition(DOWN_JOINT);
        } else {
            servoJoint.setPosition(MID_JOINT);
        }
    }

    public static void DownJoint() {
        servoJoint.setPosition(DOWN_JOINT);
    }

    public static void UpJoint() {
        servoJoint.setPosition(UP_JOINT);
    }

    public static void midJoint() {
        servoJoint.setPosition(MID_JOINT);
    }

    public static void lowerJoint() {
        servoJoint.setPosition(1);
    }

    public static Boolean fullyInside() {
        return distanceSensor.getDistance(DistanceUnit.CM) < 4 && distanceSensor2.getDistance(DistanceUnit.CM) < 4;
    }

    public static Boolean fullyOutside() {
        return distanceSensor.getDistance(DistanceUnit.CM) > 4 && distanceSensor2.getDistance(DistanceUnit.CM) > 4;
    }


    public enum SampleColor{
        RED,
        BLUE,
        YELLOW,
        NONE
    }

    public static SampleColor getColor() {
        if ((colorSensor1.red() > 210 && colorSensor1.blue() < 150 && colorSensor1.green() < 240) && (colorSensor2.red() > 150 && colorSensor2.green() < 180 && colorSensor2.blue() < 110)) {
            return SampleColor.RED;
        }

        if ((colorSensor1.blue() > 100 && colorSensor1.red() < 190 && colorSensor1.green() < 260) && (colorSensor2.blue() > 100 && colorSensor2.green() < 160 && colorSensor2.red() < 110)) {
            return SampleColor.BLUE;
        }
        if (fullyInside()) {
            return SampleColor.YELLOW;
        }
        return SampleColor.NONE;
    }

    //auto actions

    public static class lowerJointAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            DownJoint();
            return false;
        }
    }

    public static class midJointAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            midJoint();
            return false;
        }
    }

    public static class UpJointAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            UpJoint();
            return false;
        }
    }

    public static class activateEjectionAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!fullyOutside()) {
                activateEjection();
                return true;
            } else {
                stop();
                return false;
            }
        }
    }

    public static class activateInatkeAction implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            if (!fullyInside()) {
                if (servoIntakeR.getPower() == 0) {
                    activateIntake();
                }
                return true;
            } else {
                stop();
                return false;
            }
        }
    }

    public static class activateSabmersibleIntakeAction implements Action {
        SampleColor wrongColor;

        public activateSabmersibleIntakeAction(SampleColor wrongColor) {
            this.wrongColor = wrongColor;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            if (!fullyInside() || (fullyInside() && (getColor() == wrongColor || getColor() == SampleColor.NONE || getColor() == wrongColor|| getColor() == wrongColor|| getColor() == wrongColor))) {
                if (servoIntakeR.getPower() == 0) {
                    activateEjection();
                }
                return true;
            } else {
                stop();
                return false;
            }
        }
    }


}