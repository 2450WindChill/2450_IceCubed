// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightySubsystem extends SubsystemBase {
 
  AddressableLED m_led;
  AddressableLEDBuffer m_ledBuffer;

  // AddressableLED m_led2;
  // AddressableLEDBuffer m_ledBuffer2;
  
  
  /** Creates a new LightySubsystem. */
  public LightySubsystem() { 
   
    // PWM port 9
    // Must be a PWM header, not MXP or DIO
     m_led = new AddressableLED(9);
     // m_led2 = new AddressableLED(9);

    // Reuse buffer
    // Default to a length of 60, start empty output
    // Length is expensive to set, so only set it once, then just update data
    m_ledBuffer = new AddressableLEDBuffer(75);
    m_led.setLength(m_ledBuffer.getLength());

    // m_ledBuffer2 = new AddressableLEDBuffer(75);
    // m_led2.setLength(m_ledBuffer2.getLength());

    // Set the data
    m_led.setData(m_ledBuffer);
    m_led.start();

    // m_led2.setData(m_ledBuffer2);
    // m_led2.start();

    SetLEDsToBlue();
  
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public CommandBase exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }


  public void SetLEDsToBlue(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 0, 50, 155);
   }
   
   m_led.setData(m_ledBuffer);
  }
  public void SetLEDsToYellow(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 155, 75, 0);
   }
   
   m_led.setData(m_ledBuffer);
  }
  public void SetLEDsToPurple(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 155,0 , 155);
   }
   
   m_led.setData(m_ledBuffer);
  }
  public void SetLEDsToGreen(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 0,155 , 0);
   }
   
   m_led.setData(m_ledBuffer);
  }

  public void SetLEDsToRed(){
    for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      m_ledBuffer.setRGB(i, 155,0 , 0);
   }
   
   m_led.setData(m_ledBuffer);
  }

}
