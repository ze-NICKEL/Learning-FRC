// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.mechanisms.swerve.LegacySwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

  private double[] polarSpeeds = new double[3];


  public void printValues() {
    SmartDashboard.putNumber("rightX", m_driverController.getRightX());
SmartDashboard.putNumber("leftX", m_driverController.getLeftX());
SmartDashboard.putNumber("leftY", m_driverController.getLeftY());
SmartDashboard.putNumber("rotCmd", polarSpeeds[2]);
  }

    public RobotContainer() {

          SmartDashboard.putNumber("Xvalkjsasdf", 134134);

      configureBindings();
    }

    public CommandSwerveDrivetrain getDrivetrain() {

      return m_drivetrain;
    }

  public CommandXboxController getDriverController() {
    return m_driverController;
  }

private final SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric()
    .withDeadband(0.1); 
  
  //Definne button mappings
  private void configureBindings() {

    m_drivetrain.setDefaultCommand(
                m_drivetrain.applyRequest(() -> {
                        CommandSwerveDrivetrain.joyStickPolar(polarSpeeds, m_driverController);
                        return  driveRequest.withVelocityX(polarSpeeds[0]) // Drive forward with negative Y (forward)
                            .withVelocityY(polarSpeeds[1]) // Drive left with negative X (left)
                            .withRotationalRate(polarSpeeds[2]); // Drive counterclockwise with negative X (left)
                    })
            );


    printValues();

      }

            

    /*m_driverController.x().onTrue(m_Shooter.startShootMotors(TransferShootConstants.kShootSpeed));


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


