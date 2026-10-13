package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.HoodConstants;

public class Hood extends SubsystemBase {

    TalonFX hoodMotor = new TalonFX(HoodConstants.hoodMotorID);

    public Hood() {
        hoodMotor.getConfigurator().apply(HoodConstants.getHoodMotorConfig());
    }

    @Override
    public void periodic() {
        //TODO: ADD HOOD REGRESSION HERE
    }
    
}
