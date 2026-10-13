package frc.robot.subsystems;

import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TunerConstants;
import frc.robot.constants.TurretConstants;

public class Turret extends SubsystemBase {
    private final TalonFX turretMotor = new TalonFX(TurretConstants.turretMotorID);
    
    // Create a reusable control request object to avoid garbage collection overhead in periodic
    private final PositionVoltage positionControl = new PositionVoltage(0);

    private final CommandSwerveDrivetrain drivetrain;
    private final Translation2d targetPose;

    public Turret(CommandSwerveDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
        this.targetPose = TunerConstants.getTargetPosition();
        
        // Apply configurations
        turretMotor.getConfigurator().apply(TurretConstants.getTurretMotorConfig());
    }

    @Override
    public void periodic() {
        Pose2d robotPose = drivetrain.getState().Pose;

        double dx = targetPose.getX() - robotPose.getX();
        double dy = targetPose.getY() - robotPose.getY();

        Rotation2d angleToTarget = new Rotation2d(dx, dy); // WPILib automatically handles Math.atan2(dy, dx)

        Rotation2d relativeTurretAngle = angleToTarget.minus(robotPose.getRotation());

        //TODO: Get turret motor gear ratio 
        double targetMotorRotations = relativeTurretAngle.getRotations(); // * TurretConstants.GEAR_RATIO;

        // 5. Correctly command the motor to use closed-loop position control
        turretMotor.setControl(positionControl.withPosition(targetMotorRotations));
    }
}
