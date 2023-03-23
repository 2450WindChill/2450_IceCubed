// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.LimelightCommands;

import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LightySubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class LightAim extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final LimelightSubsystem m_limelightSubsystem;
  private final LightySubsystem m_ledSubsystem;
  private final Alliance m_teamColor;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public LightAim(LimelightSubsystem limelightSubsystem, LightySubsystem ledSubsystem, Alliance teamColor) {
    m_limelightSubsystem = limelightSubsystem;
    m_ledSubsystem = ledSubsystem;
    m_teamColor = teamColor;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(limelightSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_limelightSubsystem.turnOffLimelight();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (m_teamColor == DriverStation.Alliance.Blue) {
      if (m_limelightSubsystem.targets == 1){
          if ((-Constants.aimTolerance < m_limelightSubsystem.x) && m_limelightSubsystem.x < Constants.aimTolerance){
              m_ledSubsystem.SetLEDsToGreen();
          } else {
              m_ledSubsystem.SetLEDsToRed();
          }
      } else {
          m_ledSubsystem.SetLEDsToRed();
      }
    } else {
      if (m_limelightSubsystem.targets == 1){
        if ((-Constants.aimTolerance < m_limelightSubsystem.x) && m_limelightSubsystem.x < Constants.aimTolerance){
            m_ledSubsystem.SetLEDsToGreen();
        } else {
            m_ledSubsystem.SetLEDsToBlue();
        }
    } else {
        m_ledSubsystem.SetLEDsToBlue();
    }
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_limelightSubsystem.turnOffLimelight();

    if (m_teamColor == DriverStation.Alliance.Blue){
      m_ledSubsystem.SetLEDsToBlue();
    } else {
      m_ledSubsystem.SetLEDsToRed();
    }
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
