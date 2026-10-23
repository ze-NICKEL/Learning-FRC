package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase { 

    TalonFX leftIntakeMotor = new TalonFX(IntakeConstants.leftMotorPortID);

    TalonFX rightIntakeMotor = new TalonFX(IntakeConstants.rightMotorPortID);
    
}
