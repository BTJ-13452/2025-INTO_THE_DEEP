package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class CameraServo {
    final private double MAX = 1;
    final private double MIN = 0;
    public Servo cameraServo;
    public CameraServo (HardwareMap map){
        cameraServo = map.servo.get("CameraServo");
        cameraServo.setPosition(MAX);
    }
    public void moveCamera(){
        if (cameraServo.getPosition() == MAX){
            cameraServo.setPosition(MIN);
        } else {
            cameraServo.setPosition(MAX);
        }
    }
}
