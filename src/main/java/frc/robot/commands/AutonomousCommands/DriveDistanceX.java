// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutonomousCommands;

import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** An example command that uses an example subsystem. */
public class DriveDistanceX extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DrivetrainSubsystem m_driveSubsystem;
  private double currentLocationFeet = 0;
  private double targetLocationFeet = 0;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DriveDistanceX(DrivetrainSubsystem drivetrainSubsystem, double desiredDistanceFeet) {
    m_driveSubsystem = drivetrainSubsystem;
    currentLocationFeet = m_driveSubsystem.getFrontLeftEncoderVal() / Constants.rotationsPerOneFoot;
    targetLocationFeet = desiredDistanceFeet + currentLocationFeet;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(drivetrainSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveSubsystem.autonomousDrive(-0.3, 0, 0);
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double currentLocationRotations = m_driveSubsystem.getFrontLeftEncoderVal();
    currentLocationFeet = currentLocationRotations / Constants.rotationsPerOneFoot;

    SmartDashboard.putNumber("Target Location", targetLocationFeet);
    SmartDashboard.putNumber("Current Location", currentLocationFeet);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveSubsystem.autonomousDrive(0, 0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (currentLocationFeet >= targetLocationFeet){
      return true;
    } else {
      return false;
    }
  }
}
