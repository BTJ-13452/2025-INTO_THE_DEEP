package org.firstinspires.ftc.teamcode.OpModes;
import static android.os.SystemClock.sleep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.apache.commons.math3.ode.events.Action;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotSystems.Claw;



@Autonomous(name ="Red Sample" ,group = "Autonomous")

public class utonomous extends OpMode {
    Pose2d currentPose;
    MecanumDrive drive;
    ErectionSystem erection;
    ElevatorTest elevator;
    Claw claw;
    public static final double DELAY = 1;

    @Override
    public void init() {
        currentPose = (new Pose2d(-38, -61, Math.toRadians(0)));
        drive = new MecanumDrive(hardwareMap, currentPose);
        erection = new ErectionSystem(hardwareMap,true);
        elevator = new ElevatorTest(hardwareMap);
        claw = new Claw(hardwareMap);
    }

    @Override
    public void start() {

        super.start();
        TrajectoryActionBuilder driveToScorePreloadSample = drive.actionBuilder(currentPose).splineToLinearHeading(new Pose2d(-25.28, -40.65, Math.toRadians(176.01)), Math.toRadians(176.01));
        driveToScorePreloadSample.build();
        drive.actionBuilder(currentPose);

//        MecanumDrive.FollowTrajectoryAction(driveToScorePreloadSample);


    }


    @Override
    public void loop() {




        Actions.runBlocking(
                drive.actionBuilder(currentPose)
//                        .splineTo(new Vector2d(10, 0), Math.PI / 2)

                        .lineToX(-47)
                        //סמפל ראשון בפנים
                        .waitSeconds(DELAY)
                        .strafeTo(new Vector2d(-47,-75))
                        .turn(Math.toRadians(90))
                        .waitSeconds(DELAY)
                        .lineToY(-80)
                        .turn(Math.toRadians(-45))
                        //סמפל שני בפנים
                        .turn(Math.toRadians(45))
                        .strafeTo(new Vector2d(-40,-80))
                        .turn(Math.toRadians(-45))
                        //סמפל שלישי בפנים
                        .turn(Math.toRadians(65))
                        .waitSeconds(DELAY)
                        .turn(Math.toRadians(-65))
                        .turn(Math.toRadians(45))
                        .lineToY(-38)
                        .turn(Math.toRadians(-90))
                        .lineToX(-2)





//                        .turn(Math.toRadians(-135))
                        //סמפל רביעי בפנים











//                        .lineToY(-50)
                        .waitSeconds(25)
                        .build());



    }


}
