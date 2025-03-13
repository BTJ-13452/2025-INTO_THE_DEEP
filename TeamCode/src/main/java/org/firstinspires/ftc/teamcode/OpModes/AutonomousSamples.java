package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotSystems.ClimbSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.IntakeSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ElevatorSystem;
import org.firstinspires.ftc.teamcode.RobotSystems.ElbowSystem;


@TeleOp(group = "Autonomous")

public class AutonomousSamples extends LinearOpMode {
    public static final double DELAY = 0.2;

    @Override
    public void runOpMode() {
        final Pose2d currentPose = new Pose2d(-38, -61, Math.toRadians(0));

        final Pose2d scorePreloadPose = new Pose2d(-53, -53,Math.toRadians(50));

        final Pose2d intakeFirstPose = new Pose2d(-49,-38,Math.toRadians(100));
        final Pose2d scoreFirstPose = new Pose2d(-55, -55,Math.toRadians(45));

        final Pose2d intakeSecondPose = new Pose2d(-58,-34,Math.toRadians(90));
        final Pose2d scoreSecondPose = new Pose2d(-55, -55,Math.toRadians(45));

        final Pose2d intakeThirdPose = new Pose2d(-65, -35, Math.toRadians(110));
        final Pose2d scoreThirdPose = new Pose2d(-53, -53,Math.toRadians(45));

        final Pose2d intakeFourthPose = new Pose2d(-14, -60, Math.toRadians(0));

        final Pose2d park = new Pose2d(-23,-8,Math.toRadians(180));


        MecanumDrive drive = new MecanumDrive(hardwareMap, currentPose);

        //Drive Actions
        Action driveToScorePreloadSample = drive.actionBuilder(currentPose)
                .splineToLinearHeading(scorePreloadPose,Math.toRadians(140))
                .build();
        Action driveToIntakeFirstSample = drive.actionBuilder(scorePreloadPose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(intakeFirstPose,Math.toRadians(90))
                .build();
        Action driveToScoreFirstSample = drive.actionBuilder(intakeFirstPose)
                .setTangent(Math.toRadians(220))
                .splineToLinearHeading(scoreFirstPose,Math.toRadians(220))
                .build();
        Action driveToIntakeSecondSample = drive.actionBuilder(scoreFirstPose)
                .setTangent(Math.toRadians(200))
                .splineToLinearHeading(intakeSecondPose,Math.toRadians(20))
                .build();
        Action driveToScoreSecondSample = drive.actionBuilder(intakeSecondPose)
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(scoreSecondPose,Math.toRadians(45))
                .build();
        Action driveToIntakeThirdSample = drive.actionBuilder(scoreSecondPose)
                .setTangent(Math.toRadians(110))
                .splineToLinearHeading(intakeThirdPose,Math.toRadians(110))
                .build();
        Action driveToScoreThirdSample = drive.actionBuilder(intakeThirdPose)
                .strafeToLinearHeading(scoreThirdPose.component1(),Math.toRadians(45))
                .build();
        Action driveToIntakeFourthSample = drive.actionBuilder(scoreThirdPose)
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(intakeFourthPose,Math.toRadians(0))
                .build();


        new ElbowSystem(hardwareMap, true);
        new ElevatorSystem(hardwareMap, telemetry);
        new IntakeSystem(hardwareMap);
        new ClimbSystem(hardwareMap);
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
                        driveToIntakeFourthSample
//                        driveToPark
                )
        );
    }

    public static Action scoringAction(Action driveScoringAction){
        return new SequentialAction(
                new IntakeSystem.raiseJointAction(),
                driveScoringAction,
                new SleepAction(DELAY),
                new ElbowSystem.setPositionElbowByTicksAction(1400),
                new SleepAction(DELAY),
                new ElevatorSystem.setElevatorPositionByTicksAction(2348),
                new SleepAction(DELAY),
                new IntakeSystem.activateEjectionAction(),
                new SleepAction(DELAY),
                new IntakeSystem.midJointAction(),
                new SleepAction(DELAY*4),
                new ElevatorSystem.setElevatorPositionByTicksAction(0),
                new SleepAction(DELAY),
                new ElbowSystem.setPositionElbowByTicksAction(0)
        );
    }

    public static Action intakeAction(Action driveIntakeAction){
        return new SequentialAction(
                driveIntakeAction,
                new SleepAction(DELAY),
                new ParallelAction(
                        new IntakeSystem.activateInatkeAction(),
                        new ElevatorSystem.setElevatorPositionByTicksAction(1500)
                ),
                new SleepAction(DELAY),
                new ElevatorSystem.setElevatorPositionByTicksAction(0)
        );
    }
}
