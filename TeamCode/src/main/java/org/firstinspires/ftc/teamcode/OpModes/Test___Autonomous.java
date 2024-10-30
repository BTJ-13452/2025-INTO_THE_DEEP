package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotSystems.DriveAutonomous;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;

@Autonomous
public class Test___Autonomous extends OpMode {


DriveAutonomous drive;

    @Override
    public void init() {

        drive = new DriveAutonomous(hardwareMap,telemetry);
        
    }

    @Override
    public void start() {
        super.start();

        drive.driveForDistance(0.4,0,drive.frontDistance,50);
        drive.driveForDistance(0.4,90 ,drive.rightDistance,50);
    }

    @Override
    public void loop() {

    }
}

