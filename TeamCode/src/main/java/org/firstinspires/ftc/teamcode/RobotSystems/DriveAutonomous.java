package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

public class DriveAutonomous {

    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;

    public DistanceSensor frontDistance;
    public DistanceSensor rightDistance;
    Telemetry telemetry;
    AprilTagSystem aprilTag;


    public DriveAutonomous(HardwareMap map, Telemetry telemetry) {

        aprilTag = new AprilTagSystem(map);

        this.telemetry = telemetry;
        leftBackDrive = map.get(DcMotor.class, "BL");
        leftFrontDrive = map.get(DcMotor.class, "FL");
        rightBackDrive = map.get(DcMotor.class, "BR");
        rightFrontDrive = map.get(DcMotor.class, "FR");

        frontDistance = map.get(DistanceSensor.class, "FrontDistance");

        rightFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }


    public void driveForDistance(double power, int angle, DistanceSensor sensor, double distance) {

        double sin = Math.sin((angle * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((angle * (Math.PI / 180)) - (Math.PI / 4));
        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));
        double x = distance + 1;
        double leftFront = (power * cos / maxsincos);
        double rightFront = (power * sin / maxsincos);
        double leftBack = (power * sin / maxsincos);
        double rightBack = (power * cos / maxsincos);


        while (sensor.getDistance(DistanceUnit.CM) < distance) {
            leftFrontDrive.setPower(leftFront);
            rightFrontDrive.setPower(rightFront);
            leftBackDrive.setPower(leftBack);
            rightBackDrive.setPower(rightBack);

            x = sensor.getDistance(DistanceUnit.CM);
            telemetry.addData("dis", x);
            telemetry.update();

            if (sensor.getDistance(DistanceUnit.CM) > distance / 1.5) {
                leftFront /= 2;
                rightFront /= 2;
                leftBack /= 2;
                rightBack /= 2;
            }
        }


        telemetry.addData("dis", x);
        telemetry.update();

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }


    public void driveByAprilTAg(double power, int distance, int ID, int angle) {
        AprilTagDetection DATA = aprilTag.GetDetection(ID);
        while (DATA == null) {
            DATA = aprilTag.GetDetection(ID);
        }

        double sin = Math.sin((angle * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((angle * (Math.PI / 180)) - (Math.PI / 4));
        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));
        double x = distance;
        double leftFront = (power * cos / maxsincos);
        double rightFront = (power * sin / maxsincos);
        double leftBack = (power * sin / maxsincos);
        double rightBack = (power * cos / maxsincos);


        if (power < 0) {
            while (DATA.ftcPose.y < distance) {
                do {
                    DATA = aprilTag.GetDetection(ID);
                } while (DATA == null);
                leftFrontDrive.setPower(leftFront);
                rightFrontDrive.setPower(rightFront);
                leftBackDrive.setPower(leftBack);
                rightBackDrive.setPower(rightBack);

                x = DATA.ftcPose.y;
                telemetry.addData("dis", x);
                telemetry.update();


                if (DATA.ftcPose.y > distance / 1.5) {
                    leftFront /= 1.5;
                    rightFront /= 1.5;
                    leftBack /= 1.5;
                    rightBack /= 1.5;
                }
            }
        } else {
            while (DATA.ftcPose.y > distance) {
                do {
                    DATA = aprilTag.GetDetection(ID);
                } while (DATA == null);
                leftFrontDrive.setPower(leftFront);
                rightFrontDrive.setPower(rightFront);
                leftBackDrive.setPower(leftBack);
                rightBackDrive.setPower(rightBack);

                x = DATA.ftcPose.y;
                telemetry.addData("dis", x);
                telemetry.update();


//                if (DATA.ftcPose.y < distance * 1.5) {
//                    leftFront /= 1.5;
//                    rightFront /= 1.5;
//                    leftBack /= 1.5;
//                    rightBack /= 1.5;
//                }
            }
        }

        telemetry.addData("dis2", x);
        telemetry.update();

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
