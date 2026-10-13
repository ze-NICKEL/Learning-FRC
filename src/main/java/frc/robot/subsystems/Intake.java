package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {

    IntakeConstants intakeConstants = new IntakeConstants();

    TalonFX leftIntakeMotor = new TalonFX(intakeConstants.leftIntakeMotorID);
    TalonFX rightIntakeMotor = new TalonFX(intakeConstants.rightIntakeMotorID);

    TalonFX leftRetractMotor = new TalonFX(IntakeConstants.leftRetractID);
    TalonFX rightRetractMotor = new TalonFX(IntakeConstants.rightRetractID);

    public Intake() {

        leftIntakeMotor.getConfigurator().apply(IntakeConstants.getIntakeMotorConfig(false));
        rightIntakeMotor.getConfigurator().apply(IntakeConstants.getIntakeMotorConfig(true));

        leftRetractMotor.getConfigurator().apply(IntakeConstants.getRetractMotorConfiguration(false));
        rightRetractMotor.getConfigurator().apply(IntakeConstants.getRetractMotorConfiguration(true));

    }

    @Override
    public void periodic() {
        
    }

    public Command fullIntake() { 
        return Commands.runOnce(() -> { 
            leftRetractMotor.setPosition(IntakeConstants.retractDistance);
            rightRetractMotor.setPosition(IntakeConstants.retractDistance);
            leftIntakeMotor.setControl(new VelocityVoltage(IntakeConstants.intakeVelocityRPS)); 
            rightIntakeMotor.setControl(new VelocityVoltage(IntakeConstants.intakeVelocityRPS)); 
        }); 
    }

    public Command stopIntake() {
        return Commands.runOnce(() -> {
            leftRetractMotor.setPosition(0);
            rightRetractMotor.setPosition(0);
            leftIntakeMotor.set(0); 
            rightIntakeMotor.set(0);
        });
    }
}
