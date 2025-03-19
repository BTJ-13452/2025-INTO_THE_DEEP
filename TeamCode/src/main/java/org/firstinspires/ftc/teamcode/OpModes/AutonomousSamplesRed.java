package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotSystems.ClimbSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.DriveSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ElbowSystem;


@Autonomous(group = "Autonomous")

public class AutonomousSamplesRed extends LinearOpMode {
    public static final double DELAY = 0.2;

    @Override
    public void runOpMode() {

        new ElbowSystem(hardwareMap, true);
        new ElevatorSystem(hardwareMap, telemetry);
        new IntakeSystem(hardwareMap);
        new ClimbSystem(hardwareMap);
        DriveSystem.setStartAngel(90, hardwareMap);

        final Pose2d currentPose = new Pose2d(-38, -61, Math.toRadians(0));

        final Pose2d scorePreloadPose = new Pose2d(-55, -54, Math.toRadians(50));

        final Pose2d intakeFirstPose = new Pose2d(-49, -38, Math.toRadians(100));
        final Pose2d scoreFirstPose = new Pose2d(-53, -52, Math.toRadians(45));

        final Pose2d intakeSecondPose = new Pose2d(-60, -34, Math.toRadians(100));
        final Pose2d scoreSecondPose = new Pose2d(-54, -51.5, Math.toRadians(45));

        final Pose2d intakeThirdPose = new Pose2d(-62, -35, Math.toRadians(135));
        final Pose2d scoreThirdPose = new Pose2d(-55, -52, Math.toRadians(35));

        final Pose2d intakeFourthPose = new Pose2d(-18, -60, Math.toRadians(-10));
        final Pose2d scoreFourthPose = new Pose2d(-55, -52, Math.toRadians(45));

        final Pose2d intakeFirstCycle = new Pose2d(-16, -8, Math.toRadians(0));

        final Pose2d scoreFirstCycle = new Pose2d(-53, -57, Math.toRadians(45));


        MecanumDrive drive = new MecanumDrive(hardwareMap, currentPose);

        //Drive Actions
        Action driveToScorePreloadSample = drive.actionBuilder(currentPose)
                .setTangent(Math.toRadians(140))
                .splineToLinearHeading(scorePreloadPose, Math.toRadians(140))
                .build();
        Action driveToIntakeFirstSample = drive.actionBuilder(scorePreloadPose)
                .setTangent(Math.toRadians(50))
                .splineToLinearHeading(intakeFirstPose, Math.toRadians(90))
                .build();
        Action driveToScoreFirstSample = drive.actionBuilder(intakeFirstPose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(scoreFirstPose, Math.toRadians(230))
                .build();
        Action driveToIntakeSecondSample = drive.actionBuilder(scoreFirstPose)
                .setTangent(Math.toRadians(40))
                .splineToLinearHeading(intakeSecondPose, Math.toRadians(85))
                .build();
        Action driveToScoreSecondSample = drive.actionBuilder(intakeSecondPose)
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(scoreSecondPose, Math.toRadians(225))
                .build();
        Action driveToIntakeThirdSample = drive.actionBuilder(scoreSecondPose)
                .setTangent(Math.toRadians(200))
                .splineToLinearHeading(intakeThirdPose, Math.toRadians(-50))
                .build();
        Action driveToScoreThirdSample = drive.actionBuilder(intakeThirdPose)
                .setTangent(Math.toRadians(300))
                .splineToLinearHeading(scoreThirdPose, Math.toRadians(220))
                .build();
        Action driveToIntakeFourthSample = drive.actionBuilder(scoreThirdPose)
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(intakeFourthPose, Math.toRadians(-10))
                .build();
        Action driveToScoreFourthSample = drive.actionBuilder(intakeFourthPose)
                .setTangent(Math.toRadians(120))
                .splineToLinearHeading(scoreFourthPose, Math.toRadians(225))
                .build();
        Action driveToFirstCycle = drive.actionBuilder(scoreFourthPose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(intakeFirstCycle, Math.toRadians(0))
                .build();
        Action driveToFirstCycleScore = drive.actionBuilder(intakeFirstCycle)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(scoreFirstCycle, Math.toRadians(270))
                .build();


        waitForStart();

        if (isStopRequested()) return;
        Actions.runBlocking(
                new SequentialAction(
                        scoringAction(driveToScorePreloadSample),
                        intakeAction(driveToIntakeFirstSample),
                        scoringAction(driveToScoreFirstSample),
                        intakeAction(driveToIntakeSecondSample),
                        scoringAction(driveToScoreSecondSample),
                        intakeAction(driveToIntakeThirdSample),
                        scoringAction(driveToScoreThirdSample),

//                        driveToIntakeFourthSample,
//                        new ParallelAction(
//                                new IntakeSystem.activateInatkeAction(),
//                                new ElevatorSystem.setElevatorPositionByTicksAction(800)
//                        ),
//                        scoringAction(driveToScoreFourthSample),

                        intakeFromSubmersibleAction(driveToFirstCycle),
                        scoringFromSubmersibleAction(driveToFirstCycleScore)
                )
        );

    }

    public Action scoringAction(Action driveScoringAction) {
        return new SequentialAction(
                new ParallelAction(
                        new IntakeSystem.midJointAction(),
                        driveScoringAction,
                        new ElbowSystem.setPositionElbowByTicksAction(1480),
                        new ElevatorSystem.setElevatorPositionByTicksAction(2285)
                ),


                new IntakeSystem.activateEjectionAction(),

                new IntakeSystem.lowerJointAction(),
                new SleepAction(DELAY),
                new ParallelAction(
                        new ElevatorSystem.setElevatorPositionByTicksAction(200),
                        new ElbowSystem.setPositionElbowByTicksAction(0)
                )
        );
    }

    public Action intakeAction(Action driveIntakeAction) {
        return new SequentialAction(
                driveIntakeAction,
                new ParallelAction(
                        new IntakeSystem.activateInatkeAction(this, telemetry ),
                        new ElevatorSystem.setElevatorPositionByTicksAction(1700)
                )
        );
    }

    public Action intakeFromSubmersibleAction(Action driveAction) {
        return new SequentialAction(
                new ParallelAction(
                        new ElevatorSystem.setElevatorPositionByTicksAction(1500),
                        new ElbowSystem.setPositionElbowByTicksAction(0),
                        new IntakeSystem.midJointAction(),
                        driveAction
                ),
                new ParallelAction(
                        new IntakeSystem.lowerJointAction(),
                        new ElevatorSystem.closeElevatorAction(),
                        new IntakeSystem.activateSabmersibleIntakeAction(IntakeSystem.SampleColor.BLUE)
                ),

                new IntakeSystem.midJointAction(),
                new ElevatorSystem.setElevatorPositionByTicksAction(0)
        );
    }

    public Action scoringFromSubmersibleAction(Action driveScoringAction) {
        return new SequentialAction(
                new ParallelAction(
                        driveScoringAction,
                        new IntakeSystem.midJointAction(),
                        new ElbowSystem.setPositionElbowByTicksAction(400),
                        new ElevatorSystem.setElevatorPositionByTicksAction(1500)
                ),
                new ElbowSystem.setPositionElbowByTicksAction(1480),
                new ElevatorSystem.setElevatorPositionByTicksAction(2300),

        new IntakeSystem.activateEjectionAction(),

                new IntakeSystem.lowerJointAction(),
                new SleepAction(DELAY),
                new ParallelAction(
                        new ElevatorSystem.setElevatorPositionByTicksAction(0),
                        new ElbowSystem.setPositionElbowByTicksAction(0)
                )
        );
    }
}