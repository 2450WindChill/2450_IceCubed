package frc.robot.commands.ArmCommands;

import frc.robot.RobotContainer;
import frc.robot.commands.SolenoidCommands.LockArmCommand;
import frc.robot.commands.SolenoidCommands.UnlockArmCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.ArmSubsystem.State;
import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class NonRatchetArmSequentialCommand extends SequentialCommandGroup {
  public ArmSubsystem m_ArmSubsystem;
  public PneumaticsSubsystem m_PneumaticsSubsystem;
  public double m_targetPosition;

  public NonRatchetArmSequentialCommand(ArmSubsystem ArmSubsystem, PneumaticsSubsystem PneumaticsSubsystem, double targetPosition) {
    m_ArmSubsystem = ArmSubsystem;
    m_PneumaticsSubsystem = PneumaticsSubsystem;
    m_targetPosition = targetPosition;

    addRequirements(ArmSubsystem, PneumaticsSubsystem);

    if (ArmSubsystem.state == State.Joystick_State) {
      return;
    }

    else if (ArmSubsystem.state == State.Button_State) {
      addCommands(
        new UnlockArmCommand(m_PneumaticsSubsystem),
        new MoveToPositionNoPID(m_ArmSubsystem, m_targetPosition),
        new MoveToPositionPID(m_ArmSubsystem, m_targetPosition, true),
        new LockArmCommand(m_PneumaticsSubsystem)
      );
  }
    else {
      return;
    }
}
}
