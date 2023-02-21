// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  public final CANSparkMax ArmMotor = new CANSparkMax(14, MotorType.kBrushless);
 
  public ArmSubsystem() {
  }

  public void ManualInputs(XboxController xbox) {
    // Joystick drift protection
    if ((xbox.getLeftX() < .15) && (xbox.getLeftX() > -0.15)) {
      ArmMotor.set(0);
    }
    // Otherwise move motors normally
    else {
      ArmMotor.set(-xbox.getLeftX()/8);
    }
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
