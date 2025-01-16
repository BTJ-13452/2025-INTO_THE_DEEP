//package org.firstinspires.ftc.teamcode.RobotSystems;
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.robotcore.internal.ftdi.eeprom.FT_EEPROM_232H;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//import org.openftc.easyopencv.OpenCvWebcam;
//
//import java.util.List;
//
//public class AprilTagSystem {
//
//    private AprilTagProcessor aprilTag;
//    private VisionPortal visionPortal;
//
//    public AprilTagSystem(HardwareMap map) {
//        aprilTag = new AprilTagProcessor.Builder()
//                .setTagFamily(AprilTagProcessor.TagFamily.TAG_36h11)
//                .setTagLibrary(AprilTagGameDatabase.getIntoTheDeepTagLibrary())
//                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
//                  .setLensIntrinsics(578.272, 578.272, 402.145, 221.506)
//                .build();
//
//        visionPortal = new VisionPortal.Builder()
//                .setCamera(map.get(WebcamName.class, "Webcam 1"))
//                .addProcessor(aprilTag)
//                .build();
//
//
//    }
//
//    public List<AprilTagDetection> getAprilTagDetection(){
//        return aprilTag.getDetections();
//        //    telemetry.addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
//        //   telemetry.addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
//        //        detection.metadata != null
//    }
//
//  public AprilTagDetection GetDetection (int ID) {
//        List<AprilTagDetection> AT  = getAprilTagDetection();
//        for (AprilTagDetection DATA : AT) {
//            if (DATA.id == ID) {
//                return DATA;
//            }
//
//        }
//
//        return null;
//    }
//}
