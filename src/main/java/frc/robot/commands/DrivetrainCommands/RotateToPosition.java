// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DrivetrainCommands;

import frc.robot.Constants;
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
public class RotateToPosition extends CommandBase {
    private DrivetrainSubsystem m_driveSubsystem;
    private boolean rotatingPositive;
    private double m_desiredRotation;
    private double trueRotation;
  
    public RotateToPosition(DrivetrainSubsystem subsystem, double desiredRotation) {
        m_driveSubsystem = subsystem;
        m_desiredRotation = desiredRotation;
        trueRotation = m_driveSubsystem.gyro.getYaw();

        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(subsystem);
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (trueRotation > 360) {
        trueRotation = trueRotation - 360;
    } else if (trueRotation < -360) {
        trueRotation = trueRotation + 360;
    } else {
      if (trueRotation <= m_desiredRotation) {
          rotatingPositive = true;
          m_driveSubsystem.drive(new Translation2d(0, 0), 0.2, false);
      } else {
          rotatingPositive = false;
          m_driveSubsystem.drive(new Translation2d(0, 0), -0.2, false);
      }
    }
  }

  public void end(boolean interrupted) {
    m_driveSubsystem.drive(new Translation2d(0, 0), 0, false);
  }

  public boolean isFinished() {
    if (rotatingPositive){
        return m_driveSubsystem.gyro.getYaw() >= m_desiredRotation;
    } else {
        return m_driveSubsystem.gyro.getYaw() <= m_desiredRotation;
    }
    
  }
}
