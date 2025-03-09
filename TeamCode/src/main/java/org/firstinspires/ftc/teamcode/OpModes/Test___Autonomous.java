package org.firstinspires.ftc.teamcode.OpModes;


import static android.os.SystemClock.sleep;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.RobotSystems.CameraServo;
import org.firstinspires.ftc.teamcode.RobotSystems.Claw;

import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;

@Autonomous
public class Test___Autonomous extends OpMode {


    DriveSystem drive;
    // CameraServo cameraServo;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;
    double p;

    BNO055IMU imu;

    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;

    // AprilTagSystem aprilTag;


    @Override
    public void init() {

        drive = new DriveSystem(hardwareMap, telemetry,true);
        //  cameraServo = new CameraServo(hardwareMap);
//        erection = new ErectionSystem(hardwareMap, false);
//        elevator = new ElevatorTest(hardwareMap);
//        claw = new Claw(hardwareMap);
//        telemetry.addData("init", 1);
//        telemetry.update();
//        drive.resetEncoder2();

    }

    @Override
    public void start() {
        super.start();
        drive.resetEncoder();
        drive.resetFildo();
    }


    @Override
    public void loop() {

        telemetry.addData("OdometryStraightPosition= ", drive.getOdometryStraightPosition());
        telemetry.addData("angles=",drive.getAngel());
        telemetry.addData("", " ");
        telemetry.addData("rightBackDrive", drive.getOdometryStraightPosition());
        telemetry.addData("rightFrontDrive", drive.getRightFrontPosition());
        telemetry.addLine();
        drive.printTata();
        telemetry.addData("Angle = ",drive.getAngle());
        telemetry.update();

        drive.driveByEncoder(1,0,100,0);
    }

}
