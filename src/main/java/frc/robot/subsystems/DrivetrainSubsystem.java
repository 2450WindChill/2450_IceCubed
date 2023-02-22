// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.WindChillSwerveModule;

public class DrivetrainSubsystem extends SubsystemBase {
  private WindChillSwerveModule[] swerveModules;
  /** Creates a new ExampleSubsystem. */
  public DrivetrainSubsystem() {
    swerveModules = new WindChillSwerveModule[] {
      new WindChillSwerveModule(0, Constants.Mod0.constants),
      new WindChillSwerveModule(1, Constants.Mod1.constants),
      new WindChillSwerveModule(2, Constants.Mod2.constants),
      new WindChillSwerveModule(3, Constants.Mod3.constants)
    };
  }

  public void drive(Translation2d translation, double rotation){
    SwerveModuleState[] swerveModuleStates =
        Constants.swerveKinematics.toSwerveModuleStates(
          new ChassisSpeeds(translation.getX(), translation.getY(), rotation));

          for (WindChillSwerveModule mod : swerveModules) {
            mod.setDesiredState(swerveModuleStates[mod.moduleNumber]);
          }
  }

  public boolean exampleCondition() {

    return false;
  }

  @Override
  public void periodic() {

  }

  @Override
  public void simulationPeriodic() {

  }
}
