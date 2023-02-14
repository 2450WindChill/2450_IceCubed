package frc.robot.commands;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DefaultDriveCommand extends CommandBase {
    private final DrivetrainSubsystem m_drivetrainSubsystem;

    // private final DoubleSupplier xLimiter;
    // private final DoubleSupplier m_translationYSupplier;
    // private final DoubleSupplier m_rotationSupplier;
    private final SlewRateLimiter xLimiter;
    private final SlewRateLimiter yLimiter;
    private final SlewRateLimiter turnLimiter;

    private final DoubleSupplier translationXSupplier;
    private final DoubleSupplier translationYSupplier;
    private final DoubleSupplier rotationSupplier;
    private final BooleanSupplier tempFieldCentricButtonPressed;


    public DefaultDriveCommand(DrivetrainSubsystem drivetrainSubsystem,
            DoubleSupplier translationXSupplier,
            DoubleSupplier translationYSupplier,
            DoubleSupplier rotationSupplier,
            BooleanSupplier tempFieldCentricButtonPressed) {
        this.m_drivetrainSubsystem = drivetrainSubsystem;
        this.translationXSupplier = translationXSupplier;
        this.translationYSupplier = translationYSupplier;
        this.rotationSupplier = rotationSupplier;

        xLimiter = m_drivetrainSubsystem.getXLimiter();
        yLimiter = m_drivetrainSubsystem.getYLimiter();
        turnLimiter = m_drivetrainSubsystem.getTurnLimiter();


        addRequirements(drivetrainSubsystem);
    }

    @Override
    public void execute() {

        // You can use `new ChassisSpeeds(...)` for robot-oriented movement instead of
        // field-oriented movement
        m_drivetrainSubsystem.drive(
                ChassisSpeeds.fromFieldRelativeSpeeds(
                    -modifyAxis(xLimiter.getAsDouble(), xLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
                    -modifyAxis(yLimiter.getAsDouble(), yLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND,
                    getTurnValue(),
                    drivetrain.getGyroscopeRotation()
                )
            );
        // } else {
            // drivetrain.drive(
            //     new ChassisSpeeds(
            //         -modifyAxis(translationXSupplier.getAsDouble(), xLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 
            //         -modifyAxis(translationYSupplier.getAsDouble(), yLimiter) * DrivetrainSubsystem.MAX_VELOCITY_METERS_PER_SECOND, 
            //         getTurnValue()
            //     )
    }

    @Override
    public void end(boolean interrupted) {
        m_drivetrainSubsystem.drive(new ChassisSpeeds(0.0, 0.0, 0.0));
    }
}
