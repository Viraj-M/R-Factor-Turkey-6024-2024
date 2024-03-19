package frc.robot.Commands;

import frc.robot.Constants;
import frc.robot.Subsystems.climberSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class climberCmd extends Command {
  private final climberSubsystem climber;
  private final double speed;

  public climberCmd(climberSubsystem climber, double speed) {
    this.climber = climber;
    this.speed = speed;

    addRequirements(climber);
  }


  @Override
  public void initialize() {}


  @Override
  public void execute() {
    climber.setMotor(speed);
    if(Constants.smartEnable){
      SmartDashboard.putBoolean("climberCmd", true);

    }
  }


  @Override
  public void end(boolean interrupted) {
    climber.setMotor(0);
    if(Constants.smartEnable){
      SmartDashboard.putBoolean("climberCmd", false);

    }
  }


  @Override
  public boolean isFinished() {
    return false;
  }
}
