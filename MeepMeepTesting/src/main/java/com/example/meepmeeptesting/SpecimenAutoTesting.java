package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class SpecimenAutoTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(500);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setDimensions(17, 16)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(0, -61, 0))

                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(0, -30, Math.toRadians(0)), Math.toRadians(90)) //preload
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(24, -48, Math.toRadians(90)), Math.toRadians(0)) //preload
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(40, -65, Math.toRadians(180)), Math.toRadians(270)) //getFirst
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(0, -30, Math.toRadians(0)), Math.toRadians(90)) //ScoreFirst
                        .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}