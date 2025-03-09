//package org.firstinspires.ftc.teamcode.OpModes;
//
//import static android.os.SystemClock.sleep;
//
//import com.acmerobotics.roadrunner.Pose2d;
//import com.acmerobotics.roadrunner.ProfileAccelConstraint;
//import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
//import com.acmerobotics.roadrunner.TranslationalVelConstraint;
//import com.acmerobotics.roadrunner.Vector2d;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//
//import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
//import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
//import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
//import org.firstinspires.ftc.teamcode.RobotSystems.Hung;
//
//@Autonomous (name = "Auto Basket Red",group = "Red")
//public class AutoBasket2 extends OpMode {
//    DriveSystem drive;
//    ErectionSystem erection;
//    ElevatorTest elevator;
//    Claw claw;
//    boolean active;
//    @Override
//    public void onInit() {
//        Pose2d currentPose = (new Pose2d(5.5, -62.73, Math.toRadians(90.00)));
//        PinpointDrive drive = new PinpointDrive(hardwareMap, currentPose);
//
//        TrajectoryActionBuilder driveToScorePreloadSpecimen = drive.
//                .setTangent(Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(0, -27), Math.toRadians(90), new TranslationalVelConstraint(MecanumDrive.PARAMS.maxWheelVel*0.6), new ProfileAccelConstraint(MecanumDrive.PARAMS.minProfileAccel, MecanumDrive.PARAMS.maxProfileAccel));
//
//        new Hung(hardwareMap);
//
//        drive = new DriveSystem(hardwareMap,telemetry,true);
//        erection = new ErectionSystem(hardwareMap, false);
//        elevator = new ElevatorTest(hardwareMap);
//        claw = new Claw(hardwareMap);
//        active=true;
//
//    }
//    @Override
//    public void start() {
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                while(active){
////                    drive.printTata();
////                }
////            }
////        }).run();
//       //        erection.CloseElevator();
////        drive.forward(420);
////        erection.erectionbyticks(1200);
////        sleep(1200);
////        elevator.elevatorbyticks(3200);
////        drive.ResetEncoder();
////        sleep(1000);
////        claw.openJoint();
////        sleep(1000);
////        claw.frontClose();
////        //ONE IS INSIDE
////        drive.driveRightbyticks(500);
////        elevator.elevatorbyticks(0);
////        sleep(700);
////        drive.ResetEncoder();
////        drive.SpinRight(700);
////        sleep(1000);
////        erection.erectionbyticks(0);
////        sleep(1000);
////        elevator.elevatorbyticks(1200);
////        sleep(1200);
////        claw.frontClose();
////        sleep(300);
////        elevator.elevatorbyticks(500);
////        drive.ResetEncoder();
////        drive.SpinRight(780);
////        drive.forward(-280);
////        erection.erectionbyticks(1470);
////        sleep(1000);
////        elevator.elevatorbyticks(3200);
////        sleep(1300);
////        claw.openJoint();
////        sleep(300);
////        claw.frontClose();
////        drive.ResetEncoder();
////        sleep(200);
////        claw.openJoint();
////        sleep(500);
////        //SECOND IS INSIDE
////        erection.erectionbyticks(1200);
////        elevator.elevatorbyticks(0);
////        sleep(1000);
////        erection.erectionbyticks(0);
////        sleep(600);
////        drive.SpinLeft(120);
////        elevator.elevatorbyticks(1900);
////        sleep(1000);
////        claw.frontClose();
////        sleep(500);
////        drive.SpinLeft(0);
////        erection.erectionbyticks(1470);
////        sleep(1000);
////        elevator.elevatorbyticks(3200);
////        sleep(700);
////        claw.openJoint();
////        sleep(300);
////        claw.frontClose();
////        sleep(200);
////        claw.openJoint();
////        sleep(500);
////        //THIRD IS INSIDE
////        erection.erectionbyticks(1200);
////        elevator.elevatorbyticks(0);
////        sleep(1000);
////        erection.erectionbyticks(0);
////        sleep(1000);
////        drive.SpinLeft(240);
////        elevator.elevatorbyticks(2000);
////        sleep(1000);
////        claw.frontClose();
////        sleep(500);
////        drive.SpinLeft(0);
////        erection.erectionbyticks(1470);
////        sleep(1000);
////        elevator.elevatorbyticks(3200);
////        sleep(700);
////        claw.openJoint();
////        sleep(300);
////        claw.frontClose();
////        drive.ResetEncoder();
////        sleep(200);
////        claw.openJoint();
////        sleep(500);
////        //FORTH IS INSIDE
////        erection.erectionbyticks(1200);
////        elevator.elevatorbyticks(0);
////        sleep(1000);
////        erection.erectionbyticks(0);
////        drive.forward(1200);
////        sleep(2000);
////        drive.ResetEncoder();
////        drive.SpinRight(400);
////        claw.openJoint();
////        sleep(700);
////        drive.ResetEncoder();
////        drive.forward(600);
////
//    }
//
//    @Override
//    public void loop() {
//        //
//    }
//}
