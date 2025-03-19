package org.firstinspires.ftc.teamcode.RobotSystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.reflect.Field;

public class ClimbSystem {

    final static private double MIN_CLIMB = 0;
    final static private double MIN_TAIL = 0.6;
    final static private double MAX_CLIMB = 1;
    final static private double MAX_TAIL = 0.2;

    public static Servo servoClimb;
    public static Servo servoTail;

    public ClimbSystem(HardwareMap map) {
        servoClimb = map.servo.get("Hung");
        servoClimb.setPosition(MAX_CLIMB);
        servoTail = map.servo.get("Hung2");
        servoTail.setPosition(MAX_TAIL);
    }

    public static void startClimb() {
        if (servoClimb.getPosition() == MAX_CLIMB) {
            servoClimb.setPosition(MIN_CLIMB);
        } else {
            servoClimb.setPosition(MAX_CLIMB);
        }
    }

    public static void openTail() {
        if (servoTail.getPosition() == MAX_TAIL) {

            servoTail.setPosition(MIN_TAIL);

        } else {
            servoTail.setPosition(MAX_TAIL);
        }
    }



}
