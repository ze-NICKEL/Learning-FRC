// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.IntakeConstants;

public class Intake extends SubsystemBase {
  
  TalonFX m_topIntakeMotor = new TalonFX(IntakeConstants.kTopIntakeMotorPort);
  TalonFX m_bottomIntakeMotor = new TalonFX(IntakeConstants.kBottomIntakeMotorPort);
  
  NetworkTable m_intakeTable = NetworkTableInstance.getDefault().getTable("Intake");
  
  DoublePublisher m_speedPub = m_intakeTable.getDoubleTopic("speed").publish();



  public Intake() {
    m_topIntakeMotor.getConfigurator().apply(IntakeConstants.getConfig(IntakeConstants.IntakeObject.TOPINTAKE));
    m_bottomIntakeMotor.getConfigurator().apply(IntakeConstants.getConfig(IntakeConstants.IntakeObject.BOTTOMINTAKE));

  }

  //This method will be called once per scheduler run
  @Override
  public void periodic() {
    
    m_speedPub.set(m_topIntakeMotor.get());
    
  }

public Command fullIntake() {
    return Commands.startEnd(
        () -> m_topIntakeMotor.set(1.0), // Runs exactly when the button is pressed down
        () -> m_topIntakeMotor.set(0.0), // Runs exactly when the button is released
        this                          // Protects the subsystem hardware
    );
}

public Command stopIntake() {
  return Commands.runOnce(() -> {
  
    m_topIntakeMotor.set(0.0);
    m_bottomIntakeMotor.set(0.0);
  }, this
  );
}

public void setIntakeSpeed(double speed) {
  m_topIntakeMotor.set(speed);
  m_bottomIntakeMotor.set(speed);
}


}
