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
        // desiredState = OnboardModuleState.optimize(desiredState, getState().angle);
    
        // setAngle(desiredState);
        // setSpeed(desiredState, isOpenLoop);
    }
}

