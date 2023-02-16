package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends CommandBase {
    private final DrivetrainSubsystem drivetrainSubsystem;

    private final SlewRateLimiter xLimiter;
    private final SlewRateLimiter yLimiter;
    private final SlewRateLimiter turnLimiter;

    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;
    private final BooleanSupplier orientationButtonButtonPressed;


    public DefaultDriveCommand(
            DrivetrainSubsystem drivetrainSubsystem,
            DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier,
            DoubleSupplier rotationSupplier,
            BooleanSupplier orientationButtonButtonPressed
        ) {
            this.drivetrainSubsystem = drivetrainSubsystem;
            this.translationXSupplier = translationXSupplier;
            this.translationYSupplier = translationYSupplier;
            this.rotationSupplier = rotationSupplier;

            this.orientationButtonButtonPressed = orientationButtonButtonPressed;

        xLimiter = drivetrainSubsystem.getXLimiter();
        yLimiter = drivetrainSubsystem.getYLimiter();
        turnLimiter = drivetrainSubsystem.getTurnLimiter();


        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void execute() { // It also allows us to switch drive modes at any point during the match.
        if(orientationButtonButtonPressed.getAsBoolean()) {
            drivetrainSubsystem.drive(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                    -modifyAxis(translationXSupplier.getAsDouble(), xLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
                    -modifyAxis(translationYSupplier.getAsDouble(), yLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
                    getTurnValue(),
                    drivetrainSubsystem.getGyroscopeRotation()
                )
            );
        } else {
            drivetrainSubsystem.drive(
                new ChassisSpeeds(
                    -modifyAxis(translationXSupplier.getAsDouble(), xLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 
                    -modifyAxis(translationYSupplier.getAsDouble(), yLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 
                    getTurnValue()
                )
            );
        }}


     // Seperated as a method so it may be overriden by other commands if needed.
     protected double getTurnValue() {
        return -modifyAxis(rotationSupplier.getAsDouble(), turnLimiter) * DrivetrainSubsystem.MAX_ANGULAR_VELOCITY_RADIANS_PER_SECOND;
    }

    private double modifyAxis(double value, SlewRateLimiter limiter) {
        // Deadband
        value = deadband(value, 0.05);
        // Square the axis for finer control at lower values
        value = limiter.calculate(Math.copySign(value * value, value));
        
        return value;

    }
    private static double deadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
          if (value > 0.0) {
            return (value - deadband) / (1.0 - deadband);
          } else {
            return (value + deadband) / (1.0 - deadband);
          }
        } else {
          return 0.0;
        }
      }

    @Override
    public void end(boolean interrupted) {
        drivetrainSubsystem.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}
