package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.Hung;

@Autonomous
public class AutoSpecimen extends OpMode {
    @Override
    public void loop() {
    }

    DriveSystem drive;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;

    boolean active;

    @Override
    public void init() {
        new Hung(hardwareMap);

        drive = new DriveSystem(hardwareMap,telemetry);
        erection = new ErectionSystem(hardwareMap, false);
        elevator = new ElevatorTest(hardwareMap);
        claw = new Claw(hardwareMap);

        active = true;
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
        drive.forward(650);
        sleep(1000);
        erection.CloseElevator();
        sleep(1000);
        erection.erectionbyticks(700);
        sleep(1000);
        elevator.elevatorbyticks(1100);
        sleep(1000);
        erection.erectionbyticks(500);
        sleep(1000);
        claw.frontClose();
        sleep(1000);
        elevator.elevatorbyticks(0);
        sleep(1000);
        erection.CloseElevator();
        sleep(1000);
        drive.drivesidebyticks(-1500);
    }

    @Override
    public void stop() {
        active = false;
    }


}
