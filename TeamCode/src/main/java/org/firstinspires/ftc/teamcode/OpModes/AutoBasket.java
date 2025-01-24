package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.Hung;

@Autonomous
public class AutoBasket extends OpMode {
    DriveSystem drive;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;
    DriveSystem driveside;

    @Override
    public void init() {
        new Hung(hardwareMap);

        drive = new DriveSystem(hardwareMap,telemetry);
        erection = new ErectionSystem(hardwareMap, false);
        elevator = new ElevatorTest(hardwareMap);
        claw = new Claw(hardwareMap);
        driveside = new DriveSystem(hardwareMap,telemetry );
    }


    @Override
    public void start() {
        erection.CloseElevator();
        sleep(1000);
        drive.forward(200);
        sleep(1000);
        erection.erectionbyticks(1200);
        sleep(1000);
        elevator.elevatorbyticks(2600);
        sleep(1000);
        drive.forward(280);
        claw.openJoint();
        sleep(1000);
        claw.frontClose();
        sleep(1000);
        drive.forward(0);
        sleep(1000);
        elevator.elevatorbyticks(0);
        sleep(1000);
        erection.CloseElevator();
        sleep(1000);
        drive.forward(-2000);


    }

    @Override
    public void loop() {
        //
    }
}
