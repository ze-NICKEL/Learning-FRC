package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TransferShootConstants;
import frc.robot.constants.TunerConstants;

public class Shooter extends SubsystemBase {
    
    TalonFX m_shooterMotor = new TalonFX(TransferShootConstants.kShooterMotorPort);
    NetworkTable m_shooterTable = NetworkTableInstance.getDefault().getTable("Shooter");

    BooleanPublisher m_ShootRequestPub = m_shooterTable.getBooleanTopic("ShootRequest").publish();
    BooleanPublisher m_CanShootPub = m_shooterTable.getBooleanTopic("CanShoot").publish();
    Rotation2d targetRotation;
    Transfer m_transfer = new Transfer();

    boolean useRegularShoot = false;

    TunerConstants tunerConstants = new TunerConstants();

    CommandSwerveDrivetrain m_drivetrain;

    Boolean canShoot = false;
    Rotation2d targetAngleRelativeToRobot = new Rotation2d(0);
    
    public Shooter() {
        m_shooterMotor.getConfigurator().apply(TransferShootConstants.getConfig());
    }

    @Override
    public void periodic() {
        m_CanShootPub.set(canShoot);

            targetAngleRelativeToRobot = TunerConstants.getTargetAngleRelativeToRobot(
            m_drivetrain.getState().Pose, 
            TunerConstants.getTargetPosition()

            );

        if (canShoot) {
            m_transfer.m_transferMotor.set(TransferShootConstants.kTransferSpeed);
        } else {
            m_transfer.m_transferMotor.set(0.0);
        }

        targetAngleRelativeToRobot = new Rotation2d(
            m_drivetrain.getState().Pose.getRotation().getRadians() - 
        targetAngleRelativeToRobot.getRadians()
        );
    }
    //Sets shoot motor velocities
    public InstantCommand startShootMotors(double shootVelocityRPS) {
        return new InstantCommand(() -> 
        m_shooterMotor.set(TransferShootConstants.kShootSpeed)       
        );
    }

    public Command stopShootMotors() {
        return new InstantCommand(() -> m_shooterMotor.set(0.0));
    }


public Command shootSequenceCommand(CommandSwerveDrivetrain m_drivetrain, double velocityX, double velocityY) {
    return Commands.sequence(
        // 1. Initialize
        Commands.runOnce(() -> {
            m_ShootRequestPub.set(true);
            }, this),

            new ParallelCommandGroup(
            Commands.runOnce(() -> {
                m_drivetrain.applyRequest(() -> new SwerveRequest.FieldCentricFacingAngle()
                    .withVelocityX(velocityX)
                    .withVelocityY(velocityY)
                    .withTargetDirection(
                        new Rotation2d(
                            //goal
                            targetAngleRelativeToRobot.getRadians() -
                            m_drivetrain.getState().Pose.getRotation().getRadians()
                        )
                    )
                );
                this.m_drivetrain = m_drivetrain;
                canShoot = true;
            }, this)
        )
    ).finallyDo((interrupted) -> {
        canShoot = false;
    });
} 
}
