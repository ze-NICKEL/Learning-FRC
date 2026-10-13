package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TransferShootConstants;

public class Shooter extends SubsystemBase {

    TalonFX leftShooterMotor = new TalonFX(TransferShootConstants.leftShooterMotorID);
    TalonFX rightShooterMotor = new TalonFX(TransferShootConstants.rightShooterMotorID);
    
    boolean inCloseZone = false;

    public Shooter() {
        leftShooterMotor.getConfigurator().apply(TransferShootConstants.getShooterMotorConfig(false));
        rightShooterMotor.getConfigurator().apply(TransferShootConstants.getShooterMotorConfig(true));
    }


    @Override
    public void periodic() {

        if (inCloseZone) {
            shoot(TransferShootConstants.closeZoneVelocity);
        }
        else {
            shoot(TransferShootConstants.farZoneVelocity);
        }
    }


    Command shoot(double velocity) {
        return Commands.runOnce(() -> {
            //TODO: TUNE FOR FLYWHEEL VELOCITY
            leftShooterMotor.set(velocity);
            rightShooterMotor.set(velocity);
        });
                
    }
}
