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
import frc.robot.commands.LEDCommands.LEDYellowCommand;
import frc.robot.commands.LimelightCommands.LightAim;
import frc.robot.commands.SolenoidCommands.LockArmCommand;
import frc.robot.commands.SolenoidCommands.UnlockArmCommand;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.LightySubsystem;
import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  private final LimelightSubsystem m_LimelightSubsystem = new LimelightSubsystem();





  // Replace with CommandPS4Controller or CommandJoystick if needed

  static XboxController m_driverController = new XboxController(0);
  static XboxController m_operatorController = new XboxController(1);

  public final JoystickButton drive_aButton = new JoystickButton(m_driverController, Button.kA.value);
  public final JoystickButton drive_bButton = new JoystickButton(m_driverController, Button.kB.value);
  public final JoystickButton drive_leftBumper = new JoystickButton(m_operatorController, Button.kLeftBumper.value);
  public final JoystickButton drive_rightBumper = new JoystickButton(m_operatorController, Button.kRightBumper.value);

  public final JoystickButton op_aButton = new JoystickButton(m_driverController, Button.kA.value);
  public final JoystickButton op_yButton = new JoystickButton(m_driverController, Button.kY.value);
  public final JoystickButton op_bButton = new JoystickButton(m_driverController, Button.kB.value);
  public final JoystickButton op_xButton = new JoystickButton(m_driverController, Button.kX.value);
  public final JoystickButton op_leftBumper = new JoystickButton(m_driverController, Button.kLeftBumper.value);
  public final JoystickButton op_rightBumper = new JoystickButton(m_driverController, Button.kRightBumper.value);
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    

    m_ArmSubsystem.setDefaultCommand(new DefaultArmCommand(m_ArmSubsystem));

    m_drivetrainSubsystem.setDefaultCommand(
        new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> m_driverController.getLeftY(),
            () -> m_driverController.getLeftX(),
            () -> m_driverController.getRightX(),
            () -> drive_rightBumper.getAsBoolean()
          ));

    // Configure the trigger bindings
    configureBindings();
    configureShuffleBoard();
    configureCamera();
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
    
    op_leftBumper.onTrue(new LEDYellowCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand(m_LightySubsystem))));
    op_rightBumper.onTrue(new LEDPurpleCommand(m_LightySubsystem).andThen(new WaitCommand(5).andThen(new LEDBlueCommand
    (m_LightySubsystem))));

    op_aButton.onTrue(new NonRatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.frontIntakeAngle));
    op_bButton.onTrue(new NonRatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.singleSubstationAngle));
    op_xButton.onTrue(new RatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.midRowPlacingAngle));
    op_yButton.onTrue(new RatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.backIntake));

    drive_aButton.onTrue(Commands.runOnce(() -> resetGyro()));
    drive_bButton.whileTrue(new LightAim(m_LimelightSubsystem, m_LightySubsystem));

  }


  
  private void configureShuffleBoard() {
    ShuffleboardTab tab = Shuffleboard.getTab("Drive");
  }

  private void configureCamera() {
    UsbCamera camera = CameraServer.startAutomaticCapture();
    camera.setResolution(320, 240);
    camera.setFPS(10);
  }

  public void resetGyro() {
    m_drivetrainSubsystem.zeroGyro();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    // return new DriveDistanceX(m_drivetrainSubsystem, 1);
    return new InstantCommand();
  }

  public static XboxController getDriveController() {
    return m_driverController;
  }

  public static XboxController getOperatorController() {
    return m_operatorController;
  }
}
