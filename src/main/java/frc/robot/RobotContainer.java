// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.DrivetrainCommands.AutoBalance;
import frc.robot.commands.DrivetrainCommands.DefaultDriveCommand;
import frc.robot.commands.DrivetrainCommands.DirectionalDrive;
import frc.robot.commands.DrivetrainCommands.RotateToFaceGrid;
import frc.robot.commands.DrivetrainCommands.RotateToFaceRedSingleSubstation;
import frc.robot.commands.DrivetrainCommands.RotateToFaceBlueSingleSubstation;
import frc.robot.commands.ArmCommands.DefaultArmCommand;
import frc.robot.commands.ArmCommands.ManipulatorAuto;
import frc.robot.commands.ArmCommands.MoveToPositionNoPID;
import frc.robot.commands.ArmCommands.MoveToPositionPID;
import frc.robot.commands.ArmCommands.NonRatchetArmSequentialCommand;
import frc.robot.commands.ArmCommands.RatchetArmSequentialCommand;
import frc.robot.commands.ArmCommands.StopManipulatorAuto;
import frc.robot.commands.AutonomousCommands.PeicePlacerAuto;
import frc.robot.commands.AutonomousCommands.RobotCentricAutoDrive;
import frc.robot.commands.AutonomousCommands.CenteredAuto;
import frc.robot.commands.AutonomousCommands.FieldCentricAutoDrive;
import frc.robot.commands.AutonomousCommands.NonCenteredAuto;
import frc.robot.commands.LEDCommands.LEDBlueCommand;
import frc.robot.commands.LEDCommands.LEDGreenCommand;
import frc.robot.commands.LEDCommands.LEDPurpleCommand;
import frc.robot.commands.LEDCommands.LEDRedCommand;
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

import javax.swing.text.StyleContext.SmallAttributeSet;

