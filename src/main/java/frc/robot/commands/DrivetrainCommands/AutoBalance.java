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


public class AutoBalance extends CommandBase {
  private final DrivetrainSubsystem m_subsystem;

    public AutoBalance(DrivetrainSubsystem drivetrainSubsystem) {
      m_subsystem = drivetrainSubsystem;
    
      addRequirements(drivetrainSubsystem);
    }
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
      if (m_subsystem.gyro.getPitch() < -8) {
        m_subsystem.drive(new Translation2d(-0.5, 0), 0, false);
      } else if (m_subsystem.gyro.getPitch() > 5) {
        m_subsystem.drive(new Translation2d(0.5, 0), 0, false);
      }
      else {
        // ???? 
      }
    }
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
      return false;
  }
}