package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class ElevatorTest extends OpMode {

    DcMotor mainMotor;


    @Override
    public void init() {
        mainMotor = hardwareMap.get(DcMotor.class, "elevatormotor");
        mainMotor.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        if (gamepad1.right_bumper) {
            mainMotor.setPower(0.3);
        }

        if (gamepad1.left_bumper) {
            mainMotor.setPower(-0.3);
        }

        if (!(gamepad1.right_bumper || gamepad1.left_bumper)) {
            mainMotor.setPower(0);
        }
    }
}
