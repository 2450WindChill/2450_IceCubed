// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DrivetrainCommands.AutonomousCommand;
import frc.robot.commands.DrivetrainCommands.DefaultDriveCommand;
import frc.robot.commands.ArmCommands.ActivateIntake;
import frc.robot.commands.ArmCommands.Place;
import frc.robot.commands.ArmCommands.RotateArmCommand;
import frc.robot.commands.LEDCommands.LEDBlueCommand;
import frc.robot.commands.LEDCommands.LEDGreenCommand;
import frc.robot.commands.LEDCommands.LEDPurpleCommand;
import frc.robot.commands.LEDCommands.LEDYellowCommand;
import frc.robot.commands.SolenoidCommands.LockArmCommand;
import frc.robot.commands.SolenoidCommands.UnlockArmCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LightySubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;

import org.ejml.dense.row.MatrixFeatures_CDRM;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final PneumaticsSubsystem m_PneumaticsSubsystem = new PneumaticsSubsystem();
  private final LightySubsystem m_LightySubsystem = new LightySubsystem();
  private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();



  // Replace with CommandPS4Controller or CommandJoystick if needed

  static XboxController m_driverController = new XboxController(0);
  public final JoystickButton m_aButton = new JoystickButton(m_driverController, Button.kA.value);
  public final JoystickButton m_yButton = new JoystickButton(m_driverController, Button.kY.value);
  public final JoystickButton m_bButton = new JoystickButton(m_driverController, Button.kB.value);
  public final JoystickButton m_xButton = new JoystickButton(m_driverController, Button.kX.value);
  public final JoystickButton m_leftBumper = new JoystickButton(m_driverController, Button.kLeftBumper.value);
  public final JoystickButton m_rightBumper = new JoystickButton(m_driverController, Button.kRightBumper.value);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    m_ArmSubsystem.setDefaultCommand(new RotateArmCommand(m_ArmSubsystem));

    m_drivetrainSubsystem.setDefaultCommand(
        new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> -m_driverController.getLeftY(),
            () -> -m_driverController.getLeftX(),
            () -> -m_driverController.getRightX()
          ));
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // m_aButton.onTrue(new ExtendSolenoidCommand(m_PneumaticsSubsystem));
    // m_yButton.onTrue(new RetractSolenoidCommand(m_PneumaticsSubsystem));
    
    // m_yButton.onTrue(new LEDYellowCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    // m_xButton.onTrue(new LEDBlueCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    m_aButton.onTrue(new LEDGreenCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    m_bButton.onTrue(new LEDPurpleCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand
    (m_LightySubsystem))));

    m_leftBumper.onTrue(new LockArmCommand(m_PneumaticsSubsystem));
    m_rightBumper.onTrue(new UnlockArmCommand(m_PneumaticsSubsystem));

    m_yButton.whileTrue(new ActivateIntake(m_ArmSubsystem));
    m_xButton.whileTrue(new Place(m_ArmSubsystem));
  }

  public void resetGyro() {
    // m_drivetrainSubsystem.zeroGyroscope();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return new InstantCommand();
  }

  public static XboxController getClimbController() {
    return m_driverController;
  }

  
}
