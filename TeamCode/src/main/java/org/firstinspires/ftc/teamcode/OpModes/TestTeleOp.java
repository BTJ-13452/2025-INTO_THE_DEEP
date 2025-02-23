package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Event;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.Hung;
import org.firstinspires.ftc.teamcode.RobotSystems.TestSheiva;

@TeleOp
public class TestTeleOp extends OpMode {

    final int FIRST_RANGE = 550;
    final int SECOND_RANGE = 1450;

    public Claw claw;
    DriveSystem drive;
    private boolean XPressed = false;
    private boolean right_stick_button_isPrassed = false;
    private boolean APressed = false;
    private boolean YPressed = false;
    private boolean hanging = false;
    private boolean BPressed = false;
    private boolean DpadUpPressed = false;
    private boolean sampleIn = false;
    private boolean isFildoOn = false;
    private boolean isErectionOn_120 = false;
    private boolean left_stick_button_Pressed = false;

    ElevatorTest elevatorSystem;
    ErectionSystem elvatorErectionSystem;
    TestSheiva sheiva;
    Hung hung;

    //    ErectionSystem erectionSystem = new ErectionSystem(hardwareMap);
    @Override
    public void init() {

        claw = new Claw(hardwareMap);
        drive = new DriveSystem(hardwareMap, gamepad1, 0,telemetry);
        elevatorSystem = new ElevatorTest(hardwareMap);
        elvatorErectionSystem = new ErectionSystem(hardwareMap, true);
        // sheiva = new TestSheiva(hardwareMap);
        hung = new Hung(hardwareMap);

    }

    @Override
    public void loop() {
        fixeYourSelf();
//        telemetry.addData("magnet", elvatorErectionSystem.erectionMagnet.isPressed());
//        telemetry.update();

//        if (isFildoOn) {
//            drive.Drive();
//        } else
            drive.DriveWithoutFildo();
        telemetry.addData("erectionHigh",elvatorErectionSystem.motorRight.getCurrentPosition());


        telemetry.addData("isFildoOn", isFildoOn);
        telemetry.addData("elevetorHigh",elevatorSystem.getTicks());
        telemetry.update();


        if (gamepad1.x && !XPressed) {
            XPressed = true;
            claw.frontClose();
        } else if (!gamepad1.x) {
            XPressed = false;
        }

        if (gamepad1.y && !YPressed) {
            YPressed = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    elvatorErectionSystem.raisedAndlowerErection(-1);
                    elevatorSystem.CloseElevator2();
                }
            }).start();
            hanging = true;
        } else if (!gamepad1.y)
            YPressed = false;

        if (gamepad1.a && !APressed) {
            APressed = true;
            claw.openJoint();

        } else if (!gamepad1.a)
            APressed = false;

        if (gamepad1.b && !BPressed) {
            BPressed = true;
            hung.openServoHung();
        } else if (!gamepad1.b) {
            BPressed = false;
        }
        if (gamepad1.dpad_up && !DpadUpPressed) {
            DpadUpPressed = true;
            hung.openServoHung2();
        } else if (!gamepad1.dpad_up)
            DpadUpPressed = false;


        if(!hanging){
            elvatorErectionSystem.raisedAndlowerErection(gamepad1.right_trigger - gamepad1.left_trigger);
        }

        if (gamepad1.right_bumper) {
            elevatorSystem.upperElevator(elvatorErectionSystem.motorRight.getCurrentPosition());
           // telemetry.addData("1",1);
           // telemetry.update();
        }
        if (gamepad1.left_bumper) {
            elevatorSystem.lowerElevator();
            //telemetry.addData("2",2);
            telemetry.update();
        }
        if (!gamepad1.left_bumper && !gamepad1.right_bumper && !isErectionOn_120) {
            elevatorSystem.StopElevator();
         //   telemetry.addData("3",3);
          //  telemetry.update();
        }


        if (gamepad1.right_stick_button && !right_stick_button_isPrassed) {
            right_stick_button_isPrassed = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    claw.openJoint();
                    elvatorErectionSystem.CloseElevator();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sleep(250);
                    elevatorSystem.CloseElevator();
                    claw.closeJoint();
                }
            }).start();

        } else if (!gamepad1.right_stick_button){
            right_stick_button_isPrassed = false;
        }

        if (gamepad1.left_stick_button && !left_stick_button_Pressed) {
//            drive.resetFildo();
//           isFildoOn = !isFildoOn;
            left_stick_button_Pressed = true;
            hanging = false;
        } else if (!gamepad1.left_stick_button) {
            left_stick_button_Pressed = false;
        }
      //  drive.printTata();

if (gamepad1.dpad_down){
    isErectionOn_120 = true;
    elvatorErectionSystem.openErectionByTicks(300);
}

    telemetry.addData("gilad",elvatorErectionSystem.getPosition());
    telemetry.update();
        if (elvatorErectionSystem.getPosition() <= 122 && elvatorErectionSystem.getPosition()>118){
            isErectionOn_120 = false;
        }
//        if (sheiva.distanceSensor.getDistance(DistanceUnit.CM) < 4 && !sampleIn) {
//            sheiva.stopSheiva();
//        }
//
//        if (sheiva.distanceSensor.getDistance(DistanceUnit.CM) < 4) {
//            sampleIn = true;
//        } else sampleIn = false;
        // sheiva.printSensor(sheiva.distanceSensor);
//        telemetry.update();

//        telemetry.addData("sidd", sheiva.distanceSensor.getDistance(DistanceUnit.CM));
    }


    public void fixeYourSelf(){
        if(!((elvatorErectionSystem.motorRight.getCurrentPosition() >= FIRST_RANGE) || elevatorSystem.elevator.getCurrentPosition() >= -1538)){
            elevatorSystem.elevator.setPower(1);
            elevatorSystem.elevator2.setPower(1);
            telemetry.addData("4",4);
            telemetry.update();

        }
    }

    @Override
    public void stop() {
        elevatorSystem.opmodisactive = false;
    }

}
