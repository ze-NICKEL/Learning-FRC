package frc.robot.constants;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeConstants {

    public static int leftMotorPortID = 1;
    public static int rightMotorPortID = 2;

    public static double intakeVelocityRPS = 20;

    public static TalonFXConfiguration getIntakeMotorConfiguration(boolean isInverted) {

        TalonFXConfiguration config = new TalonFXConfiguration();

        Slot0Configs slot0 = config.Slot0;

        slot0.kP = 0.0;
        slot0.kI = 0.0;
        slot0.kD = 0.0;
        slot0.kV = 0.0;
        slot0.kA = 0.0;
        slot0.kS = 0.0;
        //TODO: finish config
        return config;
    }


    
    
}
