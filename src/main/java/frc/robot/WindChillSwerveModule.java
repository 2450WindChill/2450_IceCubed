/**
 * Need:
 * Define swerve module constants
 * Pass in constants on construct
 * Define Class variables
 * SetDesiredStates(public) with setAngle(private) and setSpeed(private)
 * 
 * 
 * Backlog:
 * Configure components
 */
package frc.robot;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class WindChillSwerveModule {
    public int moduleNumber;
    private Rotation2d lastAngle;
    private Rotation2d angleOffset;

    private CANSparkMax angleMotor;
    private CANSparkMax driveMotor;

    private RelativeEncoder driveEncoder;
    private RelativeEncoder integratedAngleEncoder;
    private CANCoder angleEncoder;

    public WindChillSwerveModule(int moduleNumber, SwerveModuleConstants moduleConstants) {
        
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        // Custom optimize command, since default WPILib optimize assumes continuous controller which
        // REV and CTRE are not
        desiredState = desiredState.optimize(desiredState, getState().angle);
    
        setAngle(desiredState);
        setSpeed(desiredState);
    }

    private void setSpeed(SwerveModuleState desiredState) {
          double percentOutput = desiredState.speedMetersPerSecond / Constants.maxSpeed;
          driveMotor.set(percentOutput);
      }
    
      private void setAngle(SwerveModuleState desiredState) {
        // TODO PID stuff is very involved here, not sure how to remove it
        // Prevent rotating module if speed is less then 1%. Prevents jittering.
        // Rotation2d angle =
        //     (Math.abs(desiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01))
        //         ? lastAngle
        //         : desiredState.angle;
    
        // angleController.setReference(angle.getDegrees(), ControlType.kPosition);
        // lastAngle = angle;
      }

      private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(integratedAngleEncoder.getPosition());
      }
    
      public Rotation2d getCanCoder() {
        return Rotation2d.fromDegrees(angleEncoder.getAbsolutePosition());
      }

      public SwerveModuleState getState() {
        return new SwerveModuleState(driveEncoder.getVelocity(), getAngle());
      }
}

