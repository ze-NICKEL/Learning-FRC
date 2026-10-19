package frc.robot.constants;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

public class TurretConstants {

    public static int turretMotorID = 8;

    public static double turretMaxLimit = 220.0;
    public static double turretMinLimit = -220.0;
    
    
        public static TalonFXConfiguration getTurretMotorConfig() {
            TalonFXConfiguration config = new TalonFXConfiguration();
    
            Slot0Configs slot0 = config.Slot0;
    
            slot0.kP = 0;
            slot0.kI = 0;
            slot0.kD = 0;
            slot0.kV = 0;
            slot0.kA = 0;
            slot0.kS = 0;
    
            config.CurrentLimits.SupplyCurrentLimit = 35.0;
    
            config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;
    
            config.Feedback.SensorToMechanismRatio = 43.5;
            
            config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
            config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    
            config.ClosedLoopGeneral.ContinuousWrap = false;

            config.SoftwareLimitSwitch.ReverseSoftLimitEnable = true;
            config.SoftwareLimitSwitch.ForwardSoftLimitEnable = true;

    
    
            config.SoftwareLimitSwitch.ForwardSoftLimitThreshold = turretMaxLimit / 360.0;  // +90

            config.SoftwareLimitSwitch.ReverseSoftLimitThreshold = turretMinLimit / 360.0;  // - 90

        return config;



    }
    
}
