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

  public void setMotor(double speed_left, double speed_right) {
    motorLeft.set(speed_left);
    motorRight.set(speed_right);
  }

  public double getLeftEncoder(){
    return encoderLeft.getPosition();
  }

  public double getRightEncoder(){
    return encoderRight.getPosition();
  }
}
