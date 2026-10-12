package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {

    //TODO: FIGURE OUT CONFIG FOR MOTORS
    //TODO: FIGURE OUT CORRECT WAY TO SET VELOCITY TO MOTORS
    IntakeConstants intakeConstants = new IntakeConstants();

    TalonFX leftIntakeMotor = new TalonFX(intakeConstants.leftIntakeMotorID);
    TalonFX rightIntakeMotor = new TalonFX(intakeConstants.rightIntakeMotorID);

    public Intake() {

        leftIntakeMotor.getConfigurator().apply(IntakeConstants.getIntakeMotorConfig(false));
        rightIntakeMotor.getConfigurator().apply(IntakeConstants.getIntakeMotorConfig(true));

    }

    public Command setVelocity(double velocity) { 
        return Commands.runOnce(() -> { 
            leftIntakeMotor.setControl(new VelocityVoltage(velocity)); 
            rightIntakeMotor.setControl(new VelocityVoltage(velocity)); 
        }); 
    }



}
