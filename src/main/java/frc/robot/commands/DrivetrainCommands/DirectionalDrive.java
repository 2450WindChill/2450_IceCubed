// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//CommandSparkMaxMotors

package frc.robot.commands.DrivetrainCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Direction;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;


public class DirectionalDrive extends CommandBase {
  private final DrivetrainSubsystem m_subsystem;
  private final Direction m_Direction;

    public DirectionalDrive(DrivetrainSubsystem drivetrainSubsystem, Direction direction) {
      m_subsystem = drivetrainSubsystem;
      m_Direction = direction;
    
      addRequirements(drivetrainSubsystem);
    }
    @Override
    public void initialize() {
      if (m_Direction == Direction.FOWARD) {
        m_subsystem.drive(new Translation2d(-0.45, 0), 0, true);
      }

      else if (m_Direction == Direction.BACK) {
        m_subsystem.drive(new Translation2d(0.45, 0), 0, true);
      }

      else if (m_Direction == Direction.LEFT) {
        m_subsystem.drive(new Translation2d(0, -0.45), 0, true);
      }
      else if (m_Direction == Direction.RIGHT) {
        m_subsystem.drive(new Translation2d(0, 0.45), 0, true);
      }

    }

    @Override
    public void execute() {
     

    }
    @Override
    public void end(boolean interrupted) {
      m_subsystem.drive(new Translation2d(0, 0), 0, true);
    }

    @Override
    public boolean isFinished() {
      return false;
  }
}