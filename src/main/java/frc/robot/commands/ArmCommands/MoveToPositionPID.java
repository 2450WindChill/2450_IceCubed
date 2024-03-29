package frc.robot.commands.ArmCommands;

import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveToPositionPID extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private ArmSubsystem m_armSubsystem;
  private double m_targetPosition;

  private SparkMaxPIDController m_pidController;
  public double kP, kI, kD;

  private double currentAngle;

  private boolean movingForward;
  private boolean m_isRatcheting;

  private boolean frontLimitSwitchVal;
  private boolean backLimitSwitchVal;

  public MoveToPositionPID(ArmSubsystem subsystem, double targetPosition, boolean isRatcheting) {
    m_armSubsystem = subsystem;
    m_targetPosition = targetPosition;
    m_isRatcheting = isRatcheting;

    addRequirements(subsystem);
  }

  @Override
  public void initialize() {
    m_pidController = m_armSubsystem.armMotor.getPIDController();
    kP = 0.1;
    kI = 0;
    kD = 0;
    m_pidController.setP(kP);
    m_pidController.setI(kI);
    m_pidController.setD(kD);

    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);

    SmartDashboard.putNumber("Set Rotations", 0);

    if (m_targetPosition > currentAngle) {
      movingForward = true;
    } else {
      movingForward = false;
    }

    frontLimitSwitchVal = m_armSubsystem.frontLimitSwitch.get();
    backLimitSwitchVal = m_armSubsystem.backLimitSwitch.get();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);

    // double rotations = SmartDashboard.getNumber("Set Rotations", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to
    // controller
    if ((p != kP)) {
      m_pidController.setP(p);
      kP = p;
    }
    if ((i != kI)) {
      m_pidController.setI(i);
      kI = i;
    }
    if ((d != kD)) {
      m_pidController.setD(d);
      kD = d;
    }

    System.err.println("MovetoPosition WITH PID");
    m_pidController.setReference(m_targetPosition, CANSparkMax.ControlType.kPosition);
    SmartDashboard.putNumber("Target", m_targetPosition);
    SmartDashboard.putNumber("Current Angle", m_armSubsystem.armEncoder.getPosition());
    currentAngle = m_armSubsystem.armEncoder.getPosition();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_armSubsystem.armMotor.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_armSubsystem.state == ArmSubsystem.State.Joystick_State) {
      System.err.println("Joystick state ended this thing");
      return true;
    }
    if (!m_isRatcheting) {
      System.err.println("Returning false on non ratcheting pid command");
      return false;
    }
    if (movingForward) {
      if (frontLimitSwitchVal) {
        m_armSubsystem.armMotor.set(0);
        return true;
      } else {
        return (currentAngle >= (m_targetPosition - Constants.pidTolerance));
      }
    } else {
      if (backLimitSwitchVal) {
        m_armSubsystem.armMotor.set(0);
        return true;
      } else {
        return (currentAngle <= (m_targetPosition + Constants.pidTolerance));
      }
    }
  }
}
