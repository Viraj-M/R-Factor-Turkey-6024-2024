// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import java.io.File;
import frc.robot.Commands.*;
import frc.robot.Commands.Arm.*;
import frc.robot.Commands.CV.*;
import frc.robot.Commands.Transfer.*;

import com.pathplanner.lib.auto.NamedCommands;

import frc.robot.Subsystems.*;

public class RobotContainer {

  private final SwerveSubsystem swerve = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(),
      "JsonConstants"));
  private final armSubystem arm = new armSubystem();
  private final shooterSubsystem shooter = new shooterSubsystem();
  private final intakeSubsystem intake = new intakeSubsystem();
  private final hoodSubsystem hood = new hoodSubsystem();
  private final climberSubsystem climber = new climberSubsystem();
  final Joystick WakakeController = new Joystick(0);
  final CommandXboxController AmaryanController = new CommandXboxController(1);

  public RobotContainer() {

    NamedCommands.registerCommand("intakeCmd", new intakeCmd(intake, shooter, 0.6, 0.4));
    NamedCommands.registerCommand("shootCmd", new shootCmd(shooter, true, 0.7));

    configureBindings();

    Command driveSwerve = swerve.driveCommand(
        () -> -MathUtil.applyDeadband(WakakeController.getRawAxis(1), Constants.ControllerDeadband),
        () -> -MathUtil.applyDeadband(WakakeController.getRawAxis(0), Constants.ControllerDeadband),
        () -> getAsInt(WakakeController.getRawButton(5)) - getAsInt(WakakeController.getRawButton(6)), false, true);

    // Command shoot = new shootCmd(shooter, false, () -> AmaryanController.getLeftY(),() -> AmaryanController.getRightY());
    Command climb = new climberCmd(climber, 
    () -> -MathUtil.applyDeadband(AmaryanController.getLeftY() * 0.7, 0.1),
    () -> -MathUtil.applyDeadband(AmaryanController.getRightY() * 0.7, Constants.ControllerDeadband));
    // shooter.setDefaultCommand(shoot);
    climber.setDefaultCommand(climb);

    swerve.setDefaultCommand(driveSwerve);

  }

  private void configureBindings() {

    new JoystickButton(WakakeController, 3).whileTrue(Commands.runOnce(swerve::zeroGyro));
    new JoystickButton(WakakeController, 8).whileTrue(new intakeCmd(intake, shooter, 0.6, 0.7));
    new JoystickButton(WakakeController, 7).whileTrue(new intakeCmd(intake, shooter, -0.6, 0));
    new JoystickButton(WakakeController, 2).whileTrue(new NoteAlign(swerve));
    new JoystickButton(WakakeController, 4).whileTrue(new SpeakerAlign(swerve));
    

    AmaryanController.rightTrigger().whileTrue(new armPID(arm, Constants.Arm.MaxPose));
    AmaryanController.leftTrigger().whileTrue(new armPID(arm, Constants.Arm.MinPose));
    AmaryanController.rightBumper().onTrue(new hoodPID(hood, Constants.Hood.minPose));
    AmaryanController.leftBumper().onTrue(new hoodPID(hood, Constants.Hood.maxPose));
    AmaryanController.a().whileTrue(new shootCmd(shooter, true, 0.8));
    AmaryanController.button(8).whileTrue(Commands.runOnce(hood::zeroHood));
    AmaryanController.b().toggleOnTrue(new hoodPID(hood, Constants.Hood.maxPose / 2));

 
  }

  public Command getAutonomousCommand() {
    return swerve.getAutonomousCommand("Top Path");
  }

  public Integer getAsInt(boolean bollean) {
    if (bollean) {
      return 1;
    } else {
      return 0;
    }
  }

}
