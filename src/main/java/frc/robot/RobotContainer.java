// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
// import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

// import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
  // final CommandXboxController WakakeController = new CommandXboxController(0);
  // final CommandXboxController AmaryanController = new %CommandXboxController(1);
  final CommandPS5Controller MarkRoberController = new CommandPS5Controller(1);

  public RobotContainer() {

    NamedCommands.registerCommand("intakeCmd", new intakeCmd(intake, shooter, 0.8, 0.4));
    NamedCommands.registerCommand("shootCmd", new shootCmd(shooter, true, 1));

    configureBindings();

    // Command driveSwerve = swerve.driveCommand(
    //     () -> -MathUtil.applyDeadband(WakakeController.getLeftY(), Constants.ControllerDeadband),
    //     () -> -MathUtil.applyDeadband(WakakeController.getLeftX(), Constants.ControllerDeadband),
    //     () -> getAsInt(WakakeController.getHID().getLeftBumper()) - getAsInt(WakakeController.getHID().getRightBumper()), false, true);

        Command driveSwerve = swerve.driveCommand(
        () -> -MathUtil.applyDeadband(WakakeController.getRawAxis(1) * 1, Constants.ControllerDeadband),
        () -> -MathUtil.applyDeadband(WakakeController.getRawAxis(0) * 1, Constants.ControllerDeadband),
        () -> getAsInt(WakakeController.getRawButton(5)) - getAsInt(WakakeController.getRawButton(6)), false, true);

    // Command climb = new climberCmd(climber, 
    // () -> -MathUtil.applyDeadband(AmaryanController.getLeftY() * 0.7, 0.1),
    // () -> -MathUtil.applyDeadband(AmaryanController.getRightY() * 0.7, Constants.ControllerDeadband));

    Command climb = new climberCmd(climber, 
    () -> -MathUtil.applyDeadband(MarkRoberController.getLeftY(), Constants.ControllerDeadband), 
    () -> -MathUtil.applyDeadband(MarkRoberController.getRightY(), Constants.ControllerDeadband));

    climber.setDefaultCommand(climb);
    swerve.setDefaultCommand(driveSwerve);

  }

  private void configureBindings() {

    new JoystickButton(WakakeController, 3).whileTrue(Commands.runOnce(swerve::zeroGyro));
    new JoystickButton(WakakeController, 8).whileTrue(new intakeCmd(intake, shooter, 0.8, 0.7));
    new JoystickButton(WakakeController, 7).whileTrue(new intakeCmd(intake, shooter, -0.8, 0));
    new JoystickButton(WakakeController, 11).whileTrue(new NoteAlign(swerve));
    new JoystickButton(WakakeController, 2).whileTrue(new SpeakerAlign(swerve));

    // WakakeController.b().whileTrue(Commands.runOnce(swerve::zeroGyro));
    // WakakeController.rightTrigger().whileTrue(new intakeCmd(intake, shooter, 0.6, 0.7));
    // WakakeController.leftTrigger().whileTrue(new intakeCmd(intake, shooter, -0.6, 0));
    // WakakeController.leftStick().whileTrue(new NoteAlign(swerve));
    // WakakeController.y().whileTrue(new SpeakerAlign(swerve));
    
    // AmaryanController.rightTrigger().whileTrue(new armPID(arm, Constants.Arm.MaxPose));
    // AmaryanController.leftTrigger().whileTrue(new armPID(arm, Constants.Arm.MinPose));
    // AmaryanController.rightBumper().onTrue(new hoodPID(hood, Constants.Hood.minPose));
    // AmaryanController.leftBumper().onTrue(new hoodPID(hood, Constants.Hood.maxPose));
    // AmaryanController.a().whileTrue(new shootCmd(shooter, true, 0.8));
    // AmaryanController.button(8).whileTrue(Commands.runOnce(hood::zeroHood));
    // AmaryanController.b().toggleOnTrue(new hoodPID(hood, Constants.Hood.maxPose / 2));

    MarkRoberController.R2().whileTrue(new armPID(arm, Constants.Arm.MaxPose));
    MarkRoberController.L2().whileTrue(new armPID(arm, Constants.Arm.MinPose));
    MarkRoberController.R1().onTrue(new hoodPID(hood, Constants.Hood.maxPose));
    MarkRoberController.L1().onTrue(new hoodPID(hood, Constants.Hood.minPose));
    MarkRoberController.cross().whileTrue(new shootCmd(shooter, false, 1      ));
    MarkRoberController.povUp().whileTrue(Commands.runOnce(hood::zeroHood));
    MarkRoberController.povDown().toggleOnTrue(new hoodPID(hood, Constants.Hood.maxPose / 2));
  }
  public Command getAutonomousCommand() {
    return swerve.getAutonomousCommand("Bottom3");
  }

  public double getAsInt(boolean bollean) {
    if (bollean) {
      return 1;
        } else {
      return 0;
    }
  }

}
