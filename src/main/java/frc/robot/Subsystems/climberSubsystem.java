package frc.robot.Subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

import frc.robot.Constants;

public class climberSubsystem extends SubsystemBase {
    
    final CANSparkMax motorLeft = new CANSparkMax(Constants.Climber.climberLeft, MotorType.kBrushless);
    final CANSparkMax motorRight = new CANSparkMax(Constants.Climber.climberRight, MotorType.kBrushless);
    final RelativeEncoder encoderLeft = motorLeft.getEncoder();
    final RelativeEncoder encoderRight = motorRight.getEncoder();
    
    public climberSubsystem() {
      motorLeft.setInverted(Constants.Climber.leftInvert);      
      motorRight.setInverted(Constants.Climber.rightInvert);
      
      motorLeft.setIdleMode(IdleMode.kBrake);
      motorRight.setIdleMode(IdleMode.kBrake);
    }
  
  @Override
  public void periodic() {
  }

  public void setMotor(double speed) {
    // if (speed > 0) {
    //   if (getLeftEncoder() <= Constants.Climber.maxPose) {
    //     motorLeft.set(speed);
    //   } else {
    //     motorLeft.set(0);
    //   }

    //   if (getRightEncoder() <= Constants.Climber.maxPose) {
    //     motorRight.set(speed);
    //   } else {
    //     motorRight.set(0);
    //   }

    // } else if (speed < 0) {
    //   if (getLeftEncoder() >= Constants.Climber.minPose) {
    //     motorLeft.set(speed);
    //   } else {
    //     motorLeft.set(0);
    //   }

    //   if (getRightEncoder() >= Constants.Climber.minPose) {
    //     motorRight.set(speed);
    //   } else {
    //     motorRight.set(0);
    //   }
    // } else {
    //   motorLeft.set(0);
    //   motorRight.set(0);
    // }
    motorLeft.set(speed);
    motorRight.set(speed);
  }

  public double getLeftEncoder(){
    return encoderLeft.getPosition();
  }

  public double getRightEncoder(){
    return encoderRight.getPosition();
  }
}
