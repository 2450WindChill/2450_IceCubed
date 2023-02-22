// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.DrivetrainCommands;

import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

/** An example command that uses an example subsystem. */
public class DefaultDriveCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DrivetrainSubsystem m_subsystem;
  private DoubleSupplier translationSupplier;
  private DoubleSupplier strafeSupplier;
  private DoubleSupplier rotationSupplier;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public DefaultDriveCommand(
    DrivetrainSubsystem subsystem,
    DoubleSupplier translationSupplier,
    DoubleSupplier strafeSupplier,
    DoubleSupplier rotationSupplier) {

    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);

    this.translationSupplier = translationSupplier;
    this.strafeSupplier = strafeSupplier;
    this.rotationSupplier = rotationSupplier;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Slew rate would be added here using .calculate
    double translationVal = translationSupplier.getAsDouble();
    double strafeVal = strafeSupplier.getAsDouble();
    double rotationVal = rotationSupplier.getAsDouble();

    m_subsystem.drive(
      new Translation2d(translationVal, strafeVal),
      rotationVal
    );
  }
}
