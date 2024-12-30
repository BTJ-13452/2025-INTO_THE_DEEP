package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestSheiva {
    Telemetry telemetry;
    private CRServo servo;
    boolean suctionIsON;
    boolean suctionIsReversed;
    public DistanceSensor distanceSensor;

    public TestSheiva(HardwareMap map) {

//        servo = map.get(CRServo.class, "sheiva");
       // distanceSensor = map.get(DistanceSensor.class,"dish");
        suctionIsON = false;
        suctionIsReversed = false;

    }
    public void openSheiva() {


        if (suctionIsON) {
           stopSheiva();
        } else {
            servo.setPower(0.5);
            suctionIsON = true;
        }
    }//לא עובד עדיין

    public void closeSheiva() {
      if (suctionIsReversed){
          stopSheiva();
      }else {
          servo.setPower(-1);
          suctionIsReversed = true;
      }
    }

    public void stopSheiva() {
        suctionIsON = false;
        suctionIsReversed = false;
        servo.setPower(0);
    }

}

