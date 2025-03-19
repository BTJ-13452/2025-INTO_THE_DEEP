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
    final int MAX_ELEVATOR_POS = 2550;

    DriveSystem drive;

    private boolean XPressed = false;
    private boolean APressed = false;
    private boolean YPressed = false;
    private boolean BPressed = false;
    private boolean rightStickIsPressed = false;
    private boolean left_stick_button_Pressed = false;
    private boolean dpad_rightPressed = false;
    private boolean dpadDownPressed = false;
    private boolean dpadUpPressed = false;
    private boolean dpadLeftPressed = false;
    private boolean sampleIn = false;
    private boolean isFieldOrientedOn = true;
    private boolean isErectionOn_1450 = false;
    private boolean isElevatorOn_2383 = false;
    private boolean isGamePieceIn = false;
    private boolean hanging = false;
    private boolean toRumble = false;

    //The variables of hanging
    private boolean hangingErection = false;
    private boolean hangingElevator = false;
    private boolean closeHanging = false;

    @Override
    public void init() {
        ElevatorSystem.opmodisactive = true;
        new IntakeSystem(hardwareMap);
        drive = new DriveSystem(hardwareMap, gamepad1, telemetry);
        new ClimbSystem(hardwareMap);
        DriveSystem.isAutoFieldOriented = false;
    }

    @Override
    public void start() {
        new ElevatorSystem(hardwareMap, telemetry);
        new ElbowSystem(hardwareMap, true);
    }


    @Override
    public void loop() {
        //close systems
        if (gamepad1.dpad_down && !dpadDownPressed) {
            dpadDownPressed = true;
            closeHanging = !closeHanging ;
            ElbowSystem.motorRight.setPower(-1.0);
            ElbowSystem.motorLeft.setPower(-1.0);
            ElevatorSystem.elevator.setPower(1.0);
            ElevatorSystem.elevator2.setPower(1.0);
        } else if (!gamepad1.dpad_down) {
            dpadDownPressed = false;
        }

        if (!closeHanging) {

            fixYourself();
            if (isFieldOrientedOn) {
                drive.Drive();
            } else {
                drive.DriveWithoutFildo();
            }

            printTelemetry();

            if (gamepad1.x && !XPressed && !closeHanging) {
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


            if (gamepad1.b && !BPressed && !closeHanging) {
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

            if (gamepad1.a && !APressed && !closeHanging) {
                APressed = true;
                IntakeSystem.changeJointPos();
                sampleIn = false;
                toRumble = false;
            } else if (!gamepad1.a) {
                APressed = false;
            }


            if (gamepad1.dpad_right && !dpad_rightPressed) {
                dpad_rightPressed = true;
                ClimbSystem.startClimb();
            } else if (!gamepad1.dpad_right) {
                dpad_rightPressed = false;
            }
            if (gamepad1.dpad_up && !dpadUpPressed) {
                dpadUpPressed = true;
                ClimbSystem.openTail();
            } else if (!gamepad1.dpad_up)
                dpadUpPressed = false;

            if (!hanging && !isErectionOn_1450 && !hangingErection) {
                ElbowSystem.raisedAndLowerElbow(gamepad1.right_trigger - gamepad1.left_trigger);
            }

            if (gamepad1.right_bumper) {
                isElevatorOn_2383 = false;
                hangingElevator = false;
                ElevatorSystem.raiseElevator(ElbowSystem.motorRight.getCurrentPosition());
            }
            if (gamepad1.left_bumper) {
                isElevatorOn_2383 = false;
                hangingElevator = false;
                ElevatorSystem.lowerElevator();
            }

            if (!gamepad1.left_bumper && !gamepad1.right_bumper && !isElevatorOn_2383 && !hangingElevator) {
                ElevatorSystem.StopElevator();
            }

            if (gamepad1.right_stick_button && !rightStickIsPressed) {
                sampleIn = !sampleIn;
                rightStickIsPressed = true;

                IntakeSystem.DownJoint();
                new Thread(() -> {
                    sleep(500);
                    IntakeSystem.midJoint();
                    ElbowSystem.closeElbow();
                }).start();
                new Thread(() -> ElevatorSystem.closeElevator()).start();
            } else if (!gamepad1.right_stick_button) {
                rightStickIsPressed = false;
            }

            if (gamepad1.left_stick_button && !left_stick_button_Pressed) {
                drive.resetFieldOriented();
                isFieldOrientedOn = !isFieldOrientedOn;
                left_stick_button_Pressed = true;
                hanging = false;
            } else if (!gamepad1.left_stick_button) {
                left_stick_button_Pressed = false;
            }
            //the button of levi
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
            } else if (!gamepad1.y) {
                YPressed = false;
            }

            if (ElbowSystem.getPosition() <= MAX_ELBOW_POS + 2 && ElbowSystem.getPosition() >= MAX_ELBOW_POS - 2) {
                isErectionOn_1450 = false;
            }
            if (-ElevatorSystem.getTicks() <= MAX_ELEVATOR_POS + 7 && -ElevatorSystem.getTicks() >= MAX_ELEVATOR_POS - 7) {
                isElevatorOn_2383 = false;
            }


            if (!dpadLeftPressed && gamepad1.dpad_left && !hangingElevator && !hangingErection) {
                dpadLeftPressed = true;
                hangingErection = true;
                hangingElevator = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ElbowSystem.setPositionElbowByTicks(ElbowSystem.HANGING_ELBOW_POSE);
                        sleep(0);
                        ElevatorSystem.setElevatorPositionByTicks(ElevatorSystem.HANGING_ELEVATOR_POSE);
                        ClimbSystem.openTail();
                        ClimbSystem.startClimb();
                        IntakeSystem.lowerJoint();
                    }
                }).start();
            } else if (!gamepad1.dpad_left) {
                dpadLeftPressed = false;
            }

            if (ElbowSystem.getPosition() <= ElbowSystem.HANGING_ELBOW_POSE + 2 && ElbowSystem.getPosition() >= ElbowSystem.HANGING_ELBOW_POSE - 2) {
                hangingErection = false;
            }
            if (-ElevatorSystem.getTicks() <= ElevatorSystem.HANGING_ELEVATOR_POSE + 7 && -ElevatorSystem.getTicks() >= ElevatorSystem.HANGING_ELEVATOR_POSE - 7) {
                hangingElevator = false;
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
                        ElevatorSystem.closeElevator();
                    }
                }).start();
                isGamePieceIn = true;
            } else if (IntakeSystem.fullyOutside()) {
                isGamePieceIn = false;
            }
        }


    }


    public void fixYourself() {
        if (!((ElbowSystem.motorRight.getCurrentPosition() >= FIRST_RANGE) || ElevatorSystem.elevator.getCurrentPosition() >= -1800)) {
            ElevatorSystem.elevator.setPower(1);
            ElevatorSystem.elevator2.setPower(1);
        }
    }


    @Override
    public void stop() {
        ElevatorSystem.opmodisactive = false;
    }

    public void printTelemetry() {
        telemetry.addData("ds1", IntakeSystem.distanceSensor.getDistance(DistanceUnit.CM));
        telemetry.addData("ds2", IntakeSystem.distanceSensor2.getDistance(DistanceUnit.CM));
        telemetry.addData("color", IntakeSystem.getColor());
        telemetry.addData("r", IntakeSystem.colorSensor1.red());
        telemetry.addData("g", IntakeSystem.colorSensor1.green());
        telemetry.addData("b", IntakeSystem.colorSensor1.blue());
        telemetry.update();
    }

}
