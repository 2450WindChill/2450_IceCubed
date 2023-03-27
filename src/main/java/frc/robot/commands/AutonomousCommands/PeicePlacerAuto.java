// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutonomousCommands;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.ArmCommands.MoveToPositionNoPID;
import frc.robot.commands.ArmCommands.NonRatchetArmSequentialCommand;
import frc.robot.commands.ArmCommands.ManipulatorAuto;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PeicePlacerAuto extends SequentialCommandGroup {
  public ArmSubsystem m_ArmSubsystem;
  public PeicePlacerAuto(RobotContainer robotContainer, ArmSubsystem subsystem) {
    m_ArmSubsystem = subsystem;
    
    addRequirements(subsystem);

    addCommands(
        new MoveToPositionNoPID(m_ArmSubsystem, Constants.midRowPlacingAngle),
        new ManipulatorAuto(m_ArmSubsystem)

      );
  }
}