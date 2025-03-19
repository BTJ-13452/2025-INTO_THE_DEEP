package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DriveSystem {

    double speed = 0.4;

    private final DcMotor leftFrontDrive;
    private final DcMotor leftBackDrive;
    private final DcMotor rightFrontDrive;
    private final DcMotor rightBackDrive;

    static IMU imu;
    Orientation angles = new Orientation();
    public double initYaw;
    Gamepad gamepad;
    Telemetry telemetry;
    static double startAngel;

    public static boolean isAutoFieldOriented = false;

    public DriveSystem(HardwareMap map, Gamepad gamepad, Telemetry telemetry) {
        this.gamepad = gamepad;
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

        if(!isAutoFieldOriented){
            startAngel = 0;
            imu = map.get(IMU.class, "imu");
            imu.resetYaw();
        }
    }

    public DriveSystem(HardwareMap map, Telemetry telemetry, boolean isBasket) {
        leftBackDrive = map.get(DcMotor.class, "BL");
        leftFrontDrive = map.get(DcMotor.class, "FL");
        rightBackDrive = map.get(DcMotorEx.class, "BR");
        rightFrontDrive = map.get(DcMotor.class, "FR");

        leftBackDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        if (isBasket) {
            speed = 0.5;
        }
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.telemetry = telemetry;

        imu = map.get(IMU.class, "imu");
        imu.resetYaw();
    }

    public void resetFieldOriented() {
        startAngel = 0;
        imu.resetYaw();
    }


    public void Drive() {
        angles = imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double zeroDYaw = angles.firstAngle - startAngel;

        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;

        double theta = Math.atan2(y, x) * 180 / Math.PI;
        double realtheta;
        realtheta = (360 - zeroDYaw) + theta;
        double power = Math.hypot(x, y);

        double sin = Math.sin((realtheta * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((realtheta * (Math.PI / 180)) - (Math.PI / 4));
        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = (power * cos / maxsincos + turn);
        double rightFront = (power * sin / maxsincos - turn);
        double leftBack = (power * sin / maxsincos + turn);
        double rightBack = (power * cos / maxsincos - turn);
        if ((power + Math.abs(turn)) > 1) {
            leftFront /= power + turn;
            rightFront /= power - turn;
            leftBack /= power + turn;
            rightBack /= power - turn;
        }
        leftFrontDrive.setPower(leftFront);
        rightFrontDrive.setPower(rightFront);
        leftBackDrive.setPower(leftBack);
        rightBackDrive.setPower(rightBack);
    }

    public void DriveWithoutFildo() {
        double zeroDYaw = initYaw;

        double x = gamepad.left_stick_x;
        double y = -gamepad.left_stick_y;
        double turn = gamepad.right_stick_x;

        double theta = Math.atan2(y, x) * 180 / Math.PI;
        double realtheta;
        realtheta = (360 - zeroDYaw) + theta;
        double power = Math.hypot(x, y);

        double sin = Math.sin((realtheta * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((realtheta * (Math.PI / 180)) - (Math.PI / 4));
        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = (power * cos / maxsincos + turn);
        double rightFront = (power * sin / maxsincos - turn);
        double leftBack = (power * sin / maxsincos + turn);
        double rightBack = (power * cos / maxsincos - turn);
        if ((power + Math.abs(turn)) > 1) {
            leftFront /= power + turn;
            rightFront /= power - turn;
            leftBack /= power + turn;
            rightBack /= power - turn;
        }
        leftFrontDrive.setPower(leftFront);
        rightFrontDrive.setPower(rightFront);
        leftBackDrive.setPower(leftBack);
        rightBackDrive.setPower(rightBack);
    }

    public void forward(int tick) {
        rightFrontDrive.setTargetPosition(tick);
        rightBackDrive.setTargetPosition(tick);
        leftFrontDrive.setTargetPosition(tick);
        leftBackDrive.setTargetPosition(tick);

        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void printData() {
        telemetry.addData("lb", leftBackDrive.getCurrentPosition());
        telemetry.addData("lf", leftFrontDrive.getCurrentPosition());
        telemetry.addData("rb", rightBackDrive.getCurrentPosition());
        telemetry.addData("rf", rightFrontDrive.getCurrentPosition());
    }

    public static void setStartAngel(double angel,HardwareMap map){
        isAutoFieldOriented = true;
        imu = map.get(IMU.class, "imu");
        imu.resetYaw();
        DriveSystem.startAngel = angel;
    }


    public double getOdometryStraightPosition() {
        return rightBackDrive.getCurrentPosition();
    }


    public double getOdometrySidePosition() {
        return leftFrontDrive.getCurrentPosition();
    }


    public String getAngel() {
        return imu.getRobotAngularVelocity(AngleUnit.DEGREES).toString();
    }

    public void driveByEncoder(double power, double angleDegrees, int CM, int angleAheadDegrees) {
        // 9.6 CM Koter
        // 9.6 * PI = Hekef
        // Hekef = 30.16
        // encoder for one round = 537.6
        // (30.16 / 537.6) * encoder = CM
        // 0.0561 * encoder = CM
        // encoder = CM / 0.0561
        double encoder = CM / 0.0561; // לשנות למספר רלוונטי למנועים שלנו
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double y = (power * Math.sin(Math.toRadians(-angleDegrees) + Math.PI / 2));
        double x = -(power * Math.cos(Math.toRadians(-angleDegrees) + Math.PI / 2));
        double rx = -(angleAheadDegrees - getAngle()) * 0.01;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        double frontRightEncoder = encoder;
        double backRightEncoder = encoder;

        while (Math.abs(rightFrontDrive.getCurrentPosition()) < frontRightEncoder || Math.abs(rightBackDrive.getCurrentPosition()) < backRightEncoder) {

            rx = -(angleAheadDegrees - getAngle()) * 0.01;
            denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);

            frontLeftPower = (y + x + rx) / denominator;
            backLeftPower = (y - x + rx) / denominator;
            frontRightPower = (y - x - rx) / denominator;
            backRightPower = (y + x - rx) / denominator;

            leftFrontDrive.setPower(frontLeftPower);
            leftBackDrive.setPower(backLeftPower);
            rightFrontDrive.setPower(frontRightPower);
            rightBackDrive.setPower(backRightPower);
        }
        leftBackDrive.setPower(0);
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        rightBackDrive.setPower(0);
    }

    public double getAngle() {
        return imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

}