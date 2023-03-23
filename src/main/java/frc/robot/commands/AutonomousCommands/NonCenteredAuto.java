// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutonomousCommands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class NonCenteredAuto extends SequentialCommandGroup {
  public DrivetrainSubsystem m_driveTrainSub;
  public NonCenteredAuto(RobotContainer robotContainer, DrivetrainSubsystem subsystem) {
    m_driveTrainSub = subsystem;
    addRequirements(m_driveTrainSub);

    addCommands(
        new FieldCentricAutoDrive(m_driveTrainSub, 5.0, new Translation2d(-0.5, 0), 0)
      );
  }
}
