package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.AimSwerve;
import frc.robot.constants.TransferShootConstants;

public class Shooter extends SubsystemBase {
    
    TalonFX m_shooterMotor = new TalonFX(TransferShootConstants.kShooterMotorPort);
    NetworkTable m_shooterTable = NetworkTableInstance.getDefault().getTable("Shooter");

    BooleanPublisher m_ShootRequestPub = m_shooterTable.getBooleanTopic("ShootRequest").publish();
    BooleanPublisher m_CanShootPub = m_shooterTable.getBooleanTopic("CanShoot").publish();

    Boolean canShoot = false;
    Boolean shootRequested = false;  
    
    public Shooter() {
        m_shooterMotor.getConfigurator().apply(TransferShootConstants.getConfig());
    }

    @Override
    public void periodic() {
        m_ShootRequestPub.set(shootRequested);
        m_CanShootPub.set(canShoot);

        if (shootRequested) {
                //turn logic

                //TURNING LINE HERE(COMMAND)

                
                canShoot = true;
        }
        else {
            canShoot = false;
        }

        

    }


    public Command initShoot() {
        return Commands.runOnce(
            () -> shootRequested = true
        , this);
    }

    public Command stopShoot() {
        return new InstantCommand(() -> {
            shootRequested = false;
        
        }, this);

    }




    
}
