package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.Hung;

@Autonomous
public class AutoTwoSpecimens extends OpMode {
    DriveSystem drive;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;
//    boolean active;
    @Override
    public void init() {
        new Hung(hardwareMap);

        drive = new DriveSystem(hardwareMap,telemetry,false);
        erection = new ErectionSystem(hardwareMap, false);
        elevator = new ElevatorTest(hardwareMap);
        claw = new Claw(hardwareMap);
//        active=true;
    }

    @Override
    public void start() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(active){
//                    drive.printTata();
//                }
//            }
//        }).run();
        drive.forward(-550);
        sleep(1000);
        erection.CloseElevator();
        sleep(1000);
        erection.erectionbyticks(2000);
        sleep(1000);
        elevator.elevatorbyticks(908);
        sleep(1000);
        erection.erectionbyticks(1550);
        sleep(1000);
        claw.frontClose();
        sleep(1000);
        elevator.elevatorbyticks(0);
        sleep(1000);
        //first
        drive.forward(0);
        sleep(2000);
        drive.driveLeftbyticks(1350);
        sleep(1000);
        erection.erectionbyticks(115);
        sleep(3000);
        claw.frontClose();
        sleep(1000);
        erection.erectionbyticks(500);
        sleep(1000);
        drive.forward(-550);
        sleep(3000);
        drive.ResetEncoder();
        drive.driveRightbyticks(300);
        sleep(1000);
        erection.erectionbyticks(1800);
        sleep(1000);
        elevator.elevatorbyticks(706);
        sleep(1000);
        erection.erectionbyticks(1450);
        sleep(1000);
        claw.frontClose();
        sleep(1000);
        elevator.elevatorbyticks(0);
        sleep(1000);
        drive.forward(500);
        sleep(1500);
        drive.ResetEncoder();
        sleep(1000);
        drive.driveLeftbyticks(1350);
    }

    @Override
    public void loop() {
        //
    }
}
