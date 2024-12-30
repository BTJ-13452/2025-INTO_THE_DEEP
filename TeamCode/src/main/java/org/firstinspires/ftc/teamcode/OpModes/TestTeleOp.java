package org.firstinspires.ftc.teamcode.OpModes;

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
    public Claw claw;
    DriveSystem drive;
    private boolean XPressed = false;
    private boolean APressed = false;
    private boolean YPressed = false;
    private boolean BPressed = false;
    private boolean sampleIn = false;
    private boolean isFildoOn = true;
    private boolean left_stick_button_Pressed = false;
    ElevatorTest elevatorSystem;
    ErectionSystem elvatorErectionSystem;
    TestSheiva sheiva;
    Hung hung;

    //    ErectionSystem erectionSystem = new ErectionSystem(hardwareMap);
    @Override
    public void init() {
        claw = new Claw(hardwareMap);
        drive = new DriveSystem(hardwareMap, gamepad1, 0);
        elevatorSystem = new ElevatorTest(hardwareMap);
        elvatorErectionSystem = new ErectionSystem(hardwareMap);
        // sheiva = new TestSheiva(hardwareMap);
        hung = new Hung(hardwareMap);
    }

    @Override
    public void loop() {
//        telemetry.addData("magnet", elvatorErectionSystem.erectionMagnet.isPressed());
//        telemetry.update();

        if (isFildoOn) {
            drive.Drive();
        } else drive.DriveWithoutFildo();

        if (gamepad1.x && !XPressed) {
            XPressed = true;
            claw.frontClose();
        } else if (!gamepad1.x) {
            XPressed = false;
        }

        if (gamepad1.y && !YPressed) {
            YPressed = true;
            claw.backClose();
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

        elevatorSystem.raisedAndlowerElevator(gamepad1.left_trigger - gamepad1.right_trigger);

        if (gamepad1.right_bumper) {
            elvatorErectionSystem.raisedErection();
        }
        if (gamepad1.left_bumper) {
            elvatorErectionSystem.lowerErection();
        }
        if (!gamepad1.left_bumper && !gamepad1.right_bumper) {
            elvatorErectionSystem.stopErection();
        }


        if (gamepad1.right_stick_button && isFildoOn) {
            isFildoOn = false;
        }

        if (gamepad1.left_stick_button && !left_stick_button_Pressed) {
            drive.resetFildo();
            isFildoOn = true;
            left_stick_button_Pressed = true;
        } else if (!gamepad1.left_stick_button) {
            left_stick_button_Pressed = false;
        }


//        if (sheiva.distanceSensor.getDistance(DistanceUnit.CM) < 4 && !sampleIn) {
//            sheiva.stopSheiva();
//        }
//
//        if (sheiva.distanceSensor.getDistance(DistanceUnit.CM) < 4) {
//            sampleIn = true;
//        } else sampleIn = false;

        // sheiva.printSensor(sheiva.distanceSensor);

//        telemetry.addData("sidd", sheiva.distanceSensor.getDistance(DistanceUnit.CM));
//        telemetry.update();
    }

}
