package frc.robot.constants;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class HoodConstants {
    public static final int hoodMotorID = 6;
    public static final double hoodMaxLimit = 0.0;
    public static final double hoodMinLimit = -90.0;


public static TalonFXConfiguration getHoodMotorConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        Slot0Configs slot0 = config.Slot0;

        slot0.kP = 15.0;
        slot0.kI = 0.0;
        slot0.kD = 0.0;
        slot0.kS = 4.8;
        slot0.kV = 0.0;
        slot0.kA = 0.245143636466;

        config.CurrentLimits.SupplyCurrentLimit = 25.0;

        config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;

        config.Feedback.SensorToMechanismRatio = 32.0 / 12.0;
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

        return config;
    }
}
