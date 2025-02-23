package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hung {

    final private double MAX = 1;
    final private double MIN = 0;
    final private double MAX2 = 0;
    final private double MIN2 = 1;
    public Servo servoHung;
    public Servo servoHung2;

    public Hung (HardwareMap map) {
        servoHung = map.servo.get("Hung");
        servoHung.setPosition(MAX);
        servoHung2 = map.servo.get("Hung2");
        servoHung2.setPosition(MAX2);
    }

    public void openServoHung() {
        if (servoHung.getPosition() == MAX) {
            servoHung.setPosition(MIN);

        } else {
            servoHung.setPosition(MAX);
        }
    }
    public void openServoHung2() {
        if (servoHung2.getPosition() == MAX2) {
            servoHung2.setPosition(MIN2);

        } else {
            servoHung2.setPosition(MAX2);
        }
    }

    public double getClawPosition(){
        return servoHung.getPosition();
    }
}
