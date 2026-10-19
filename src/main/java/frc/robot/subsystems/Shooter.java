package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TransferShootConstants;
import frc.robot.constants.TunerConstants;
import frc.robot.constants.TunerConstants.ZONE;

public class Shooter extends SubsystemBase {

    TalonFX leftShooterMotor = new TalonFX(TransferShootConstants.leftShooterMotorID);
    TalonFX rightShooterMotor = new TalonFX(TransferShootConstants.rightShooterMotorID);
    
    NetworkTable m_table = NetworkTableInstance.getDefault().getTable("Shooter");


    DoublePublisher m_flywheelVelPub = m_table.getDoubleTopic("Flywheel/CurrentRPS").publish();


    private final VelocityTorqueCurrentFOC flywheelRequest = new VelocityTorqueCurrentFOC(0.0);

    double desiredFlwheelRPS = 0;

    boolean inCloseZone = false;

    CommandSwerveDrivetrain drivetrain;

    TunerConstants tunerConstants = new TunerConstants();

    boolean shootAllowed = false;
    boolean shootRequested = false;

    Pose2d currentPose;

    Transfer transfer;

    public Shooter(CommandSwerveDrivetrain drivetrain, Transfer transfer) {
        leftShooterMotor.getConfigurator().apply(TransferShootConstants.getShooterMotorConfig(false));
        rightShooterMotor.getConfigurator().apply(TransferShootConstants.getShooterMotorConfig(true));
        this.drivetrain = drivetrain;
        this.transfer = transfer;
    
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

        if (shootAllowed) {
            setFlywheelVelRPS(desiredFlwheelRPS);

            if (shootRequested) {
                transfer.transfer();
            }
            else {
                transfer.stopTransfer();
            }
        }
        else {
            setFlywheelVelRPS(0);
            transfer.stopTransfer();

        }

        }
        else if (DriverStation.isAutonomousEnabled()) {
            desiredFlwheelRPS = TransferShootConstants.autoShootVelocity;
            setFlywheelVelRPS(desiredFlwheelRPS);
        }


    }

    public void setFlywheelVelRPS(double velocity) {
            leftShooterMotor.setControl(flywheelRequest.withVelocity(velocity));
            rightShooterMotor.setControl(flywheelRequest.withVelocity(velocity));        
    }

    Command setDesiredFlywheel() {
        return Commands.runOnce(() ->
        setFlywheelVelRPS(desiredFlwheelRPS)
        , this);
    }

    public void resetShootState() {
    shootAllowed = false;
    shootRequested = false;
    }

    public Command setFlywheelInitial() {
        return Commands.runOnce(() -> {

        setFlywheelVelRPS(TransferShootConstants.autoShootVelocity);
        transfer.transfer();
        
    }, this);
    }

    public Command changeShootAllowance() {
        return new InstantCommand(() -> {
            shootAllowed = !shootAllowed;
        });
    }

    public Command requestShoot() {
        return Commands.startEnd(
            () -> shootRequested = true,
            () -> shootRequested = false,
            this);
    }


    public Command stopShootRequest() {
        return Commands.run(() -> {
            shootRequested = false;
        }, this);
    }

}
