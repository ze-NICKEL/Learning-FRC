package frc.robot.constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class TransferShootConstants {

    public static final int kTransferShootMotorPort = 3;
    
    public static final int kShooterMotorPort = 4;

    public static final TalonFXConfiguration getConfig() {
            TalonFXConfiguration config = new TalonFXConfiguration();

            config.Slot0.kP = 0.11;
            config.Slot0.kI = 0.0;
            config.Slot0.kD = 0.001;
            config.Slot0.kV = 0.12;
            config.CurrentLimits.StatorCurrentLimit = 40.0; //40 amps max
            config.CurrentLimits.StatorCurrentLimitEnable = true;

            return config;
    }
    
}
