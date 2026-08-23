package frc.robot.Constants;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class IntakeConstants {

    public static final int kTopIntakeMotorPort = 1;

    public static final int kBottomIntakeMotorPort = 2;

    public enum IntakeObject {
      TOPINTAKE,
      BOTTOMINTAKE,

      TRANSFER
    }

    public static final TalonFXConfiguration getConfig(IntakeObject intakeObject) {
            TalonFXConfiguration config = new TalonFXConfiguration();

            if (intakeObject == IntakeObject.TOPINTAKE) {
              config.Slot0.kP = 0.11;
              config.Slot0.kI = 0.0;
              config.Slot0.kD = 0.001;
              config.Slot0.kV = 0.12; 
            } else if (intakeObject == IntakeObject.BOTTOMINTAKE) {
              config.Slot0.kP = 0.11;
              config.Slot0.kI = 0.0;
              config.Slot0.kD = 0.001;
              config.Slot0.kV = 0.12; 
            }
            else if (intakeObject == IntakeObject.TRANSFER) {
              config.Slot0.kP = 0.11;
              config.Slot0.kI = 0.0;
              config.Slot0.kD = 0.001;
              config.Slot0.kV = 0.12; 
            }
      
            config.CurrentLimits.StatorCurrentLimit = 40.0; //40 amps max
            config.CurrentLimits.StatorCurrentLimitEnable = true;

            return config;
    }
    
}
