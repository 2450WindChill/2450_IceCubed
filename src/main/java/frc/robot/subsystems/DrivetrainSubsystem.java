// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.WindChillSwerveModule;

public class DrivetrainSubsystem extends SubsystemBase {
  private final Pigeon2 gyro;
  private WindChillSwerveModule[] swerveModules;

  /** Creates a new ExampleSubsystem. */
  public DrivetrainSubsystem() {

    gyro = new Pigeon2(Constants.pigeonID);
    gyro.configFactoryDefault();
    zeroGyro();

    swerveModules = new WindChillSwerveModule[] {
      new WindChillSwerveModule(0, Constants.FrontLeftModule.constants),
      new WindChillSwerveModule(1, Constants.FrontRightModule.constants),
      new WindChillSwerveModule(2, Constants.BackLeftModule.constants),
      new WindChillSwerveModule(3, Constants.BackRightModule.constants)
  };
  }

  public void drive(Translation2d translation, double rotation) {
    SwerveModuleState[] swerveModuleStates = Constants.swerveKinematics.toSwerveModuleStates(
        new ChassisSpeeds(translation.getX(), translation.getY(), rotation)
      );

    for (WindChillSwerveModule mod : swerveModules) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber]);
    }
  }

  public void autonomousDrive(double xSpeed, double ySpeed, double rotation) {
    SwerveModuleState[] swerveModuleStates = Constants.swerveKinematics.toSwerveModuleStates(
        new ChassisSpeeds(xSpeed, ySpeed, rotation)
      );

    for (WindChillSwerveModule mod : swerveModules) {
      mod.setDesiredState(swerveModuleStates[mod.moduleNumber]);
    }
  }

  public double getAverageEncoderVal(){
    double averageEncoderVal = 0;
    for (WindChillSwerveModule mod : swerveModules) {
      averageEncoderVal += mod.getDriveEncoder();
    }
    
    return averageEncoderVal/4;
  }

  public void zeroGyro() {
    gyro.setYaw(0);
  }


  public boolean exampleCondition() {

    return false;
  }

  @Override
  public void periodic() {
    for (WindChillSwerveModule mod : swerveModules) {
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Cancoder", mod.getCanCoder().getDegrees());
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Integrated", mod.getState().angle.getDegrees());
      SmartDashboard.putNumber(
          "Mod " + mod.moduleNumber + " Velocity", mod.getState().speedMetersPerSecond);
      
      SmartDashboard.putNumber("Average Encoder Value", getAverageEncoderVal());
    }

  }

  @Override
  public void simulationPeriodic() {

  }
}
