package org.firstinspires.ftc.teamcode.OpModes;

import static android.os.SystemClock.sleep;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.RobotSystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ElbowSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ClimbSystem;

@TeleOp
public class BTJTeleOp extends OpMode {
    final int FIRST_RANGE = 550;
    final int MAX_ELBOW_POS = 1470;
    final int MAX_ELEVATOR_POS = 2450;

    IntakeSystem intake;
    DriveSystem drive;
    ElevatorSystem elevator;
    ElbowSystem elbow;
    ClimbSystem climb;

    private boolean XPressed = false;
    private boolean APressed = false;
    private boolean YPressed = false;
    private boolean BPressed = false;
    private boolean rightStickIsPressed = false;
    private boolean left_stick_button_Pressed = false;
    private boolean dpad_rightPressed = false;
    private boolean dpadLeftPressed = false;
    private boolean dpadUpPressed = false;

    private boolean sampleIn = false;
    private boolean isFieldOrientedOn = false;
    private boolean isErectionOn_1450 = false;
    private boolean isElevatorOn_2383 = false;
    private boolean isGamePieceIn = false;
    private boolean hanging = false;
    private boolean toRumble = false;


    @Override
    public void init() {

        intake = new IntakeSystem(hardwareMap);
        drive = new DriveSystem(hardwareMap, gamepad1, 0, telemetry);
        elevator = new ElevatorSystem(hardwareMap, telemetry);
        elbow = new ElbowSystem(hardwareMap, true);
        climb = new ClimbSystem(hardwareMap);
        drive.resetFildo();
    }


