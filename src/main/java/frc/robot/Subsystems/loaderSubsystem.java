package frc.robot.Subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class loaderSubsystem extends SubsystemBase {
    
    final CANSparkMax loaderMotor = new CANSparkMax(Constants.Loader.loaderID, MotorType.kBrushless);
    final RelativeEncoder loaderEncoder = loaderMotor.getEncoder();
    final DigitalInput limitSwitch = new DigitalInput(Constants.Loader.limitSwitch);

  public loaderSubsystem() {
    loaderMotor.setInverted(Constants.Loader.motorInvert);
    loaderMotor.setIdleMode(IdleMode.kCoast);
  }


  
  @Override
  public void periodic() {
  }

  public void setMotors(double speed){
    loaderMotor.set(speed * Constants.Shooter.loaderSpeed);
  }

  public boolean limitSwitch(){
    return !limitSwitch.get();
  }
}
