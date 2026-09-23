package frc.robot.commands;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.TransferShootConstants;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
public class ShootMove extends Command{

    TalonFX m_shooterMotor = new TalonFX(TransferShootConstants.kShooterMotorPort);

    TunerConstants tunerConstants = new TunerConstants();


    Translation2d targetPose = TunerConstants.getTargetPosition();

    CommandSwerveDrivetrain m_drivetrain;

    Rotation2d targetAngleRelativeToRobot;

    private double targetShootSpeedRPS = TransferShootConstants.kShootSpeed;

    CommandXboxController m_driverController;

    public ShootMove(CommandSwerveDrivetrain m_drivetrain, CommandXboxController m_driverController) {

        this.m_drivetrain = m_drivetrain;

        this.m_driverController = m_driverController;
    }

    @Override
    public void execute() {


        targetAngleRelativeToRobot = TunerConstants.getTargetAngleRelativeToRobot(
            m_drivetrain.getState().Pose,
            targetPose
        ); 
        
        if (tunerConstants.currentShootState == TunerConstants.SHOOT_STATE.SOTM) {
            executeCommand(
                m_driverController.getLeftY(),
                m_driverController.getLeftX()
            );
        }
    }


    public Command executeCommand(double velocityX, double velocityY) {
         return Commands.runOnce(() -> {
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
            }, m_drivetrain);
    }







    
}
