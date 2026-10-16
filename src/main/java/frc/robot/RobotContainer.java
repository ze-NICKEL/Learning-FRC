// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;

public class RobotContainer {

  CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  CommandXboxController driverController = new CommandXboxController(0);

  //SUBSYSTEMS
  Intake intake = new Intake();
  Transfer transfer = new Transfer();

  Shooter shooter = new Shooter();

  private final SendableChooser<Command> autoChooser;



  public RobotContainer() {


    autoChooser = AutoBuilder.buildAutoChooser("BlueFirstAut");
    SmartDashboard.putData("Auto Mode", autoChooser);

    NamedCommands.registerCommand("intake", intake.fullIntake());
    NamedCommands.registerCommand("shoot", shooter.autoShoot(transfer));
      
      
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





  public Command getAutonomousCommand() {
      /* Run the path selected from the auto chooser */
      return autoChooser.getSelected();
  }
}
