package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hung {

    final private double MAX = 1;
    final private double MIN = 0;
    public Servo servoHung;

    public Hung (HardwareMap map) {
        servoHung = map.servo.get("Hung");
        servoHung.setPosition(MAX);
    }

    public void openServoHung() {
        if (servoHung.getPosition() == MAX) {
            servoHung.setPosition(MIN);

        } else {
            servoHung.setPosition(MAX);
        }
    }

    public double getClawPosition(){
        return servoHung.getPosition();
    }
}
