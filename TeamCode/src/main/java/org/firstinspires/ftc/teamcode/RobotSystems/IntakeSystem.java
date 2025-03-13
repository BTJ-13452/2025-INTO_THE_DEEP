package org.firstinspires.ftc.teamcode.RobotSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class IntakeSystem {
    static final private double UP_JOINT = 0.2;
    static final private double MID_JOINT = 0.63;
    public static CRServo servoIntakeL;
    public static CRServo servoIntakeR;
    public static Servo servoJoint;
    public static DistanceSensor distanceSensor;
    public static DistanceSensor distanceSensor2;

    public IntakeSystem(HardwareMap map) {
        servoIntakeL = map.get(CRServo.class, "ClawL");
        servoIntakeR = map.get(CRServo.class, "ClawR");
        servoJoint = map.servo.get("ClawJ");
        distanceSensor = map.get(DistanceSensor.class, "distanceSensor");
        distanceSensor2 = map.get(DistanceSensor.class, "distanceSensor2");

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
            servoJoint.setPosition(UP_JOINT);
        } else {
            servoJoint.setPosition(MID_JOINT);
        }
    }

    public static void raiseJoint() {
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

    //auto actions

    public static class raiseJointAction implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            raiseJoint();
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
                activateIntake();
                return true;
            } else {
                stop();
                return false;
            }
        }
    }

}