    @Override
    public void loop() {
        fixYourself();
        if (isFieldOrientedOn) {
            drive.Drive();
        } else {
            drive.DriveWithoutFildo();
        }

        printTelemetry();

        if (gamepad1.x && !XPressed) {
            XPressed = true;
            if (!IntakeSystem.fullyInside()) {
                IntakeSystem.activateIntake();
                sampleIn = false;
                toRumble = false;
            } else {
                sampleIn = true;
                IntakeSystem.activateIntake();
            }
        } else if (!gamepad1.x) {
            XPressed = false;
        }


        if (gamepad1.b && !BPressed) {
            BPressed = true;
            if (!IntakeSystem.fullyInside()) {
                IntakeSystem.activateEjection();
                sampleIn = false;
                toRumble = false;

            } else {
                sampleIn = true;
                IntakeSystem.activateEjection();
            }
        } else if (!gamepad1.b) {
            BPressed = false;
        }

        if (gamepad1.a && !APressed) {
            APressed = true;
            IntakeSystem.changeJointPos();
            sampleIn = false;
            toRumble = false;
        } else if (!gamepad1.a) {
            APressed = false;
        }

        if (gamepad1.dpad_left && !dpadLeftPressed) {
            dpadLeftPressed = true;
            ElbowSystem.setPositionElbowByTicks(200);
            IntakeSystem.lowerJoint();
        } else if (!gamepad1.b) {
            dpadLeftPressed = false;
        }

        if (gamepad1.dpad_right && !dpad_rightPressed) {
            dpad_rightPressed = true;
            climb.startClimb();
        } else if (!gamepad1.dpad_right) {
            dpad_rightPressed = false;
        }
        if (gamepad1.dpad_up && !dpadUpPressed) {
            dpadUpPressed = true;
            climb.openTail();
        } else if (!gamepad1.dpad_up)
            dpadUpPressed = false;

        if (!hanging && !isErectionOn_1450) {
            elbow.raisedAndLowerElbow(gamepad1.right_trigger - gamepad1.left_trigger);
        }

        if (gamepad1.right_bumper) {
            isElevatorOn_2383 = false;
            elevator.raiseElevator(ElbowSystem.motorRight.getCurrentPosition());
        }
        if (gamepad1.left_bumper) {
            isElevatorOn_2383 = false;
            elevator.lowerElevator();
        }

        if (!gamepad1.left_bumper && !gamepad1.right_bumper && !isElevatorOn_2383) {
            elevator.StopElevator();
        }

        if (gamepad1.right_stick_button && !rightStickIsPressed) {
            sampleIn = !sampleIn;
            rightStickIsPressed = true;

            IntakeSystem.raiseJoint();
            new Thread(() -> {
                sleep(500);
                IntakeSystem.midJoint();
                elbow.closeElbow();
            }).start();
            new Thread(() -> elevator.closeElevator()).start();
        } else if (!gamepad1.right_stick_button) {
            rightStickIsPressed = false;
        }

        if (gamepad1.left_stick_button && !left_stick_button_Pressed) {
            drive.resetFildo();
            isFieldOrientedOn = !isFieldOrientedOn;
            left_stick_button_Pressed = true;
            hanging = false;
        } else if (!gamepad1.left_stick_button) {
            left_stick_button_Pressed = false;
        }

        if (!YPressed && gamepad1.y && !isErectionOn_1450 && !isElevatorOn_2383) {
            YPressed = true;
            isErectionOn_1450 = true;
            isElevatorOn_2383 = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ElbowSystem.setPositionElbowByTicks(MAX_ELBOW_POS);
                    sleep(0);
                    ElevatorSystem.setElevatorPositionByTicks(MAX_ELEVATOR_POS);
                }
            }).start();
        }else if(!gamepad1.y){
            YPressed = false;
        }

        if (ElbowSystem.getPosition() <= MAX_ELBOW_POS + 2 && ElbowSystem.getPosition() >= MAX_ELBOW_POS - 2) {
            isErectionOn_1450 = false;
        }
        if (-ElevatorSystem.getTicks() <= MAX_ELEVATOR_POS + 7 && -ElevatorSystem.getTicks() >= MAX_ELEVATOR_POS - 7) {
            isElevatorOn_2383 = false;
        }

        //rumble
        if (IntakeSystem.fullyInside() && !toRumble) {
            gamepad1.rumble(50);
            toRumble = true;
        } else if (!sampleIn) {
            gamepad1.stopRumble();
        }

        if (IntakeSystem.fullyInside() && !sampleIn) {
            IntakeSystem.stop();
        }

        if (IntakeSystem.fullyInside() && !isGamePieceIn) {
            IntakeSystem.midJoint();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    elevator.closeElevator();
                }
            }).start();
            isGamePieceIn = true;
        } else if (IntakeSystem.fullyOutside()) {
            isGamePieceIn = false;
        }


    }


    public void fixYourself() {
        if (!((ElbowSystem.motorRight.getCurrentPosition() >= FIRST_RANGE) || ElevatorSystem.elevator.getCurrentPosition() >= -1538)) {
            ElevatorSystem.elevator.setPower(1);
            ElevatorSystem.elevator2.setPower(1);
        }
    }


    @Override
    public void stop() {
        elevator.opmodisactive = false;
    }

    public void printTelemetry() {
        telemetry.addData("erectionHigh", ElbowSystem.motorRight.getCurrentPosition());
        telemetry.addData("isFildoOn", isFieldOrientedOn);
        telemetry.addData("elevetorHigh", ElevatorSystem.getTicks());
        telemetry.addData("ElbowPosition ", ElbowSystem.getPosition());
        telemetry.addData("OdometryStraightPosition", drive.getOdometryStraightPosition());
        telemetry.addData("OdometrySidePosition", drive.getOdometrySidePosition());
        telemetry.addData("isElevatorOn_2383", isElevatorOn_2383);
        telemetry.addData("Angle = ", drive.getAngle());
        telemetry.addData("ds1", IntakeSystem.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("ds2", IntakeSystem.distanceSensor2.getDistance(DistanceUnit.CM));
        telemetry.addData(" ", "");
        drive.printData();
        telemetry.addData("sampleIn= ", sampleIn);
        telemetry.update();
    }

}
