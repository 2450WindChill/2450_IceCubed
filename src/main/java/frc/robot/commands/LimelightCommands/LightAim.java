// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LimelightCommands;

import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LightySubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class LightAim extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LimelightSubsystem m_limelightSubsystem;
  private final LightySubsystem m_ledSubsystem;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LightAim(LimelightSubsystem limelightSubsystem, LightySubsystem ledSubsystem) {
    m_limelightSubsystem = limelightSubsystem;
    m_ledSubsystem = ledSubsystem;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelightSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelightSubsystem.turnOnLimelight();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_limelightSubsystem.targets == 1){
        if ((-Constants.aimTolerance < m_limelightSubsystem.x) && m_limelightSubsystem.x < Constants.aimTolerance){
            m_ledSubsystem.SetLEDsToGreen();
        } else {
            m_ledSubsystem.SetLEDsToRed();
        }
    } else {
        m_ledSubsystem.SetLEDsToRed();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_limelightSubsystem.turnOffLimelight();
    m_ledSubsystem.SetLEDsToBlue();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
