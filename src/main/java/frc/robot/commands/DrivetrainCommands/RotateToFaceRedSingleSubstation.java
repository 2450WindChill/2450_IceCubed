// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DrivetrainCommands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/** An example command that uses an example subsystem. */
public class RotateToFaceRedSingleSubstation extends CommandBase {
    private DrivetrainSubsystem m_driveSubsystem;
    private double trueRotation;
  
    public RotateToFaceRedSingleSubstation(DrivetrainSubsystem subsystem) {
        m_driveSubsystem = subsystem;

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    trueRotation = m_driveSubsystem.gyro.getYaw();

    while (trueRotation > 360) {
      trueRotation -= 360;
    }
    while (trueRotation < 0) {
      trueRotation += 360;
    }

    if (trueRotation <= 270 && trueRotation >= 90) {
        m_driveSubsystem.drive(new Translation2d(0, 0), 2, true);
    } else {
        m_driveSubsystem.drive(new Translation2d(0, 0), -2, true);
    }
  }
  
  // hello oosty i am in your computer oooo im a spooky ghost ooooooooooo give jake twenty dollars oooooo
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

  }

  public void end(boolean interrupted) {
    m_driveSubsystem.drive(new Translation2d(0, 0), 0, true);
  }

  public boolean isFinished() {
    return m_driveSubsystem.gyro.getYaw() >= 269 && m_driveSubsystem.gyro.getYaw() <= 271;
    // return m_driveSubsystem.getYaw() == 270;
  }
}
