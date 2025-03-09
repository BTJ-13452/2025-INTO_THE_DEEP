package org.firstinspires.ftc.teamcode.OpModes;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;


@Autonomous(name ="Red Sample" ,group = "Autonomous")
public class utonomous extends OpMode {
    Pose2d currentPose;
    MecanumDrive drive;

    @Override
    public void init() {
        currentPose = (new Pose2d(8.09, -64.52, Math.toRadians(90.00)));
        drive = new MecanumDrive(hardwareMap, currentPose);
    }

    @Override
    public void start() {

        super.start();



    }


    @Override
    public void loop() {
        TrajectoryActionBuilder driveToScorePreloadSample = drive.actionBuilder(currentPose).splineToLinearHeading(new Pose2d(-25.28, -40.65, Math.toRadians(176.01)), Math.toRadians(176.01));
        driveToScorePreloadSample.build();

    }
}
