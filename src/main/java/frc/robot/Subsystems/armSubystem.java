package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DutyCycleEncoder;

import frc.robot.Constants;
public class armSubystem extends SubsystemBase {

  final CANSparkMax leftMotor = new CANSparkMax(Constants.Arm.armLeftID, MotorType.kBrushless);
  final CANSparkMax rightMotor = new CANSparkMax(Constants.Arm.armRightID, MotorType.kBrushless);
  final DutyCycleEncoder ThroughBoreEncoder = new DutyCycleEncoder(0);

  final PIDController PIDarm = new PIDController(Constants.Arm.kp, 0, Constants.Arm.kd);

  public armSubystem() {
    leftMotor.setInverted(Constants.Arm.leftInvert);
    rightMotor.setInverted(Constants.Arm.rightInvert);
    leftMotor.setIdleMode(IdleMode.kBrake);
    rightMotor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
  }

  public void setMotors(double Speed) {
    if (Speed >= 0) {
      if (getAbsoluteEncoder() <= Constants.Arm.MaxPose) {
        leftMotor.set(Speed);
        rightMotor.set(Speed);
      } else {
        leftMotor.set(0);
        rightMotor.set(0);
      }

    } else if (Speed <= 0) {
      if (Constants.Arm.MinPose <= getAbsoluteEncoder()) {
        leftMotor.set(Speed);
        rightMotor.set(Speed);
      } else {
        leftMotor.set(0);
        rightMotor.set(0);
      }
    } else {
      leftMotor.set(0);
      rightMotor.set(0);
    }
  }

  public void setRawMotors(double leftSpeed, double rightSpeed){
    leftMotor.set(leftSpeed);
    rightMotor.set(rightSpeed);
  }

  public double getAbsoluteEncoder() {
    return (ThroughBoreEncoder.getAbsolutePosition() - Constants.Arm.absoluteOffset) * Constants.Arm.absoluteInvert;
  }

  public double getRawEncoder() {
    return ThroughBoreEncoder.getAbsolutePosition();
  }

}
