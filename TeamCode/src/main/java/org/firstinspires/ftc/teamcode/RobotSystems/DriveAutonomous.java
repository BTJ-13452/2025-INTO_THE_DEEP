//package org.firstinspires.ftc.teamcode.RobotSystems;
//
//import static com.sun.tools.doclint.Entity.theta;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.DistanceSensor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.IMU;
//
//import org.firstinspires.ftc.robotcore.external.Telemetry;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
//import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
//import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//
//public class DriveAutonomous {
//
//    private DcMotor leftFrontDrive;
//    private DcMotor leftBackDrive;
//    private DcMotor rightFrontDrive;
//    private DcMotor rightBackDrive;
//
//    public DistanceSensor frontDistance;
//    public DistanceSensor frontDistanceL;
//    Telemetry telemetry;
//    AprilTagSystem aprilTag;
//    Orientation angles = new Orientation();
//    IMU imu;
//
//    public DriveAutonomous(HardwareMap map, Telemetry telemetry) {
//
//        aprilTag = new AprilTagSystem(map);
//        imu = map.get(IMU.class, "imu");
//        this.telemetry = telemetry;
//        leftBackDrive = map.get(DcMotor.class, "BL");
//        leftFrontDrive = map.get(DcMotor.class, "FL");
//        rightBackDrive = map.get(DcMotor.class, "BR");
//        rightFrontDrive = map.get(DcMotor.class, "FR");
//        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        frontDistance = map.get(DistanceSensor.class, "FrontDistance");
//        frontDistanceL = map.get(DistanceSensor.class,"frontDistanceL");
//        rightFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//    }
//
//
//    public void driveForDistance(double power, int angle, DistanceSensor sensor, double distance) {
//
//        double sin = Math.sin((angle * (Math.PI / 180)) - (Math.PI / 4));
//        double cos = Math.cos((angle * (Math.PI / 180)) - (Math.PI / 4));
//        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));
//        double x = distance + 1;
//        double leftFront = (power * cos / maxsincos);
//        double rightFront = (power * sin / maxsincos);
//        double leftBack = (power * sin / maxsincos);
//        double rightBack = (power * cos / maxsincos);
//
//        telemetry.addData("hi",sensor.getDistance(DistanceUnit.CM) );
//        telemetry.update();
//
//
//        while (sensor.getDistance(DistanceUnit.CM) < distance) {
//            leftFrontDrive.setPower(leftFront);
//            rightFrontDrive.setPower(rightFront);
//            leftBackDrive.setPower(leftBack);
//            rightBackDrive.setPower(rightBack);
//
//            x = sensor.getDistance(DistanceUnit.CM);
//            telemetry.addData("dis", x);
//            telemetry.update();
//
//            if (sensor.getDistance(DistanceUnit.CM) > distance / 1.5) {
//                leftFront /= 2;
//                rightFront /= 2;
//                leftBack /= 2;
//                rightBack /= 2;
//            }
//        }
//
//
//        telemetry.addData("dis", x);
//        telemetry.update();
//
//        leftFrontDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightBackDrive.setPower(0);
//    }
//
//
//
//
//    public void driveByAprilTag(double power, int distance, int ID, int angle) {
//
//        AprilTagDetection DATA = aprilTag.GetDetection(ID);
//
//        // המתנה לקבלת מידע על ה-AprilTag
//        while (DATA == null) {
//            DATA = aprilTag.GetDetection(ID);
//
//        }
//
//        // מחשבים את כוחות המנועים לפי הזווית והמרחק
//        double sin = Math.sin((angle * (Math.PI / 180)) - (Math.PI / 4));
//        double cos = Math.cos((angle * (Math.PI / 180)) - (Math.PI / 4));
//        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));
//        double x = distance;
//        double leftFront = (power * cos / maxsincos);
//        double rightFront = (power * sin / maxsincos);
//        double leftBack = (power * sin / maxsincos);
//        double rightBack = (power * cos / maxsincos);
//
//        if (power < 0) {
//            while (DATA.ftcPose.y < distance) {
//                do {
//                    DATA = aprilTag.GetDetection(ID);
//                } while (DATA == null);
//
//                // התיישרות עם החיישן
//                alignWithSensors();
//
//                leftFrontDrive.setPower(leftFront);
//                rightFrontDrive.setPower(rightFront);
//                leftBackDrive.setPower(leftBack);
//                rightBackDrive.setPower(rightBack);
//
//                x = DATA.ftcPose.y;
//                telemetry.addData("dis", x);
//                telemetry.update();
//
//                // אם הרובוט רחוק מדי מהמרחק הרצוי, נקטין את הכוח
//                if (DATA.ftcPose.y > distance / 1.5) {
//                    leftFront /= 1.5;
//                    rightFront /= 1.5;
//                    leftBack /= 1.5;
//                    rightBack /= 1.5;
//                }
//            }
//        } else {
//            while (DATA.ftcPose.y > distance) {
//                do {
//                    DATA = aprilTag.GetDetection(ID);
//                } while (DATA == null);
//
//                // התיישרות עם החיישן
//                alignWithSensors();
//
//                leftFrontDrive.setPower(leftFront);
//                rightFrontDrive.setPower(rightFront);
//                leftBackDrive.setPower(leftBack);
//                rightBackDrive.setPower(rightBack);
//
//                x = DATA.ftcPose.y;
//                telemetry.addData("dis", x);
//                telemetry.update();
//            }
//        }
//
//        telemetry.addData("dis2", x);
//        telemetry.update();
//
//        leftFrontDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightBackDrive.setPower(0);
//    }
//
//    public void alignWithSensors() {
//        // מקבלים את הזווית הנוכחית של הרובוט
//        double currentAngle = getRobotAngle();
//
//        // נבדוק אם יש שגיאה בזווית (תנאי להתיישרות)
//        double angleError = currentAngle - 0; // זווית היעד כאן היא 0 (הקו הישר)
//        double correctionFactor = 0.02; // גורם תיקון
//
//        // אם יש שגיאה בזווית, נבצע תיקון על המנועים
//        if (Math.abs(angleError) > 1) {  // אם השגיאה בזווית גדולה מ-1 מעלות
//            double correction = correctionFactor * angleError;
//
//            // נוסיף תיקון למנועים
//            leftFrontDrive.setPower(leftFrontDrive.getPower() - correction);
//            rightFrontDrive.setPower(rightFrontDrive.getPower() + correction);
//            leftBackDrive.setPower(leftBackDrive.getPower() - correction);
//            rightBackDrive.setPower(rightBackDrive.getPower() + correction);
//        }
//    }
//
//    private double getRobotAngle() {
//        // מחזירים את הזווית הנוכחית של הרובוט מה-IMU
//        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
//        return angles.firstAngle;  // זווית ה-Yaw (הזווית על הציר האנכי)
//    }
//
//}
