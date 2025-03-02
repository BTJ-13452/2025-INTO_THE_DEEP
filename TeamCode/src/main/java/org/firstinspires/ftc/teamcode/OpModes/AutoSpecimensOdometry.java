package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
import org.firstinspires.ftc.teamcode.RobotSystems.Hung;
import org.firstinspires.ftc.teamcode.RobotSystems.AutonomousDriveSystem;

@Autonomous
public class AutoSpecimensOdometry extends OpMode {
    AutonomousDriveSystem drive;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;
    boolean active;

    @Override
    public void init() {
        new Hung(hardwareMap);
        drive = new AutonomousDriveSystem(hardwareMap, telemetry, false);
        erection = new ErectionSystem(hardwareMap, false);
        elevator = new ElevatorTest(hardwareMap);
        claw = new Claw(hardwareMap);
        active = true;
    }

    @Override
    public void loop() {

//        telemetry.addData("SP",drive.getOdometryStraightPosition());
        //telemetry.update();
        drive.driveStraightWithGyro(0.3, 1000);
//        drive.driveStraightWithoutGyro(0.3, 1000);

//        drive.printGyroValues();
    }
}
