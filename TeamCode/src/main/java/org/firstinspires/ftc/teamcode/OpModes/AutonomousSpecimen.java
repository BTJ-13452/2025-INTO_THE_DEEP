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

public class AutonomousSpecimen extends LinearOpMode {
    public static final double DELAY = 0.2;

    @Override
    public void runOpMode() {

        new ElbowSystem(hardwareMap, true);
        new ElevatorSystem(hardwareMap, telemetry);
        new IntakeSystem(hardwareMap);
        new ClimbSystem(hardwareMap);
        DriveSystem.setStartAngel(0,hardwareMap);


        final Pose2d currentPose = new Pose2d(0, -61, Math.toRadians(90));

        final Pose2d ScorePreloadPose = new Pose2d(0, -30, Math.toRadians(90));

        final Pose2d DelayDrive = new Pose2d(24, -48, Math.toRadians(0));

        final Pose2d IntakeReloadFirstPose = new Pose2d(40, -65, Math.toRadians(270));

        final Pose2d ScoreReloadFirstPose = new Pose2d(0, -30, Math.toRadians(90));





        MecanumDrive drive = new MecanumDrive(hardwareMap, currentPose);

        //Drive Actions
        Action driveToScorePreloadSpecimen = drive.actionBuilder(currentPose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(ScorePreloadPose, Math.toRadians(90))
                .build();

        Action driveToDelay = drive.actionBuilder(ScorePreloadPose)
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(DelayDrive, Math.toRadians(0))
                .build();

        Action driveToIntakeFirstSpecimen = drive.actionBuilder(DelayDrive)
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(IntakeReloadFirstPose, Math.toRadians(270))
                .build();

        Action driveToScoreFirstSpecimen = drive.actionBuilder(IntakeReloadFirstPose)
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(ScoreReloadFirstPose, Math.toRadians(90))
                .build();




        waitForStart();

        if (isStopRequested()) return;
        Actions.runBlocking(
                new SequentialAction(
                        scoringAction(driveToScorePreloadSpecimen),
                        driveToDelay,
                        intakeAction(driveToIntakeFirstSpecimen),
                        scoringAction( driveToScoreFirstSpecimen)
                )

        );

    }

    public Action scoringAction(Action driveScoringAction) {
        return new SequentialAction(
                new ParallelAction(
                        driveScoringAction,
                        new ElbowSystem.setPositionElbowByTicksAction(1080),
                        new ElevatorSystem.setElevatorPositionByTicksAction(800),
                        new IntakeSystem.lowerJointAction()
                ),
                new ElevatorSystem.setElevatorPositionByTicksAction(0)


//                new IntakeSystem.activateEjectionAction(),
//
//                new IntakeSystem.lowerJointAction(),
//                new SleepAction(DELAY),
//                new ParallelAction(
//                        new ElevatorSystem.setElevatorPositionByTicksAction(400),
//                        new ElbowSystem.setPositionElbowByTicksAction(0)
//                )
        );
    }

    public Action intakeAction(Action driveIntakeAction) {
        return new SequentialAction(
                new ParallelAction(
                        driveIntakeAction,
                        new ElbowSystem.setPositionElbowByTicksAction(0),
                        new IntakeSystem.midJointAction()
                ),
                new SleepAction(DELAY*10),
                new ParallelAction(
                new IntakeSystem.activateInatkeAction(this, telemetry),
                new ElbowSystem.setPositionElbowByTicksAction(500)
                )
        );
    }
}

