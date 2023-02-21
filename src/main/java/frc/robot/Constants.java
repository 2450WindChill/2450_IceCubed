// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  /**
   * The left-to-right distance between the drivetrain wheels
   *
   * Should be measured from center to center.
   */
  public static final double DRIVETRAIN_TRACKWIDTH_METERS = 0.55;
  /**
   * The front-to-back distance between the drivetrain wheels.
   *
   * Should be measured from center to center.
   */
  public static final double DRIVETRAIN_WHEELBASE_METERS = 0.54;

  public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
      new Translation2d(DRIVETRAIN_WHEELBASE_METERS / 2.0, DRIVETRAIN_TRACKWIDTH_METERS / 2.0),
      new Translation2d(DRIVETRAIN_WHEELBASE_METERS / 2.0, -DRIVETRAIN_TRACKWIDTH_METERS / 2.0),
      new Translation2d(-DRIVETRAIN_WHEELBASE_METERS / 2.0, DRIVETRAIN_TRACKWIDTH_METERS / 2.0),
      new Translation2d(-DRIVETRAIN_WHEELBASE_METERS / 2.0, -DRIVETRAIN_TRACKWIDTH_METERS / 2.0));

  public static final class Mod0 {
    // TODO All these ids are wrong
    public static final int driveMotorID = 4;
    public static final int angleMotorID = 3;
    public static final int canCoderID = 1;
    public static final Rotation2d angleOffset = Rotation2d.fromDegrees(327.48046875);
    public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
        canCoderID, angleOffset);
  }

  /* Front Right Module - Module 1 */
  public static final class Mod1 {
    public static final int driveMotorID = 14;
    public static final int angleMotorID = 13;
    public static final int canCoderID = 2;
    public static final Rotation2d angleOffset = Rotation2d.fromDegrees(286.34765625);
    public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
        canCoderID, angleOffset);
  }

  /* Back Left Module - Module 2 */
  public static final class Mod2 {
    public static final int driveMotorID = 2;
    public static final int angleMotorID = 1;
    public static final int canCoderID = 3;
    public static final Rotation2d angleOffset = Rotation2d.fromDegrees(55.01953125);
    public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
        canCoderID, angleOffset);
  }

  /* Back Right Module - Module 3 */
  public static final class Mod3 {
    public static final int driveMotorID = 15;
    public static final int angleMotorID = 16;
    public static final int canCoderID = 4;
    public static final Rotation2d angleOffset = Rotation2d.fromDegrees(67.939453125);
    public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
        canCoderID, angleOffset);
  }
}
