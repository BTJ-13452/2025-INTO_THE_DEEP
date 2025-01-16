//package org.firstinspires.ftc.teamcode.OpModes;
//
//
//import static android.os.SystemClock.sleep;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.Servo;
//
//import org.firstinspires.ftc.teamcode.RobotSystems.AprilTagSystem;
//import org.firstinspires.ftc.teamcode.RobotSystems.CameraServo;
//import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
//import org.firstinspires.ftc.teamcode.RobotSystems.DriveAutonomous;
//import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
//
//@Autonomous
//public class Test___Autonomous extends OpMode {
//
//
//    DriveAutonomous drive;
//    CameraServo cameraServo;
//    ErectionSystem erection;
//    ElevatorTest elevator;
//    Claw claw;
//
//   // AprilTagSystem aprilTag;
//
//
//    @Override
//    public void init() {
//
//        drive = new DriveAutonomous(hardwareMap, telemetry);
//        cameraServo = new CameraServo(hardwareMap);
//        erection = new ErectionSystem(hardwareMap);
//        elevator = new ElevatorTest(hardwareMap);
//        claw = new Claw(hardwareMap);
//    }
//
//    @Override
//    public void start() {
//        super.start();
////
//        drive.driveForDistance(-0.4, 0, drive.frontDistanceL, 35);
//
//        drive.driveByAprilTag(-0.4,52,12,0);
//
//        claw.openJoint();
//
//        sleep(500);
//
//        elevator.OpenElevator(300);
//        sleep(1000);
//        erection.openErectionByTicks(2000);
//        sleep(1000);
//        elevator.OpenElevator(200);
//
////        cameraServo.moveCamera();
////
////        drive.driveByAprilTag(0.5, 53, 11, -90);
//
//    }
//
//    @Override
//    public void loop() {
//
//    }
//}
//
