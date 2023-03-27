// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

//CommandSparkMaxMotors

package frc.robot.commands.ArmCommands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class StopManipulatorAuto extends CommandBase {
  private final ArmSubsystem m_subsystem;

    public StopManipulatorAuto(ArmSubsystem armSubsystem) {
      m_subsystem = armSubsystem;
    
      addRequirements(armSubsystem);
    }
    @Override
    public void initialize() {
        double manipulatorSpeed = 0;
        m_subsystem.topManipulatorMotor.set(manipulatorSpeed);
        m_subsystem.bottomManipulatorMotor.set(-manipulatorSpeed);
        // new WaitCommand(2);
    }

    @Override
    public void execute() {
     

    }

        
    @Override
    public void end(boolean interrupted) {
        // m_subsystem.topManipulatorMotor.set(0);
        // m_subsystem.bottomManipulatorMotor.set(0);
    }

    @Override
    public boolean isFinished() {
      return true;
  }
}