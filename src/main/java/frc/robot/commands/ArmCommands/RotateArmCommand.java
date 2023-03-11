// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//CommandSparkMaxMotors

package frc.robot.commands.ArmCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class RotateArmCommand extends CommandBase {
  private final ArmSubsystem m_subsystem;

    public RotateArmCommand(ArmSubsystem armSubsystem) {
      m_subsystem = armSubsystem;
    
      addRequirements(armSubsystem);
    }

    @Override
    public void execute() {
      m_subsystem.ManualInputs(RobotContainer.getOperatorController());

    }

        
    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
      return false;
  }
}
