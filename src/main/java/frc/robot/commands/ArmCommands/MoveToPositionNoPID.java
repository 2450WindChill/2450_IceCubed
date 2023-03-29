// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.ArmCommands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** An example command that uses an example subsystem. */
public class MoveToPositionNoPID extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private ArmSubsystem m_armSubsystem;
  private double currentAngle;
  private double m_targetPosition;

  private boolean movingForward;

  private boolean frontLimitSwitchVal;
  private boolean backLimitSwitchVal;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public MoveToPositionNoPID(ArmSubsystem armSubsystem, double targetPosition) {
    m_armSubsystem = armSubsystem;
    m_targetPosition = targetPosition;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    currentAngle = m_armSubsystem.armEncoder.getPosition();
    if (m_targetPosition > currentAngle) {
      movingForward = true;
    } else {
      movingForward = false;
    }
  }
  
  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
   
    if (movingForward == true){
      System.err.println("Moving foward");
        m_armSubsystem.armMotor.set(0.85);
    } else {
      System.err.println("Moving back");
        m_armSubsystem.armMotor.set(-0.85);
    }
    
    System.err.println("MovetoPosition NO PID");
    SmartDashboard.putNumber("Target", m_targetPosition);
    SmartDashboard.putNumber("Current Angle", m_armSubsystem.armEncoder.getPosition());
    currentAngle = m_armSubsystem.armEncoder.getPosition();

    frontLimitSwitchVal = m_armSubsystem.frontLimitSwitch.get();
    backLimitSwitchVal = m_armSubsystem.backLimitSwitch.get();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.err.println("STOPPPPPINGGGGGGGGGGGGGGGGGGGGGGGGGGG: Interrupted? " + interrupted );
    m_armSubsystem.armMotor.set(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (movingForward) {
      // if (frontLimitSwitchVal){
      //   m_armSubsystem.armMotor.set(0);
      //   return true;
      // } else {
        System.err.println(" FOWARD: Target pos:  " + m_targetPosition + " Current angle: " + currentAngle);
        return (currentAngle >= (m_targetPosition - Constants.nonPidTolerance));
      }
    // } else {
    //   if (backLimitSwitchVal){
    //     m_armSubsystem.armMotor.set(0);
    //     return true;
    //   } else {
      System.err.println("BACK: Target pos:  " + m_targetPosition + " Current angle: " + currentAngle);
        return (currentAngle <= (m_targetPosition + Constants.nonPidTolerance));
      }
    }
  // }
// }
