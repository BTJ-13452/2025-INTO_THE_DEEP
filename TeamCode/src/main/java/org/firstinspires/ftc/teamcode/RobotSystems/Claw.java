package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    final private double MAX = 1;
    final private double MIN = 0;
    public Servo servoClaw;

    public Claw (HardwareMap map) {
        servoClaw = map.servo.get("Claw");
        servoClaw.setPosition(MAX);
    }

    public void openServoClaw() {
        if (servoClaw.getPosition() == MAX) {
            servoClaw.setPosition(MIN);

        } else {
            servoClaw.setPosition(MAX);
        }
    }

    public double getClawPosition(){
        return servoClaw.getPosition();
    }
}
