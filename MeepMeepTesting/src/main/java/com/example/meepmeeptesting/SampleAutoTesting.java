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
                        .splineToLinearHeading(new Pose2d(-54, -51,Math.toRadians(50)),Math.toRadians(140)) //preload

                        .setTangent(Math.toRadians(50))
                        .splineToLinearHeading(new Pose2d(-49,-38,Math.toRadians(90)),Math.toRadians(90)) //intake first

                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-55, -50,Math.toRadians(45)),Math.toRadians(230))// score first
                        .setTangent(Math.toRadians(40))
                        .splineToLinearHeading(new Pose2d(-60,-34,Math.toRadians(90)),Math.toRadians(90)) //intake second
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-53, -51,Math.toRadians(45)),Math.toRadians(225)) //score second
                        .setTangent(Math.toRadians(200))
                        .splineToLinearHeading(new Pose2d(-62, -35, Math.toRadians(120)),Math.toRadians(-50)) //intake third
                        .setTangent(Math.toRadians(300))
                        .splineToLinearHeading(new Pose2d(-56, -53,Math.toRadians(35)),Math.toRadians(220)) //score third
//                        .setTangent(Math.toRadians(0))
//                        .splineToLinearHeading(new Pose2d(-18,-59,Math.toRadians(-10)),Math.toRadians(-10)) //intake fourth
//                        .setTangent(Math.toRadians(170))
//                        .splineToLinearHeading(new Pose2d(-56, -55,Math.toRadians(45)),Math.toRadians(180)) //score fourth
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-16, -8,Math.toRadians(0)),Math.toRadians(0)) //Intake first cycle
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(-54, -57,Math.toRadians(45)),Math.toRadians(270)) //Score first cycle



                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}