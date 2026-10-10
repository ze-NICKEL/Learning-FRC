package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.FuelSim;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class LaunchFuelSim extends Command {
    
    private double velocity;
    private double angle;
    private CommandSwerveDrivetrain drivetrain;
    private double disttohub;


    Timer shoot_timer = new Timer();
    Pose2d robotPose;

    FuelSim fuelSim;

    // time between shots
    double SHOOT_INTERVAL = 0.05;

    public LaunchFuelSim(CommandSwerveDrivetrain drivetrain, FuelSim fuelSim) {
        this.drivetrain = drivetrain;
        this.fuelSim = fuelSim;
    }

    boolean launchRequested = false;

    @Override
    public void initialize() {
        shoot_timer.restart();
    }

    @Override
    public void execute() {
        // 1. Get the current robot pose
        robotPose = drivetrain.getState().Pose;

        // 2. Calculate distance in inches (multiplying meters by 39.37)
        double dx = (robotPose.getX() - SwerveConstants.blueHub.getX()) * 39.37;
        double dy = (robotPose.getY() - SwerveConstants.blueHub.getY()) * 39.37;
        
        disttohub = Math.hypot(dx, dy);
        
        // 3. Target angle calculation in degrees
        angle = Math.min(Math.max(70 - (27.0 / 250.0) * (disttohub - 40), 47), 70);

        // Convert to radians as a primitive double for Math functions
        double angleRad = Math.toRadians(angle);

        // Math.cos and Math.tan expect radians directly
        double denominator = (2 * Math.pow(Math.cos(angleRad), 2) * (disttohub * Math.tan(angleRad) - (72 - 30)));

        // 4. Calculate exit velocity in m/s (386.09 is gravity in inches/s^2)
        velocity = Math.sqrt((386.09 * Math.pow(disttohub, 2)) / denominator) / 39.37; 
    
        // 5. Convert primitive doubles into WPILib Strongly Typed Units
        LinearVelocity wpilibVelocity = Units.MetersPerSecond.of(velocity);
        Angle wpilibHoodAngle = Units.Degrees.of(angle);
        
        // Assuming turret yaw points straight forward relative to robot heading (0 degrees)
        // If your turret turns, replace this with your actual turret angle measurement!
        Angle wpilibTurretYaw = Units.Degrees.of(0); 

        // Set the physical launch height of your shooter mechanism on the robot (e.g., 30 inches)
        Distance wpilibLaunchHeight = Units.Inches.of(30.0);

        // 6. Pass the properly typed variables into your physics sim
        if (launchRequested == true) {
        fuelSim.launchFuel(wpilibVelocity, wpilibHoodAngle, wpilibTurretYaw, wpilibLaunchHeight);
        }
    }   

    @Override
    public void end(boolean interrupted) {

        shoot_timer.stop();
    }


    public Command launchFuel() {
        return new InstantCommand(() ->
            launchRequested = true

        );
    }
}
