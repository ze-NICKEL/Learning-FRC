// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.Intake;

public class RobotContainer {

  //Controllers
  CommandXboxController driver = new CommandXboxController(0);
  CommandXboxController operator = new CommandXboxController(1);

  Intake intake = new Intake();


  public RobotContainer() {

    configureBindings();

  }

    private void configureBindings() {

      driver.rightTrigger().whileTrue(intake.fullIntake());



      

    }


    //Getters
    public Intake getIntake() {
      return this.intake;
    }

    

}
