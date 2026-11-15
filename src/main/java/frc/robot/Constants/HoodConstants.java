package frc.robot.constants;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class HoodConstants {
    public static final int hoodMotorID = 18;
    public static final double hoodMaxLimit = 0.0;
    public static final double hoodMinLimit = -90.0;


public static TalonFXConfiguration getHoodMotorConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();
        Slot0Configs slot0 = config.Slot0;

        slot0.kP = 780.0;
        slot0.kI = 0.0;
        slot0.kD = 20.0;
        slot0.kS = 5.0;
        slot0.kV = 0.0;
        slot0.kA = 2.203;
        slot0.kG = 2.0;

        config.CurrentLimits.SupplyCurrentLimit = 11.0;

        config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;
        config.Feedback.SensorToMechanismRatio = (38.0 /12.0) * (50.0 / 1.0) * (29.0 / 30.0); //NOT ACCURATE RN
 
        config.MotionMagic.MotionMagicCruiseVelocity = 0.5;
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        return config;
    }
}
