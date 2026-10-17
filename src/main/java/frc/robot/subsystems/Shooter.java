package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TransferShootConstants;
import frc.robot.constants.TunerConstants;
import frc.robot.constants.TunerConstants.ZONE;

public class Shooter extends SubsystemBase {

    TalonFX leftShooterMotor = new TalonFX(TransferShootConstants.leftShooterMotorID);
    TalonFX rightShooterMotor = new TalonFX(TransferShootConstants.rightShooterMotorID);
    
    NetworkTable m_table = NetworkTableInstance.getDefault().getTable("Shooter");


    DoublePublisher m_flywheelVelPub = m_table.getDoubleTopic("Flywheel/CurrentRPS").publish();


    double desiredFlwheelRPS = 0;

    boolean inCloseZone = false;

    CommandSwerveDrivetrain drivetrain;

    TunerConstants tunerConstants = new TunerConstants();

    Pose2d currentPose;

    public Shooter(CommandSwerveDrivetrain drivetrain) {
        leftShooterMotor.getConfigurator().apply(TransferShootConstants.getShooterMotorConfig(false));
        rightShooterMotor.getConfigurator().apply(TransferShootConstants.getShooterMotorConfig(true));
        this.drivetrain = drivetrain;
    
    
    }


    @Override
    public void periodic() {

        currentPose = drivetrain.getState().Pose;

        if (DriverStation.isTeleopEnabled()) {

        ZONE currentZone = tunerConstants.getZone(currentPose);

        if (currentZone == ZONE.CLOSE) {
            desiredFlwheelRPS = TransferShootConstants.closeZoneVelocity;
        }
        else {
            desiredFlwheelRPS = TransferShootConstants.farZoneVelocity;
        }

        m_flywheelVelPub.set(leftShooterMotor.getVelocity().getValueAsDouble());


        }
        else {
            desiredFlwheelRPS = TransferShootConstants.autoShootVelocity;
        }

        setFlywheelVelRPS(desiredFlwheelRPS);

    }

    /*
   @Override
    public void periodic() {

        if (inCloseZone) {
            shoot(TransferShootConstants.closeZoneVelocity);
        }
        else {
            shoot(TransferShootConstants.farZoneVelocity);
        }
    }
     */


    Command setFlywheelVelRPS(double velocity) {
        return Commands.runOnce(() -> {
            leftShooterMotor.set(velocity);
            rightShooterMotor.set(velocity);
        });
                
    }

    Command setDesiredFlywheel() {
        return Commands.runOnce(() ->
        
        setFlywheelVelRPS(desiredFlwheelRPS)
        
        );
    }

    public Command autoShoot(Transfer transfer) {
        return Commands.runOnce(() -> {

        setFlywheelVelRPS(TransferShootConstants.autoShootVelocity);
        transfer.transfer();
        
    });
    }
    
    public Command stopShoot(Transfer transfer) {

        return Commands.runOnce(() ->
        transfer.stopTransfer()
    );
}

}
