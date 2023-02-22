// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DrivetrainCommands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonomousCommand extends SequentialCommandGroup {
  public DrivetrainSubsystem m_driveTrainSub;
  public AutonomousCommand(RobotContainer robotContainer, DrivetrainSubsystem subsystem) {
    m_driveTrainSub = subsystem;
    addRequirements(m_driveTrainSub);

    addCommands(
        // new TimedDriveFoward(m_driveTrainSub)
      );
  }
}
