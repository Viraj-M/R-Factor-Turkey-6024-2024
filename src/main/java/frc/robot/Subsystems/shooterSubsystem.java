package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class shooterSubsystem extends SubsystemBase {
    
    private final TalonSRX motor1 = new TalonSRX(Constants.Shooter.shooterTopID);
    private final TalonSRX motor2 = new TalonSRX(Constants.Shooter.shooterBottomID);
    final SensorCollection sensor1 = motor1.getSensorCollection();
    final CANSparkMax loaderMotor = new CANSparkMax(Constants.Loader.loaderID, MotorType.kBrushless);
    final RelativeEncoder loaderEncoder = loaderMotor.getEncoder();
    final SensorCollection sensor2 = motor2.getSensorCollection();
    final DigitalInput limitSwitch = new DigitalInput(Constants.Loader.limitSwitch);

  public shooterSubsystem() {
    motor1.setInverted(Constants.Shooter.leftInvert);
    motor2.setInverted(Constants.Shooter.rightInvert);

    motor1.setSelectedSensorPosition(sensor1.getPulseWidthPosition() & 0xFFF);
    motor2.setSelectedSensorPosition(sensor1.getPulseWidthPosition() & 0xFFF);
    
    motor1.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    motor2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);

    motor1.setNeutralMode(NeutralMode.Coast);
    motor2.setNeutralMode(NeutralMode.Coast);

    loaderMotor.setInverted(Constants.Loader.motorInvert);
    loaderMotor.setIdleMode(IdleMode.kCoast);
  }


  
  @Override
  public void periodic() {
  }

  public void setMotors(double speed1, double speed2) {
    motor1.set(ControlMode.PercentOutput, speed1);
    motor2.set(ControlMode.PercentOutput, speed2);
  }

  public void Loader(double speed){
    loaderMotor.set(speed * Constants.Shooter.loaderSpeed);
  }

  public boolean limitSwitch(){
    return !limitSwitch.get();
  }

  public double getVelocity1(){
    return sensor1.getPulseWidthVelocity();
  }

  public double getVelocity2(){
    return sensor2.getPulseWidthVelocity();
  }

}
