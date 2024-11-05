package org.firstinspires.ftc.teamcode.OpModes;

import android.graphics.Camera;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.RobotSystems.AprilTagSystem;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.List;
@Autonomous
public class AprilTag_test extends OpMode {
    AprilTagSystem aprilTag;


    @Override
    public void init() {
        aprilTag = new AprilTagSystem(hardwareMap);
    }

    @Override
    public void loop() {
        AprilTagDetection DATA = aprilTag.GetDetection(12);
        if (DATA != null) {
            telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", DATA.ftcPose.x, DATA.ftcPose.y, DATA.ftcPose.z));
            telemetry.addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", DATA.ftcPose.pitch, DATA.ftcPose.roll, DATA.ftcPose.yaw));
            telemetry.addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", DATA.ftcPose.range, DATA.ftcPose.bearing, DATA.ftcPose.elevation));
            telemetry.update();
        }
    }
}
