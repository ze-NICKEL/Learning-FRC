// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  private final Intake m_Intake = new Intake();
  private final Transfer m_Transfer = new Transfer();
  private final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
  Shooter m_Shooter = new Shooter();

  //Controller(s)
  private final CommandXboxController m_driverController =
      new CommandXboxController(GeneralConstants.kDriverControllerPort);

    //Main robot Container constructor.
    public RobotContainer() {
    //Key mappings defined
    configureBindings();
  }

  //Definne button mappings
  private void configureBindings() {

    m_driverController.x().onTrue(m_Shooter.startShootMotors(TransferShootConstants.kShootSpeed));


    m_driverController.rightTrigger().whileTrue(m_Intake.fullIntake().finallyDo(() -> m_Intake.stopIntake()));

    m_driverController.leftTrigger().whileTrue(m_Transfer.fullTransfer().finallyDo(() -> m_Transfer.forceStopTransfer()));

    m_driverController.rightBumper().whileTrue(
        m_Shooter.shootSequenceCommand( 
          m_drivetrain,
            m_driverController.getRightY(), 
            m_driverController.getRightX()
        )
    );

  }


}
