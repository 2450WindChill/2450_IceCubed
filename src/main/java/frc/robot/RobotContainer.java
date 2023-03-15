// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DrivetrainCommands.DefaultDriveCommand;
import frc.robot.commands.ArmCommands.DefaultArmCommand;
import frc.robot.commands.ArmCommands.MoveToPositionNoPID;
import frc.robot.commands.ArmCommands.MoveToPositionPID;
import frc.robot.commands.ArmCommands.NonRatchetArmSequentialCommand;
import frc.robot.commands.ArmCommands.RatchetArmSequentialCommand;
import frc.robot.commands.LEDCommands.LEDBlueCommand;
import frc.robot.commands.LEDCommands.LEDGreenCommand;
import frc.robot.commands.LEDCommands.LEDPurpleCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LightySubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
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
  // private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  // private final PneumaticsSubsystem m_PneumaticsSubsystem = new PneumaticsSubsystem();
  // private final LightySubsystem m_LightySubsystem = new LightySubsystem();
  // private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  // private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();



  // Replace with CommandPS4Controller or CommandJoystick if needed

  static XboxController m_driverController = new XboxController(0);
  static XboxController m_operatorController = new XboxController(1);
  
  public final JoystickButton m_aButton = new JoystickButton(m_operatorController, Button.kA.value);
  public final JoystickButton m_yButton = new JoystickButton(m_operatorController, Button.kY.value);
  public final JoystickButton m_bButton = new JoystickButton(m_operatorController, Button.kB.value);
  public final JoystickButton m_xButton = new JoystickButton(m_operatorController, Button.kX.value);
  public final JoystickButton m_leftBumper = new JoystickButton(m_operatorController, Button.kLeftBumper.value);
  public final JoystickButton m_rightBumper = new JoystickButton(m_operatorController, Button.kRightBumper.value);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    CameraServer.startAutomaticCapture();
    m_ArmSubsystem.setDefaultCommand(new DefaultArmCommand(m_ArmSubsystem));

    // m_drivetrainSubsystem.setDefaultCommand(
    //     new DefaultDriveCommand(
    //         m_drivetrainSubsystem,
    //         () -> m_driverController.getLeftY(),
    //         () -> m_driverController.getLeftX(),
    //         () -> m_driverController.getRightX()
    //       ));

    // Configure the trigger bindings
    configureBindings();
    configureShuffleBoard();
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

    // m_leftBumper.onTrue(new LEDGreenCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    // m_rightBumper.onTrue(new LEDPurpleCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand
    // (m_LightySubsystem))));

    m_aButton.onTrue(new NonRatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.frontIntakeAngle));
    m_bButton.onTrue(new NonRatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.singleSubstationAngle));
    m_xButton.onTrue(new RatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.midRowPlacingAngle));
    m_yButton.onTrue(new RatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.backIntake));

    m_leftBumper.onTrue(new MoveToPositionNoPID(m_ArmSubsystem, 0));
    m_rightBumper.onTrue(new MoveToPositionPID(m_ArmSubsystem, 100, false));
    // m_aButton.onTrue(new ExtendSolenoidCommand(m_PneumaticsSubsystem));
    // m_yButton.onTrue(new RetractSolenoidCommand(m_PneumaticsSubsystem));
    // m_aButton.onTrue(new DriveDistanceX(m_drivetrainSubsystem, 1));
    
    // m_yButton.onTrue(new LEDYellowCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    // m_xButton.onTrue(new LEDBlueCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    // m_aButton.onTrue(new LEDGreenCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    // m_bButton.onTrue(new LEDPurpleCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand
    // (m_LightySubsystem))));


    // m_leftBumper.onTrue(new ExtendSolenoidCommand(m_PneumaticsSubsystem));
    // m_rightBumper.onTrue(new RetractSolenoidCommand(m_PneumaticsSubsystem));

    // m_yButton.onTrue(new ActivateIntake(m_ArmSubsystem));
    // m_xButton.onTrue(new Place(m_ArmSubsystem));
  }

  
  private void configureShuffleBoard() {
    ShuffleboardTab tab = Shuffleboard.getTab("Drive");
    tab.add("Speed", 123);
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

  public static XboxController getDriveController() {
    return m_driverController;
  }

  public static XboxController getOperatorController() {
    return m_operatorController;
  }
}
