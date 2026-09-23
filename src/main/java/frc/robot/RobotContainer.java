// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.GeneralConstants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;


public class RobotContainer {
//Subsystems are defined here
  private final Intake m_Intake = new Intake();
  private final Transfer m_Transfer = new Transfer();
  private final CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
  private final ShootMove m_Shooter = new ShootMove(m_drivetrain, new CommandXboxController(GeneralConstants.kDriverControllerPort));
  

  //Controller(s)
  private final CommandXboxController m_driverController =
      new CommandXboxController(GeneralConstants.kDriverControllerPort);

  ShootMove m_shootMove = new ShootMove(m_drivetrain, m_driverController);

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
        m_Shooter.executeCommand( 
            m_driverController.getRightY(), 
            m_driverController.getRightX()
        )
    );

    m_driverController.y().onTrue(m_shootMove.executeCommand(
      m_driverController.getLeftY(),
      m_driverController.getLeftX()
    ));
  }


}
