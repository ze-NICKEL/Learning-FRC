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
    
    private final PositionVoltage positionControl = new PositionVoltage(0);

    private final CommandSwerveDrivetrain drivetrain;
    private Translation2d targetPose;

    public Turret(CommandSwerveDrivetrain drivetrain) {
        this.drivetrain = drivetrain;
                
        // Apply configurations
        turretMotor.getConfigurator().apply(TurretConstants.getTurretMotorConfig());
        
        this.targetPose = TunerConstants.getTargetPosition();
    
    }

    @Override
    public void periodic() {

        targetPose = TunerConstants.getTargetPosition();
        Pose2d robotPose = drivetrain.getState().Pose;

        Rotation2d relativeTurretAngle =
            TunerConstants.getTargetAngleRelativeToRobot(robotPose, targetPose);

        //TODO: Get turret motor gear ratio 
        double targetMotorRotations = relativeTurretAngle.getRotations(); // * TurretConstants.GEAR_RATIO;

        double currentMotorRotations = turretMotor.getPosition().getValueAsDouble();
        double alternateTarget = targetMotorRotations
            + (targetMotorRotations < currentMotorRotations ? 1.0 : -1.0);

        // Nearest equivalent rotation
        if (alternateTarget >= TurretConstants.turretMinLimit / 360.0
                && alternateTarget <= TurretConstants.turretMaxLimit / 360.0
                && Math.abs(alternateTarget - currentMotorRotations)
                    < Math.abs(targetMotorRotations - currentMotorRotations)) {
            targetMotorRotations = alternateTarget;
        }

        turretMotor.setControl(positionControl.withPosition(targetMotorRotations));
    }
}
