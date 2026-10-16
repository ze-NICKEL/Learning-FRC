// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot;


import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler; 


/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {




  private final Field2d m_field = new Field2d();


  private Command m_autonomousCommand;

  RobotContainer m_RobotContainer;
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    m_RobotContainer = new RobotContainer();

    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.

    m_autonomousCommand = m_RobotContainer.getAutonomousCommand();

    SmartDashboard.putData("Field", m_field);

  }


  @Override
  public void robotPeriodic() {


   /*  m_drivetrain.drive(new ChassisSpeeds(
    -m_driverController.getLeftY() * 0.3,
    -m_driverController.getLeftX() * 0.3,
     -m_driverController.getRightX() * 0.3
   
    )); */


   
    CommandScheduler.getInstance().run();
  }


  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}


  @Override
  public void disabledPeriodic() {
   
  }


  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */


  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}


  @Override
  public void teleopInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }


  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    SmartDashboard.updateValues();
  }


  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }


  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}


  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}


  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {

  }
}