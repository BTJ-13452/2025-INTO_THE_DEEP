package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class SampleAutoTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setDimensions(17,16)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(-38, -61, 0))


                        .setTangent(Math.toRadians(140))
                        .splineToLinearHeading(new Pose2d(-53, -53,Math.toRadians(50)),Math.toRadians(140)) //preload

                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-48,-38,Math.toRadians(90)),Math.toRadians(90)) //intake first

                        .setTangent(Math.toRadians(220))
                        .splineToLinearHeading(new Pose2d(-54, -55,Math.toRadians(45)),Math.toRadians(220))// score first
                        .setTangent(Math.toRadians(220))
                        .splineToLinearHeading(new Pose2d(-58,-34,Math.toRadians(90)),Math.toRadians(50)) //intake second
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-55, -55,Math.toRadians(45)),Math.toRadians(45)) //score second
                        .setTangent(Math.toRadians(110))
                        .splineToLinearHeading(new Pose2d(-65, -35, Math.toRadians(110)),Math.toRadians(110)) //intake third
                        .setTangent(Math.toRadians(220))
                        .splineToLinearHeading(new Pose2d(-52, -55,Math.toRadians(45)),Math.toRadians(220)) //score third
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-14,-60,Math.toRadians(0)),Math.toRadians(0)) //intake fourth


                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}