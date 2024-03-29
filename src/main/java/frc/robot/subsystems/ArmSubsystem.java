// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.AlternateEncoderType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxAbsoluteEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxAbsoluteEncoder.Type;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.SolenoidCommands.UnlockArmCommand;

public class ArmSubsystem extends SubsystemBase {
  // public final AbsoluteEncoder AbsoluteEncoder;
  /** Creates a new ExampleSubsystem. */
  public final CANSparkMax armMotor = new CANSparkMax(17, MotorType.kBrushless);
 // public final SparkMaxAbsoluteEncoder AbsoluteEncoder;

  // public final AbsoluteEncoder armEncoder = armMotor.get(8192)

  // public final CANEncoder armEncoder =
  // armMotor.getAlternateEncoder(AlternateEncoderType.kQuadrature, 8192);
  // public final CANEncoder armEncoder = armMotor.getAlternateEncoder(8192);

  // private final Encoder encoder = new Encoder(0, 1, true,
  // CounterBase.EncodingType.k4X);

  public final RelativeEncoder armEncoder = armMotor.getEncoder();

  public final CANSparkMax topManipulatorMotor = new CANSparkMax(18, MotorType.kBrushless);
  public final CANSparkMax bottomManipulatorMotor = new CANSparkMax(19, MotorType.kBrushless);

  public final DigitalInput frontLimitSwitch = new DigitalInput(8);
  public final DigitalInput backLimitSwitch = new DigitalInput(9);

  // States
  public boolean doWeNeedToStopRumble = false;
  public State state = State.Button_State;

  // Enum that defines the differnt possible states
  public enum State {
    Button_State,
    Joystick_State,
  }

  // public double manipulatorSpeed;

  public ArmSubsystem() {
   // AbsoluteEncoder = armMotor.getAbsoluteEncoder(Type.kDutyCycle);
   
    // AbsoluteEncoder = armMotor.getAlternateEncoder(8192);
    // manipulatorSpeed = SmartDashboard.getNumber("Manipulator Speed", 0.1);
    armMotor.setIdleMode(IdleMode.kBrake);

    // armEncoder.
  }

  public void UpdateState(XboxController xbox) {
    if (doWeNeedToStopRumble == true) {
      doWeNeedToStopRumble = false;
      xbox.setRumble(RumbleType.kLeftRumble, 0);
    }
    if ((xbox.getPOV() == Constants.POV_left_dpad)) {
      state = State.Button_State;
      SmartDashboard.putString("Current State", "Button");
      SmartDashboard.putBoolean("State", true);
      // Vibrate the controller when you switch into this state
      xbox.setRumble(RumbleType.kLeftRumble, 1);

      // Turn rumble off
      doWeNeedToStopRumble = true;

    } else if ((xbox.getPOV() == Constants.POV_right_dpad)) {
      state = State.Joystick_State;
      SmartDashboard.putString("Current State", "Joystick");
      SmartDashboard.putBoolean("State", false);
      // Vibrate the controller when you switch into this state
      xbox.setRumble(RumbleType.kLeftRumble, 1);
      doWeNeedToStopRumble = true;
    }
  }

  public void ManualInputs(XboxController xbox) {
    double manipulatorSpeed = 0;
    boolean goingForward = xbox.getLeftX() > 0;
    

    manipulatorSpeed = ((xbox.getRightTriggerAxis() - xbox.getLeftTriggerAxis())) / 2;
    topManipulatorMotor.set(manipulatorSpeed);
    bottomManipulatorMotor.set(manipulatorSpeed);

    // System.err.println("Setting manipulator speed to: " + manipulatorSpeed);

    // if (state == State.Joystick_State) {
    // Joystick drift protection
    if ((xbox.getLeftX() < .15) && (xbox.getLeftX() > -0.15)) {
      armMotor.set(0);
    }
    // Otherwise move motors normally
    else {
      // if ((frontLimitSwitch.get() && goingForward) || (backLimitSwitch.get() &&
      // !goingForward)) {
      // System.err.println("Limit switch hit");
      // armMotor.set(0);
      // } else {
      armMotor.set(xbox.getLeftX() / 1.3);

    }

  }

  // else if (state == State.Button_State) {
  // Don't care about xbox inputs, just pay attention to buttons
  // return;
  // }

  // }

  @Override
  public void periodic() {
    UpdateState(RobotContainer.getOperatorController());
    // This method will be called once per scheduler run
    // manipulatorSpeed = SmartDashboard.getNumber("Manipulator Speed", 0.1);

    // // SmartDashboard.putBoolean("frontLimitSwitch", frontLimitSwitch.get());
    // // SmartDashboard.putBoolean("backLimitSwitch", backLimitSwitch.get());
    // SmartDashboard.getBoolean("Front Limit Switch", frontLimitSwitch.get());
    // SmartDashboard.getBoolean("Back Limit Switch", backLimitSwitch.get());

    SmartDashboard.putNumber("Encoder: ", armEncoder.getPosition());
   //System.err.println("Arm ENCODER: " + armEncoder.getPosition());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
