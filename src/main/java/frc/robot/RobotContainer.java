// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.LaunchFuelSim;
import frc.robot.constants.FuelSim;
import frc.robot.constants.GeneralConstants;
import frc.robot.constants.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Transfer;

public class RobotContainer {

    // 1. Define FuelSim ONCE as a final variable
    public final FuelSim fuelSim = new FuelSim("FuelSim");

    // Subsystems
    CommandSwerveDrivetrain m_drivetrain = TunerConstants.createDrivetrain();
    private final Intake m_Intake = new Intake();
    private final Transfer m_Transfer = new Transfer();
    private final Shooter m_Shooter = new Shooter(m_drivetrain, m_Transfer);

    // Controller
    private final CommandXboxController m_driverController =
        new CommandXboxController(GeneralConstants.kDriverControllerPort);

    // Drive State Helper
    private double[] polarSpeeds = new double[3];

    // Telemetry
    Telemetry logger = new Telemetry(CommandSwerveDrivetrain.MaxSpeed, m_drivetrain.getPigeon2());

    // Suppliers (Defined as fields so they initialize before use)
    private final Supplier<Pose2d> poseSupplier = () -> {
        return m_drivetrain.getState().Pose;
    };

    private final Supplier<ChassisSpeeds> fieldSpeedsSupplier = () -> {
        var state = m_drivetrain.getState();
        var s = state.Speeds;
        return new ChassisSpeeds(s.vxMetersPerSecond, s.vyMetersPerSecond, s.omegaRadiansPerSecond);
    };

    private final SwerveRequest.FieldCentric driveRequest = new SwerveRequest.FieldCentric()
            .withDeadband(0).withRotationalDeadband(0)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {

        // --- SIMULATION SETUP (Run Only Once) ---
        fuelSim.spawnStartingFuel();
        
        fuelSim.registerRobot(
            0.900, // width (meters)
            0.900, // length (meters)
            0.3,   // bumper height
            poseSupplier, 
            fieldSpeedsSupplier
        );

        // Define the "Magic Box" intake zone
        fuelSim.registerIntake(0.272, 0.8, -0.45, 0.45);
        
        fuelSim.start();  
        fuelSim.updateSim();
        // ----------------------------------------

        configureBindings();
    }

    private void configureBindings() {



        // Dashboard Button to Reset Simulation Fuel
        SmartDashboard.putData(Commands.runOnce(() -> {
                fuelSim.clearFuel();
                fuelSim.spawnStartingFuel();
            })
            .withName("Reset Fuel")
            .ignoringDisable(true));

        // Drivetrain Default Command
        m_drivetrain.setDefaultCommand(
            m_drivetrain.applyRequest(() -> {
                // Update polarSpeeds array based on joystick input
                CommandSwerveDrivetrain.joyStickPolar(polarSpeeds, m_driverController);
                
                return driveRequest.withVelocityX(polarSpeeds[0]) 
                    .withVelocityY(polarSpeeds[1]) 
                    .withRotationalRate(polarSpeeds[2]); 
            })
        );
        
        // Register Telemetry
        m_drivetrain.registerTelemetry(logger::telemeterize);            
        
        // Intake & Transfer Triggers
        // --- SHOOTING LOGIC (Real + Sim) ---
        // Runs the real motor sequence AND the physics calculation at the same time
m_driverController.rightBumper().whileTrue(
    m_Shooter.shootSequenceCommand(m_Transfer, polarSpeeds)
    .alongWith(new LaunchFuelSim(m_drivetrain, fuelSim)) // Combine commands
    .finallyDo((interrupted) -> m_Shooter.stopShootMotors()) 
);
    }

    public void printValues() {
        SmartDashboard.putNumber("rightX", m_driverController.getRightX());
        SmartDashboard.putNumber("leftX", m_driverController.getLeftX());
        SmartDashboard.putNumber("leftY", m_driverController.getLeftY());
        SmartDashboard.putNumber("rotCmd", polarSpeeds[2]);
    }

    public CommandSwerveDrivetrain getDrivetrain() {
        return m_drivetrain;
    }

    public CommandXboxController getDriverController() {
        return m_driverController;
    }
}
