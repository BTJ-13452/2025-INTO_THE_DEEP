package org.firstinspires.ftc.teamcode.RobotSystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class DriveAutonomous {

    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;
    public DistanceSensor frontDistance;
    public DistanceSensor rightDistance;
    Telemetry telemetry;


    public DriveAutonomous (HardwareMap map,Telemetry telemetry){

        this.telemetry = telemetry;
        leftBackDrive = map.get(DcMotor.class, "BL");
        leftFrontDrive = map.get(DcMotor.class, "FL");
        rightBackDrive = map.get(DcMotor.class, "BR");
        rightFrontDrive = map.get(DcMotor.class, "FR");

        rightDistance = map.get(DistanceSensor.class, "RightDistance");
        frontDistance = map.get(DistanceSensor.class, "FrontDistance");

        rightFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);


    }


    public void driveForDistance(double power, int angle, DistanceSensor sensor, double distance){

        double sin = Math.sin((angle * (Math.PI / 180)) - (Math.PI / 4));
        double cos = Math.cos((angle * (Math.PI / 180)) - (Math.PI / 4));
        double maxsincos = Math.max(Math.abs(sin), Math.abs(cos));

        double leftFront = (power * cos / maxsincos);
        double rightFront = (power * sin / maxsincos);
        double leftBack = (power * sin / maxsincos);
        double rightBack = (power * cos / maxsincos);

        leftFrontDrive.setPower(leftFront);
        rightFrontDrive.setPower(rightFront);
        leftBackDrive.setPower(leftBack);
        rightBackDrive.setPower(rightBack);

        double x = distance + 1;
        while (x > distance){
            x = sensor.getDistance(DistanceUnit.CM);
            telemetry.addData("dis",x);
            telemetry.update();
        }

        telemetry.addData("dis",x);
        telemetry.update();

        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
        rightBackDrive.setPower(0);
    }
}
