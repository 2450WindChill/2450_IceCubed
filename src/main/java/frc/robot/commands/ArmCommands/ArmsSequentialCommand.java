package frc.robot.commands.ArmCommands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ArmsSequentialCommand extends SequentialCommandGroup {
  public ArmSubsystem m_ArmSubsystem;
  public ArmsSequentialCommand(RobotContainer robotContainer, ArmSubsystem subsystem) {
    m_ArmSubsystem = subsystem;
    addRequirements(m_ArmSubsystem);

    addCommands(
        // new TimedDriveFoward(m_driveTrainSub)
      );
  }
}
