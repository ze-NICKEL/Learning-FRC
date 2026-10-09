// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.FuelSim;
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
    SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric();


  private final Intake m_Intake = new Intake();
  private final Transfer m_Transfer = new Transfer();
  private final Shooter m_Shooter = new Shooter(m_drivetrain, m_Transfer);

  //Controller(s)
  private final CommandXboxController m_driverController =
      new CommandXboxController(GeneralConstants.kDriverControllerPort);

  public double[] polarSpeeds = new double[3];


  SwerveRequest.FieldCentric m_drivetrainRequest = new SwerveRequest.FieldCentric();


    public RobotContainer() {

      SmartDashboard.putNumber("rightX", m_driverController.getRightX());
SmartDashboard.putNumber("leftX", m_driverController.getLeftX());
SmartDashboard.putNumber("leftY", m_driverController.getLeftY());
SmartDashboard.putNumber("rotCmd", polarSpeeds[2]);

      configureBindings();
    // polarSpeeds[0] = X velocity in meters per second
    // polarSpeeds[1] = Y velocity in meters per second
    // polarSpeeds[2] = rotational rate in radians per second

    m_drivetrain.setDefaultCommand(
        m_drivetrain.applyRequest(() -> {
            // Process joystick input
            CommandSwerveDrivetrain.joyStickPolar(
                polarSpeeds,
                m_driverController
            );

            return m_drivetrainRequest
                .withVelocityX(polarSpeeds[0])
                .withVelocityY(polarSpeeds[1])
                .withRotationalRate(polarSpeeds[2] / 20);
        })
    );

    //FuelSim.getInstance().spawnStartingFuel();

    }

    public CommandSwerveDrivetrain getDrivetrain() {

      return m_drivetrain;
    }

  public CommandXboxController getDriverController() {
    return m_driverController;
  }

  //Definne button mappings
  private void configureBindings() {

    m_driverController.x().onTrue(m_Shooter.startShootMotors(TransferShootConstants.kShootSpeed));


    m_driverController.rightTrigger().whileTrue(m_Intake.fullIntake().finallyDo(() -> m_Intake.stopIntake()));

    m_driverController.leftTrigger().whileTrue(m_Transfer.fullTransfer().finallyDo(() -> m_Transfer.forceStopTransfer()));

    //Goes through shooting logic

    /*m_driverController.rightBumper().whileTrue(
      m_Shooter.shootSequenceCommand(
        m_Transfer,
        polarSpeeds
      ).finallyDo(() -> m_Shooter.stopShootMotors()));
    
*/
    
  }


}
