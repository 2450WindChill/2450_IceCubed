// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticsSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  // Compressor pcmCompressor = new Compressor(0, PneumaticsModuleType.CTREPCM);
  // public DoubleSolenoid pcmSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);

  public PneumaticsSubsystem() {
    // pcmCompressor.enableDigital();
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
