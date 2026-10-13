// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Transfer;

public class RobotContainer {

  CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  CommandXboxController driverController = new CommandXboxController(0);

  //SUBSYSTEMS
  Intake intake = new Intake();
  Transfer transfer = new Transfer();


    public RobotContainer() {
        configureBindings();
    }

    private void configureBindings() {

      driverController.rightTrigger().onTrue(intake.fullIntake()).onFalse(intake.stopIntake());
      driverController.rightBumper().onTrue(transfer.transfer()).onFalse(transfer.stopTransfer());
    }


    /// HELPER FUNCTIONS \\\
    
    public CommandSwerveDrivetrain getDrivetrain() {
      return this.drivetrain;
    }
}
