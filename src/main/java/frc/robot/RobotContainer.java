// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;
import frc.robot.subsystems.Turret;

public class RobotContainer {

  CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

  CommandXboxController driver = new CommandXboxController(0);
  CommandXboxController operator = new CommandXboxController(1);
  //SUBSYSTEMS
  Intake intake = new Intake();
  Transfer transfer = new Transfer();

  Shooter shooter = new Shooter(drivetrain, transfer);

  Turret turret = new Turret(this.drivetrain);
  Hood hood = new Hood();

  private final SendableChooser<Command> autoChooser;

  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
          .withDeadband(0).withRotationalDeadband(0) // Deadband handled manually for better feel
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors


  public RobotContainer() {

    NamedCommands.registerCommand("intake", intake.fullIntake());
    NamedCommands.registerCommand("setFlywheelInitial", shooter.setFlywheelInitial());
    NamedCommands.registerCommand("stopShoot", transfer.stopAutoTransfer());
    NamedCommands.registerCommand("stopIntake", intake.stopIntake());
    NamedCommands.registerCommand("transfer", transfer.transferCommand());
    autoChooser = AutoBuilder.buildAutoChooser("BlueFirstAuto");
    SmartDashboard.putData("Auto Mode", autoChooser);

    configureBindings();

  }

  private void configureBindings() {
    double[] driverInputs = new double[3];

    CommandSwerveDrivetrain.joyStickPolar(driverInputs, driver);
    
    drivetrain.setDefaultCommand(
      drivetrain.applyRequest(() -> {
        CommandSwerveDrivetrain.joyStickPolar(driverInputs, driver);
        return drive.withVelocityX(driverInputs[0])
                    .withVelocityY(driverInputs[1])
                    .withRotationalRate(driverInputs[2]);
      })
    );

    intake.setDefaultCommand(intake.stopIntake());

    shooter.setDefaultCommand(shooter.stopShootRequest());

    driver.rightTrigger().whileTrue(intake.fullIntake());

    driver.leftBumper().whileTrue(shooter.requestShoot());

    operator.a().onTrue(shooter.changeShootAllowance());
  
  }

    /// HELPER FUNCTIONS \\\
    

  public Shooter getShooter() {
    return this.shooter;
  }
    
  public CommandSwerveDrivetrain getDrivetrain() {
    return this.drivetrain;
  }

  public Command getAutonomousCommand() {
    /* Run the path selected from the auto chooser */
    return autoChooser.getSelected();
  }
}
