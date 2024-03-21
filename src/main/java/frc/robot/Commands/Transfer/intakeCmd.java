package frc.robot.Commands.Transfer;

import frc.robot.Constants;
import frc.robot.Subsystems.intakeSubsystem;
import frc.robot.Subsystems.shooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class intakeCmd extends Command {
  private final intakeSubsystem Intake;
  private final shooterSubsystem shooter;
  private final double intakeSpeed;
  private final double loaderSpeed;
  private boolean everTrue = false;
  private boolean endLoop = false;

  public intakeCmd(intakeSubsystem Intake, shooterSubsystem shooter, double intakeSpeed, double loaderSpeed) {
    this.Intake = Intake;
    this.shooter = shooter;
    this.intakeSpeed = intakeSpeed;
    this.loaderSpeed = loaderSpeed;
    addRequirements(Intake);
  }

  @Override
  public void initialize() {
    endLoop = false;
    everTrue = false;
    SmartDashboard.putBoolean("Limitz Switch", endLoop);

  }
        
  @Override
  public void execute() {

    Intake.setMotors(intakeSpeed, intakeSpeed);
    shooter.Loader(loaderSpeed);

    if (shooter.limitSwitch()) {
      everTrue = true;
    } else if (everTrue && !shooter.limitSwitch()) {
      endLoop = true;
    }

    SmartDashboard.putBoolean("Limit Switch", endLoop);
    if (Constants.smartEnable) {
      SmartDashboard.putBoolean("Intake", true);
    }
  }

  @Override
  public void end(boolean interrupted) {
    Intake.setMotors(0, 0);
    shooter.Loader(0);
    SmartDashboard.putBoolean("Limit Switch", endLoop);
    if (Constants.smartEnable) {
      SmartDashboard.putBoolean("Intake", false);
    }
  }

  @Override
  public boolean isFinished() {
    return endLoop;
  }
}