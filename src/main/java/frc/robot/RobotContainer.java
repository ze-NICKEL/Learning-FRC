// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;


public class RobotContainer {
//Subsystems are defined here
  private final Intake m_Intake = new Intake();

  //Controller(s)
  private final CommandXboxController m_driverController =
      new CommandXboxController(Constants.kDriverControllerPort);

    //Main robot Container constructor.
    public RobotContainer() {
    //Key mappings defined
    configureBindings();
  }

  //Definne button mappings
  private void configureBindings() {


    m_driverController.rightTrigger().onTrue(m_Intake.fullIntake());

    
  }


}
