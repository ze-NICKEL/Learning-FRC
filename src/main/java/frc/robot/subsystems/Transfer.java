package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.TransferShootConstants;

public class Transfer extends SubsystemBase {

    TalonFX transferMotor = new TalonFX(TransferShootConstants.transferMotorID);


    VelocityTorqueCurrentFOC transferRequest = new VelocityTorqueCurrentFOC(0);


    public Transfer() {

        transferMotor.getConfigurator().apply(TransferShootConstants.getTransferMotorConfig());
    
    }


    public Command transferCommand() {
        return Commands.runOnce(() ->
            transferMotor.setControl(transferRequest.withVelocity(TransferShootConstants.transferSpeedRPS))
        , this) ;
    }

    public void transfer() {
        transferMotor.setControl(transferRequest.withVelocity(TransferShootConstants.transferSpeedRPS));
    }
    public Command stopAutoTransfer() {
        return Commands.runOnce(() -> {
            transferMotor.set(0);
        }, this);
    }

    public void stopTransfer() {
            transferMotor.set(0);
    }
    
}
