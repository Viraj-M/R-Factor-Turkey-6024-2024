package frc.robot.Commands.Arm;

import frc.robot.Constants;
import frc.robot.Subsystems.hoodSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class hoodPID extends Command {
  private final hoodSubsystem hood;
  private double targetPose;
  private PIDController PIDHood;

  public hoodPID(hoodSubsystem hood, double targetPose) {
    this.hood = hood;
    this.targetPose = targetPose;
    this.PIDHood = new PIDController(Constants.Hood.kp, 0, Constants.Hood.kd);
    addRequirements(hood);
  }

  @Override
  public void initialize() {
    PIDHood.setSetpoint(targetPose);
  }

  @Override
  public void execute() {
    double speed = PIDHood.calculate(hood.getHoodPosition());

    if (speed > Constants.Hood.maxSpeed) {
      speed = Constants.Hood.maxSpeed;
    } else if (speed < -Constants.Hood.maxSpeed) {
      speed = -Constants.Hood.maxSpeed;
    }
    hood.setHood(speed);

    if (Constants.smartEnable) {
      SmartDashboard.putNumber("Hood Position", hood.getHoodPosition());
      SmartDashboard.putBoolean("hoodPID", true);

    }
  }

  @Override
  public void end(boolean interrupted) {
    hood.setHood(0);
    if (Constants.smartEnable) {
      SmartDashboard.putBoolean("hoodPID", false);
    }
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
