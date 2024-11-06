package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;

@TeleOp
public class TestTeleOp extends OpMode {

    DriveSystem drive;

    @Override
    public void init() {
        drive = new DriveSystem();

    }

    @Override
    public void loop() {
        drive.Drive(hardwareMap, gamepad1);
    }
}
