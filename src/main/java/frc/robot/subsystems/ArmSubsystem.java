// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.SolenoidCommands.UnlockArmCommand;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public final CANSparkMax armMotor = new CANSparkMax(14, MotorType.kBrushless);
  public final RelativeEncoder armEncoder = armMotor.getEncoder();

  public final CANSparkMax topManipulatorMotor = new CANSparkMax(15, MotorType.kBrushed);
  public final CANSparkMax bottomManipulatorMotor = new CANSparkMax(16, MotorType.kBrushed);

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
    // manipulatorSpeed = SmartDashboard.getNumber("Manipulator Speed", 0.1);
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
    if (state == State.Joystick_State) {
      // Joystick drift protection
      if ((xbox.getLeftX() < .15) && (xbox.getLeftX() > -0.15)) {
        armMotor.set(0);
      }
      // Otherwise move motors normally
      else {
        armMotor.set(-xbox.getLeftX() / 8);
      }

    }

    else if (state == State.Button_State) {
      // Don't care about xbox inputs, just pay attention to buttons
      return;
    }

  }

  @Override
  public void periodic() {
    UpdateState(RobotContainer.getOperatorController());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
