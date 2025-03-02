//package org.firstinspires.ftc.teamcode.OpModes;
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.IMU;
//import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
//import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
//
//@Autonomous(name="Mecanum IMU Auto Drive", group="Autonomous")
//public class AutonomousWithGyro extends OpMode {
//    private DcMotor leftFrontDrive, leftBackDrive, rightFrontDrive, rightBackDrive;
//    private IMU imu;
//
//    private enum State { DRIVE_FORWARD, STOP }
//    private State currentState;
//
//    private double targetHeading;
//    private boolean firstRun = true;
//
//    @Override
//    public void init() {
//        // Initialize motors
//        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
//        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
//        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
//        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");
//
//        // Reverse right motors for correct movement
//        rightFrontDrive.setDirection(DcMotor.Direction.REVERSE);
//        rightBackDrive.setDirection(DcMotor.Direction.REVERSE);
//
//        // Set zero power behavior
//        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//
//        // Initialize IMU
//        imu = hardwareMap.get(IMU.class, "imu");
//        IMU.Parameters imuParameters = new IMU.Parameters();
//        imu.initialize(imuParameters);
//
//        currentState = State.DRIVE_FORWARD;  // Start in drive state
//    }
//
//    @Override
//    public void loop() {
//        switch (currentState) {
//            case DRIVE_FORWARD:
//                if (firstRun) {
//                    targetHeading = getHeading();  // Store initial heading
//                    firstRun = false;
//                }
//
//                boolean reachedTarget = driveStraight(0.5, 100);  // Drive forward with IMU correction
//
//                if (reachedTarget) {
//                    currentState = State.STOP;
//                }
//                break;
//
//            case STOP:
//                stopMotors();
//                break;
//        }
//
//        telemetry.addData("Heading", getHeading());
//        telemetry.addData("Target Heading", targetHeading);
//        telemetry.addData("State", currentState);
//        telemetry.update();
//    }
//
//    private boolean driveStraight(double power, double targetDistance) {
//        double currentHeading = getHeading();
//        double error = targetHeading - currentHeading;
//
//        // Proportional correction factor
//        double correction = error * 0.05;
//
//        // Apply correction while moving forward
//        double leftPower = power + correction;
//        double rightPower = power - correction;
//
//        // Set motor power
//        leftFrontDrive.setPower(leftPower);
//        leftBackDrive.setPower(leftPower);
//        rightFrontDrive.setPower(rightPower);
//        rightBackDrive.setPower(rightPower);
//
//        // TODO: Replace this with actual odometry-based distance check
//        return false;  // Replace with a condition to stop when reaching the target distance
//    }
//
//    private double getHeading() {
//        YawPitchRollAngles angles = imu.getRobotYawPitchRollAngles();
//        return angles.getYaw(AngleUnit.DEGREES);
//    }
//
//    private void stopMotors() {
//        leftFrontDrive.setPower(0);
//        leftBackDrive.setPower(0);
//        rightFrontDrive.setPower(0);
//        rightBackDrive.setPower(0);
//    }
//}
