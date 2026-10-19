package frc.robot.subsystems;

import com.ctre.phoenix6.controls.MotionMagicTorqueCurrentFOC;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
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

    VelocityTorqueCurrentFOC intakeVelRequest = new VelocityTorqueCurrentFOC(0.0);


    MotionMagicVoltage retractRequest = new MotionMagicVoltage(0.0);
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
        return Commands.run(() -> { 
            leftRetractMotor.setControl(retractRequest.withPosition(IntakeConstants.retractDistance));
            rightRetractMotor.setControl(retractRequest.withPosition(IntakeConstants.retractDistance));
            leftIntakeMotor.setControl(intakeVelRequest.withVelocity(IntakeConstants.intakeVelocityRPS)); 
            rightIntakeMotor.setControl(intakeVelRequest.withVelocity(IntakeConstants.intakeVelocityRPS)); 
        }, this); 
    }  

    public Command stopIntake() {
        return Commands.run(() -> {
            leftRetractMotor.setControl(retractRequest.withPosition(0));
            rightRetractMotor.setControl(retractRequest.withPosition(0));
            leftIntakeMotor.set(0); 
            rightIntakeMotor.set(0);
        }, this);
    }
}
