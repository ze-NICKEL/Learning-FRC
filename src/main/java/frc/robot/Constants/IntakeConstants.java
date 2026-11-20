package frc.robot.constants;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.apriltag.AprilTagPoseEstimator.Config;


public class IntakeConstants {

    public int leftIntakeMotorID = 21;
    public int rightIntakeMotorID = 22;

    public static double intakeVelocityRPS = 20;
    
    public static int leftRetractID = 40;
    public static int rightRetractID = 40;


    public static double retractDistance = -0.15;


    public static TalonFXConfiguration getIntakeMotorConfig(boolean isInverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        Slot0Configs slot0 = config.Slot0;

        slot0.kP = 15.0;
        slot0.kI = 0.0;
        slot0.kD = 0.0;
        slot0.kS = 0.0;
        slot0.kV = 10.0;
        slot0.kA = 0.0;

        //config.CurrentLimits.SupplyCurrentLimit = 50.0;

        config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;

        config.Feedback.SensorToMechanismRatio = 1.5;
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        //config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.MotorOutput.Inverted = isInverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;


        return config;
    }

    public static TalonFXConfiguration getRetractMotorConfiguration(boolean isInverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();
        Slot0Configs slot0 = config.Slot0;

        slot0.kP = 50.0;
        slot0.kI = 0.0;
        slot0.kD = 0.0;
        slot0.kS = 5.0;
        slot0.kV = 0.0;
        slot0.kA = 0.0;

        slot0.GravityType = GravityTypeValue.Arm_Cosine;

        config.CurrentLimits.SupplyCurrentLimit = 45.0;
        config.MotionMagic.MotionMagicCruiseVelocity = 3.0;

        config.MotionMagic.MotionMagicAcceleration = 9.0;


        config.TorqueCurrent.PeakReverseTorqueCurrent = 0.0;

        config.Feedback.SensorToMechanismRatio = (100.0/15.0) * (60.0/46.0) * (50.0/30.0) * (38.0/12.0);
        
        config.MotorOutput.NeutralMode = NeutralModeValue.Coast;
        config.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        config.MotorOutput.Inverted = isInverted ? InvertedValue.Clockwise_Positive : InvertedValue.CounterClockwise_Positive;


        return config;
    }
    
}
