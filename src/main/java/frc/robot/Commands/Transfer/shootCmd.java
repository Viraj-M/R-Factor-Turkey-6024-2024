package frc.robot.Commands.Transfer;

import frc.robot.Constants;
import frc.robot.Subsystems.shooterSubsystem;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

public class shootCmd extends Command {
  private final shooterSubsystem shooter;
  double speed = 0;
  double speed2 = 0;
  DoubleSupplier left;
  DoubleSupplier right;
  boolean autoShoot;
  boolean endLoop = false;
  double timer = 0;

  public shootCmd(shooterSubsystem shooter, boolean autoShoot, DoubleSupplier speed_Left, DoubleSupplier speed_right) {
    this.shooter = shooter;
    this.autoShoot = autoShoot;
    this.left = speed_Left;
    this.right = speed_right;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    speed = 0;
    speed2 = 0;
    endLoop = false;
    timer = 0;
  }

  @Override
  public void execute() {
    // speed = speed + (0.8 - speed) / 75;
    // speed2 = speed2 + (1 - speed2) / 75;

    // if (speed > 0.75) {
    //   speed = 0.8;
    //   timer++;
    // }

    // if (speed2 > 0.95) {
    //   speed2 = 1;
    //   timer++;
    // }


    // if (timer > 25 && autoShoot) {
    //   shooter.Loader(1);
    // }

    // if (timer > 50 && autoShoot) {
    //   endLoop = true;
    // }

    if (Constants.smartEnable) {
      SmartDashboard.putBoolean("ShootCmd", true);
      SmartDashboard.putNumber("Shooter Speed", speed);
    }

    shooter.setMotors(left.getAsDouble(), right.getAsDouble());
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