package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotSystems.Claw;

@Disabled
@TeleOp
public class TeleopTest_2 extends LinearOpMode {

    Claw claw;
    boolean aIsPressed = true;


    @Override
    public void runOpMode() throws InterruptedException {
        claw = new Claw(hardwareMap);
//        claw.openServoClaw();
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("maxticks", ".getTicks()");
            telemetry.update();
            if (gamepad1.y) {
                aIsPressed = false;
//                claw.openServoClaw();
            }
        }
    }
}
