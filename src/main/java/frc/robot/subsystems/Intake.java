package frc.robot.subsystems;

import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase { 

    TalonFX leftIntakeMotor = new TalonFX(IntakeConstants.leftMotorPortID);
    TalonFX rightIntakeMotor = new TalonFX(IntakeConstants.rightMotorPortID);

    NetworkTable m_intakeTable = NetworkTableInstance.getDefault().getTable("Intake");
    DoublePublisher m_intakeVelPublisher = m_intakeTable.getDoubleTopic("Intake/Intake Vel").publish();

    VelocityTorqueCurrentFOC intakeRequest = new VelocityTorqueCurrentFOC(0);

    public Intake() {
        leftIntakeMotor.getConfigurator().apply(IntakeConstants.getIntakeMotorConfiguration(false));
        rightIntakeMotor.getConfigurator().apply(IntakeConstants.getIntakeMotorConfiguration(true));
    }

    public Command fullIntake() {
        return Commands.runEnd(() ->
        {
            runIntake();
        },
        () ->
        stopIntakeMotors());
    }

    public Command stopIntake() {
        return Commands.runEnd(() -> {
            stopIntakeMotors();
        },
        () -> {
            stopIntakeMotors();
        });
    }

    void runIntake() {
        leftIntakeMotor.setControl(intakeRequest.withVelocity(IntakeConstants.intakeVelocityRPS));
        rightIntakeMotor.setControl(intakeRequest.withVelocity(IntakeConstants.intakeVelocityRPS));
    
    }

    void stopIntakeMotors() {
        leftIntakeMotor.setControl(intakeRequest.withVelocity(0));
        rightIntakeMotor.setControl(intakeRequest.withVelocity(0));
    
    }
    
}
