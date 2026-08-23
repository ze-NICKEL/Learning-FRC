package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TransferShootConstants;

public class Transfer extends SubsystemBase{
    TalonFX m_transferMotor = new TalonFX(TransferShootConstants.kTransferShootMotorPort);

    NetworkTable m_transferTable = NetworkTableInstance.getDefault().getTable("Transfer");

    BooleanPublisher m_ShootRequestPub = m_transferTable.getBooleanTopic("ShootRequest").publish();

    DoublePublisher m_transferSpeedPub = m_transferTable.getDoubleTopic("TransferSpeed").publish();

    @Override
    public void periodic() {
        m_transferSpeedPub.set(m_transferMotor.get());
    } 

    public Command fullTransfer() {
        return Commands.startEnd(
            () -> m_transferMotor.set(1.0),
            () -> m_transferMotor.set(0.0)
        , this);
    }

    public Command forceStopTransfer() {
        return Commands.runOnce(() ->
        
            m_transferMotor.set(0.0) 
        , this
        );
    }

    
    
}
