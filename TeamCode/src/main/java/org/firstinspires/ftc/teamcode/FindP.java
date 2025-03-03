package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Find P", group = "Testing")
public class FindP extends LinearOpMode {

    private DcMotor rightBackDrive, rightFrontDrive, leftBackDrive, leftFrontDrive;
    private IMU imu;

    private double kP = 0.001; // מתחילים עם ערך קטן ונגדיל בהדרגה
    private final int targetPosition = 5000; // יעד לדוגמה (ביחידות טיקים)
    private double error;

    @Override
    public void runOpMode() {
        // אתחול מנועים
        rightBackDrive = hardwareMap.get(DcMotor.class, "BR");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "FR");
        leftBackDrive = hardwareMap.get(DcMotor.class, "BL");
        leftFrontDrive = hardwareMap.get(DcMotor.class, "FL");

        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // אתחול ג'יירו
        imu = hardwareMap.get(IMU.class, "imu");
        imu.resetYaw();

        telemetry.addData("Ready", "Press Start to begin");
        telemetry.update();

        waitForStart(); // מחכים ללחיצה על כפתור ה-Start

        while (opModeIsActive()) {
            int currentPosition = rightBackDrive.getCurrentPosition(); // ניקח חיישן אודומטריה קדמי
            error = targetPosition - currentPosition; // השגיאה היא כמה רחוק אנחנו מהיעד

            double power = kP * error; // בקרה פרופורציונלית
            power = Math.max(-1, Math.min(1, power)); // מגבילים לטווח המותר של המנועים

            // הפעלת המנועים
            rightBackDrive.setPower(power);
            rightFrontDrive.setPower(power);
            leftBackDrive.setPower(-power);
            leftFrontDrive.setPower(-power);

            // הצגת נתונים בטלמטריה
            telemetry.addData("Error", error);
            telemetry.addData("Power", power);
            telemetry.addData("kP", kP);
            telemetry.addData("Current Position", currentPosition);
            telemetry.update();

            // שליטה ידנית בשינוי kP בעזרת gamepad
            if (gamepad1.dpad_up) {
                kP += 0.0001; // מגדילים את kP
                sleep(200);
            } else if (gamepad1.dpad_down) {
                kP -= 0.0001; // מקטינים את kP
                sleep(200);
            }

            if (Math.abs(error) < 10) { // אם הרובוט הגיע ליעד, עוצרים
                rightBackDrive.setPower(0);
                rightFrontDrive.setPower(0);
                leftBackDrive.setPower(0);
                leftFrontDrive.setPower(0);
                break;
            }
        }

        telemetry.addData("Final kP", kP);
        telemetry.update();
        sleep(3000); // מחכים קצת לראות את התוצאה לפני סיום התוכנית
    }
}
