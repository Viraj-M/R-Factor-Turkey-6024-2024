package frc.robot.Commands.Arm;

import frc.robot.Subsystems.armSubystem;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

public class armPID extends Command {
  private final armSubystem Arm;
  private double targetPose;
  private PIDController PIDarm;

  public armPID(armSubystem subsystem, double targetPose) {
    this.Arm = subsystem;
    this.targetPose = targetPose;
    this.PIDarm = new PIDController(Constants.Arm.kp, 0, Constants.Arm.kd);

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {

    PIDarm.setSetpoint(targetPose);
  }

  @Override
  public void execute() {

    double speed = PIDarm.calculate(Arm.getAbsoluteEncoder());

    if (speed > Constants.Arm.maxSpeed) {
      speed = Constants.Arm.maxSpeed;
    } else if (speed < -Constants.Arm.maxSpeed+0.2) {
      speed = -Constants.Arm.maxSpeed+0.2;
    }
    Arm.setMotors(speed);

    if (Constants.smartEnable) {
      SmartDashboard.putNumber("Arm Position", Arm.getAbsoluteEncoder());
      SmartDashboard.putNumber("Raw Arm Position", Arm.getRawEncoder());
      SmartDashboard.putBoolean("armPID", true);
    }
  }

  @Override
  public void end(boolean interrupted) {
    Arm.setMotors(0);
    SmartDashboard.putBoolean("armPID", false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
