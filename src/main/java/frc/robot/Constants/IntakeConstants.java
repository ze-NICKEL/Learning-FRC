package frc.robot.constants;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;


public class IntakeConstants {

    public int leftIntakeMotorID = 1;
    public int rightIntakeMotorID = 2;

    public static double intakeVelocityRPS = 50;
    
    public static int leftRetractID = 4;
    public static int rightRetractID = 5;


    public static double retractDistance = 10;


    public static TalonFXConfiguration getIntakeMotorConfig(boolean isInverted) {
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

    public static TalonFXConfiguration getRetractMotorConfiguration(boolean isInverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();

        return config;
    }
    
}