import org.ejml.dense.row.MatrixFeatures_CDRM;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.BooleanArrayTopic;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
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
  public DriverStation.Alliance teamColor;

  // The robot's subsystems and commands are defined here...
  private final PneumaticsSubsystem m_PneumaticsSubsystem = new PneumaticsSubsystem();
  private final LightySubsystem m_LightySubsystem = new LightySubsystem(teamColor);
  private final ArmSubsystem m_ArmSubsystem = new ArmSubsystem();
  private final DrivetrainSubsystem m_drivetrainSubsystem = new DrivetrainSubsystem();
  // private final LimelightSubsystem m_LimelightSubsystem = new LimelightSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed

  static XboxController m_driverController = new XboxController(0);
  static XboxController m_operatorController = new XboxController(1);

  public final JoystickButton drive_aButton = new JoystickButton(m_driverController, Button.kA.value);
  public final JoystickButton drive_bButton = new JoystickButton(m_driverController, Button.kB.value);

  public final JoystickButton drive_xButton = new JoystickButton(m_driverController, Button.kX.value);
  public final JoystickButton drive_yButton = new JoystickButton(m_driverController, Button.kY.value);

  public final JoystickButton drive_leftBumper = new JoystickButton(m_driverController, Button.kLeftBumper.value);
  public final JoystickButton drive_rightBumper = new JoystickButton(m_driverController, Button.kRightBumper.value);


  public final JoystickButton op_aButton = new JoystickButton(m_operatorController, Button.kA.value);
  public final JoystickButton op_yButton = new JoystickButton(m_operatorController, Button.kY.value);
  public final JoystickButton op_bButton = new JoystickButton(m_operatorController, Button.kB.value);
  public final JoystickButton op_xButton = new JoystickButton(m_operatorController, Button.kX.value);
  public final JoystickButton op_leftBumper = new JoystickButton(m_operatorController, Button.kLeftBumper.value);
  public final JoystickButton op_rightBumper = new JoystickButton(m_operatorController, Button.kRightBumper.value);

  public Command balance;
  public Command backOut;
  public Command onlyScore;

  public SendableChooser<Command> m_chooser;

  public UsbCamera camera = CameraServer.startAutomaticCapture();

  /**
   * The container for the robot. Contains
   * , OI devices, and commands.
   */
  public RobotContainer() {
    m_ArmSubsystem.setDefaultCommand(new DefaultArmCommand(m_ArmSubsystem));

    m_drivetrainSubsystem.setDefaultCommand(
        new DefaultDriveCommand(
            m_drivetrainSubsystem,
            () -> m_driverController.getLeftY(),
            () -> m_driverController.getLeftX(),
            () -> m_driverController.getRightX(),
            // True for robot oriented, for demo purposes (keep orientation constant)
            () -> true
          ));

    configureBindings();
    configureCamera();
    configureAutoChooser();
    configureShuffleboard();
  }

  private void configureBindings() {
    // drive_aButton.whileTrue(new DirectionalDrive(m_drivetrainSubsystem, Direction.BACK));
    // drive_bButton.whileTrue(new DirectionalDrive(m_drivetrainSubsystem, Direction.RIGHT));
    // drive_xButton.whileTrue(new DirectionalDrive(m_drivetrainSubsystem, Direction.LEFT));
    // drive_yButton.whileTrue(new DirectionalDrive(m_drivetrainSubsystem, Direction.FOWARD));

    drive_leftBumper.onTrue(new LEDYellowCommand(m_LightySubsystem)
        .andThen(new WaitCommand(5))
        .andThen(Commands.runOnce(() -> setLEDsToAlliance())));

    drive_rightBumper.onTrue(new LEDPurpleCommand(m_LightySubsystem)
        .andThen(new WaitCommand(5)
        .andThen(Commands.runOnce(() -> setLEDsToAlliance()))));

        drive_aButton.onTrue(new NonRatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.frontIntakeAngle));
    // op_bButton.onTrue(
    //     new NonRatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.singleSubstationAngle));
    // op_xButton
    //     .onTrue(new RatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.midRowPlacingAngle));
    // op_yButton.onTrue(new RatchetArmSequentialCommand(m_ArmSubsystem, m_PneumaticsSubsystem, Constants.backIntake));

    // drive_leftBumper.whileTrue(new LightAim(m_LimelightSubsystem, m_LightySubsystem, teamColor))
    // drive_aButton.onTrue(Commands.runOnce(() -> m_drivetrainSubsystem.zeroGyro()));
    // drive_bButton.onTrue(new RotateToFaceRedSingleSubstation(m_drivetrainSubsystem));
    // drive_xButton.onTrue(new RotateToFaceBlueSingleSubstation(m_drivetrainSubsystem));
    // drive_yButton.onTrue(new RotateToFaceGrid(m_drivetrainSubsystem));

  }

  private void configureCamera() {
    camera.setResolution(320, 240);
    camera.setFPS(10);
  }

  public void configureAutoChooser() {
    balance = Commands.runOnce(() -> setLEDsToAlliance())
                  // Score Cone
                  .andThen(new MoveToPositionNoPID(m_ArmSubsystem, Constants.midRowPlacingAngle))
                  .andThen(new ManipulatorAuto(m_ArmSubsystem))
                  .andThen(new WaitCommand(0.5))
                  .andThen(new StopManipulatorAuto(m_ArmSubsystem))
                  .andThen(new MoveToPositionNoPID(m_ArmSubsystem, Constants.singleSubstationAngle))

                  // Move onto charge station and balance
                  .andThen(new FieldCentricAutoDrive(m_drivetrainSubsystem, new Translation2d(-1.5, 0), 0))
                  .andThen(new WaitCommand(2.7))
                  .andThen(new FieldCentricAutoDrive(m_drivetrainSubsystem, new Translation2d(0, 0), 0))
                  .andThen(new AutoBalance(m_drivetrainSubsystem));

    backOut = Commands.runOnce(() -> setLEDsToAlliance())
                  // Score Cone
                  .andThen(new MoveToPositionNoPID(m_ArmSubsystem, Constants.midRowPlacingAngle))
                  .andThen(new ManipulatorAuto(m_ArmSubsystem))
                  .andThen(new WaitCommand(0.5))
                  .andThen(new StopManipulatorAuto(m_ArmSubsystem))
                  .andThen(new MoveToPositionNoPID(m_ArmSubsystem, Constants.singleSubstationAngle))

                  // Move out of community
                  .andThen(new FieldCentricAutoDrive(m_drivetrainSubsystem, new Translation2d(-1, 0), 0))
                  .andThen(new WaitCommand(5))
                  .andThen(new FieldCentricAutoDrive(m_drivetrainSubsystem, new Translation2d(0, 0), 0));

    onlyScore = Commands.runOnce(() -> setLEDsToAlliance())
                // Score Cone
                .andThen(new MoveToPositionNoPID(m_ArmSubsystem, Constants.midRowPlacingAngle))
                .andThen(new ManipulatorAuto(m_ArmSubsystem))
                .andThen(new WaitCommand(0.5))
                .andThen(new StopManipulatorAuto(m_ArmSubsystem))
                .andThen(new MoveToPositionNoPID(m_ArmSubsystem, Constants.singleSubstationAngle));

    m_chooser = new SendableChooser<>();

    m_chooser.setDefaultOption("Only Score", onlyScore);
    m_chooser.addOption("Balance", balance);
    m_chooser.addOption("Back Out", backOut);
  }

  private void configureShuffleboard() {
    Shuffleboard.getTab("Competition Tab")
    .add("Camera", camera)
    .withSize(5, 4)
    .withPosition(0, 0);

    Shuffleboard.getTab("Competition Tab")
    .add("Auto Chooser", m_chooser)
    .withSize(2, 1)
    .withPosition(5, 0); 

    Shuffleboard.getTab("Competition Tab")
    .add("Gyro", m_drivetrainSubsystem.gyro.getYaw())
    .withSize(2, 1)
    .withPosition(5, 1);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }

  public void setLEDsToAlliance() {
    teamColor = DriverStation.getAlliance();
    if (teamColor == DriverStation.Alliance.Red) {
      System.err.println("Alliance RED");
      m_LightySubsystem.SetLEDsToRed();
    } else {
      System.err.println("Alliance BLUE");
      m_LightySubsystem.SetLEDsToBlue();
    }
  }

  public static XboxController getDriveController() {
    return m_driverController;
  }

  public static XboxController getOperatorController() {
    return m_operatorController;
  }

}
