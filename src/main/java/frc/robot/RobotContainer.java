// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.GeneralConstants;
import frc.robot.constants.TransferShootConstants;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;


public class RobotContainer {
//Subsystems are defined here
  CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();

  private final Intake m_Intake = new Intake();
  private final Transfer m_Transfer = new Transfer();
  private final Shooter m_Shooter = new Shooter(m_drivetrain, m_Transfer);


  //Controller(s)
  private final CommandXboxController m_driverController =
      new CommandXboxController(GeneralConstants.kDriverControllerPort);


    //Main robot Container constructor.
    public RobotContainer() {
              m_drivetrain.setDefaultCommand(
            m_drivetrain.applyRequest(() -> new SwerveRequest.FieldCentric()
                .withVelocityX(-m_driverController.getLeftY() * 5)
                .withVelocityY(-m_driverController.getLeftX() * 5)
                .withRotationalRate(-m_driverController.getRightX() * 5)
            )
        );
    //Key mappings defined
    configureBindings();
  }

  //Definne button mappings
  private void configureBindings() {

    m_driverController.x().onTrue(m_Shooter.startShootMotors(TransferShootConstants.kShootSpeed));


    m_driverController.rightTrigger().whileTrue(m_Intake.fullIntake().finallyDo(() -> m_Intake.stopIntake()));

    m_driverController.leftTrigger().whileTrue(m_Transfer.fullTransfer().finallyDo(() -> m_Transfer.forceStopTransfer()));

    //Goes through shooting logic

    m_driverController.rightBumper().whileTrue(
      m_Shooter.shootSequenceCommand(
        m_driverController.getRightY(),
        m_driverController.getRightX()
      ).finallyDo(() -> m_Shooter.stopShootMotors()));
    


    
  }


}
