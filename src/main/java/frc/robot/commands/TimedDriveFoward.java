package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LightySubsystem;
import frc.robot.Constants;

public class TimedDriveFoward extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final DrivetrainSubsystem m_DrivetrainSubsystem;
  
    /**
     * Creates a new ExampleCommand.
     *
     * @param subsystem The subsystem used by this command.
     */
    public TimedDriveFoward(DrivetrainSubsystem subsystem) {
  
        m_DrivetrainSubsystem = subsystem;
      // Use addRequirements() here to declare subsystem dependencies.
      addRequirements(subsystem);
    }
  
    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      m_DrivetrainSubsystem.drive(m_DrivetrainSubsystem.m_chassisSpeeds);

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
       // TODO: Figure out how to access motors
      m_DrivetrainSubsystem.drive(m_DrivetrainSubsystem.m_chassisSpeedsZeroSpeeds);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return true;
    }
  }
  