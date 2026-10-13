package frc.robot.constants;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TransferShootConstants {

    public static final int transferMotorID = 3;
    
    public static final double transferSpeedRPS = 5;

    public static final int leftShooterMotorID = 4;
    public static final int rightShooterMotorID = 5;

    public static TalonFXConfiguration getShooterMotorConfig(boolean isInverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        Slot0Configs slot0 = config.Slot0;

        slot0.kP = 35.0;
        slot0.kI = 0.0;
        slot0.kD = 0.0;
        slot0.kS = 4.8;
        slot0.kV = 0.0;
        slot0.kA = 0.245143636466;

        config.CurrentLimits.SupplyCurrentLimit = 35.0;

        config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;

        config.Feedback.SensorToMechanismRatio = 42.0 / 12.0;
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.MotorOutput.Inverted = isInverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;
        return config;
    }


    public static double closeZoneVelocity = 2000;

    public static double farZoneVelocity = 4000;
    
}
