package frc.robot.Commands.Transfer;

import frc.robot.Constants;
import frc.robot.Subsystems.shooterSubsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class shootCmd extends Command {
  private final shooterSubsystem shooter;
  double speed = 0;
  boolean autoShoot;
  boolean endLoop = false;
  double timer = 0;
  double maxSpeed;

  public shootCmd(shooterSubsystem shooter, boolean autoShoot, double maxSpeed) {
    this.shooter = shooter;
    this.autoShoot = autoShoot;
    this.maxSpeed = maxSpeed;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    speed = 0;
    endLoop = false;
    timer = 0;
  }

  @Override
  public void execute() {
    speed = speed + (maxSpeed - speed) / 25;

    if (speed > maxSpeed - 0.05) {
      speed = maxSpeed;
      timer++;
    }

    if (timer > 25 && autoShoot) {
      shooter.Loader(1);
    }

    if (timer > 50 && autoShoot) {
      endLoop = true;
    }

    if (Constants.smartEnable) {
      SmartDashboard.putBoolean("ShootCmd", true);
      SmartDashboard.putNumber("Shooter Speed", speed);
    }

    shooter.setMotors(speed, speed);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setMotors(0, 0);
    shooter.Loader(0);
    if (Constants.smartEnable) {
      SmartDashboard.putBoolean("shootCmd", false);

    }
  }

  @Override
  public boolean isFinished() {
    return endLoop;
  }
}