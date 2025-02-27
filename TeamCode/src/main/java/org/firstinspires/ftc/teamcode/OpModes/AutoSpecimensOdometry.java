package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotSystems.Claw;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.Hung;

@Autonomous
public class AutoSpecimensOdometry extends OpMode {
    DriveSystem drive;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;
    boolean active;

    @Override
    public void init() {
        new Hung(hardwareMap);
        drive = new DriveSystem(hardwareMap, telemetry, false);
        erection = new ErectionSystem(hardwareMap, false);
        elevator = new ElevatorTest(hardwareMap);
        claw = new Claw(hardwareMap);
        active = true;
    }

    @Override
    public void loop() {

        telemetry.addData("SP",drive.getOdometryStraightPosition());
        telemetry.update();
        drive.forward_by_odometry(10000);
    }
}
