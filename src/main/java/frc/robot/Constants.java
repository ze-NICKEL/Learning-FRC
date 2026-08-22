// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;


public final class Constants {

    public static final int kDriverControllerPort = 0;

    public static final int kTopIntakeMotorPort = 1;

    public static final int kBottomIntakeMotorPort = 2;

    public enum IntakeObject {
      TOP,
      BOTTOM
    }

    public static final TalonFXConfiguration getConfig(IntakeObject intakeObject) {
            TalonFXConfiguration config = new TalonFXConfiguration();

            if (intakeObject == IntakeObject.TOP) {
              config.Slot0.kP = 0.11;
              config.Slot0.kI = 0.0;
              config.Slot0.kD = 0.001;
              config.Slot0.kV = 0.12; 
            } else if (intakeObject == IntakeObject.BOTTOM) {
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
