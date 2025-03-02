/*
This file responsible of the Autonomous Driving System
currently there is in this class the function driveWithGyro that gets power and ticks
need to really drive stright with the Gyro sensor (consider the Gyro offset).

need to adjust the correctionFactor param to check what works better

for more data make contact with Noam the fucking king
 */


package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutonomousDriveSystem {

    private double speed = 0.1;

    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;

    private IMU imu;
    private double yawOffset = 0; // Store the offset

    private Telemetry telemetry;
//    private int startAngle;
    public boolean opModeIsActive = false;

    public AutonomousDriveSystem(HardwareMap map, Telemetry telemetry, boolean isBasket) {
//        this.startAngle = startAngle;

        // Initialize motors
        leftBackDrive = map.get(DcMotor.class, "BL");
        leftFrontDrive = map.get(DcMotor.class, "FL");
        rightBackDrive = map.get(DcMotor.class, "BR");
        rightFrontDrive = map.get(DcMotor.class, "FR");

        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.telemetry = telemetry;

        imu = map.get(IMU.class, "imu");
        resetGyro();
    }

//    public void resetGyro() {
//        imu.resetYaw();
//    }

    public void driveStraightWithGyro(double power, int targetTicks) {
        resetEncoders();
        resetGyro();

        double targetHeading = getHeading(); // Set initial heading
        double correctionFactor = 0.02; // Proportional correction factor

        while (Math.abs(rightBackDrive.getCurrentPosition()) < Math.abs(targetTicks)) {
            double currentHeading = getHeading();
            double error = targetHeading - currentHeading;
            double correction = error * correctionFactor;

            double leftPower = power + correction;
            double rightPower = power - correction;

            // Ensure power does not exceed 1
            leftPower = Math.max(-1, Math.min(1, leftPower));
            rightPower = Math.max(-1, Math.min(1, rightPower));

            leftFrontDrive.setPower(leftPower);
            leftBackDrive.setPower(leftPower);
            rightFrontDrive.setPower(rightPower);
            rightBackDrive.setPower(rightPower);

            telemetry.addData("Heading", currentHeading);
            telemetry.addData("Target Heading", targetHeading);
            telemetry.addData("Correction", correction);
            telemetry.addData("Ticks", rightBackDrive.getCurrentPosition());
            telemetry.update();
        }
        stopMotors();
    }

    public void resetGyro() {
        imu.resetYaw();
        yawOffset = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES); // Store new offset
    }

    public void printGyroValues() {
        telemetry.addData("Current Angle", getHeading());
    }

    public void driveStraightWithoutGyro(double power, int targetTicks) {
        resetEncoders();

        double targetHeading = getHeading(); // Set initial heading
        double correctionFactor = 0.05; // Proportional correction factor

        while (Math.abs(rightBackDrive.getCurrentPosition()) < Math.abs(targetTicks)) {
//            double currentHeading = getHeading();
//            double error = targetHeading - currentHeading;
//            double correction = error * correctionFactor;
//
//            double leftPower = power + correction;
//            double rightPower = power - correction;
//
//            // Ensure power does not exceed 1
//            leftPower = Math.max(-1, Math.min(1, leftPower));
//            rightPower = Math.max(-1, Math.min(1, rightPower));

            leftFrontDrive.setPower(power);
            leftBackDrive.setPower(power);
            rightFrontDrive.setPower(power);
            rightBackDrive.setPower(power);

//            telemetry.addData("Heading", currentHeading);
//            telemetry.addData("Target Heading", targetHeading);
//            telemetry.addData("Correction", correction);
//            telemetry.addData("Ticks", rightBackDrive.getCurrentPosition());
//            telemetry.update();
        }
        stopMotors();
    }

    private void resetEncoders() {
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public double getHeading() {
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) - yawOffset; // Apply offset
    }

    private void stopMotors() {
        leftFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
