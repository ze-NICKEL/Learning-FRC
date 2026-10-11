package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.FuelSim;
import frc.robot.constants.SwerveConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class LaunchFuelSim extends Command {
    private static final double SHOOT_INTERVAL = 0.05;

    private final CommandSwerveDrivetrain drivetrain;
    private final FuelSim fuelSim;
    private final Timer shootTimer = new Timer();

    public LaunchFuelSim(
            CommandSwerveDrivetrain drivetrain,
            FuelSim fuelSim) {
        this.drivetrain = drivetrain;
        this.fuelSim = fuelSim;
    }

    @Override
    public void initialize() {
        shootTimer.restart();
    }

    @Override
    public void execute() {
        // Fire one fuel every SHOOT_INTERVAL seconds.
        if (!shootTimer.advanceIfElapsed(SHOOT_INTERVAL)) {
            return;
        }

        Pose2d robotPose = drivetrain.getState().Pose;

        /*
         * The ballistic calculation below uses inches:
         * - distance is in inches
         * - gravity is in inches/sec^2
         * - target and launch heights are in inches
         */

        double dxInches =
                (robotPose.getX() - SwerveConstants.blueHub.getX()) * 39.37;

        double dyInches =
                (robotPose.getY() - SwerveConstants.blueHub.getY()) * 39.37;

        double distanceInches = Math.hypot(dxInches, dyInches);

        if (distanceInches <= 0.0) {
            return;
        }

        // Hood angle in degrees.
        double hoodAngleDegrees = Math.min(
                Math.max(
                        70.0 - (27.0 / 250.0) * (distanceInches - 40.0),
                        47.0
                ),
                70.0
        );

        double hoodAngleRadians = Math.toRadians(hoodAngleDegrees);

        // Hub target height: 72 inches.
        // Shooter launch height: 30 inches.
        double heightDifferenceInches = 72.0 - 30.0;

        double denominator =
                2.0
                * Math.pow(Math.cos(hoodAngleRadians), 2.0)
                * (
                    distanceInches * Math.tan(hoodAngleRadians)
                    - heightDifferenceInches
                );

        // Avoid Math.sqrt() of a negative value.
        if (denominator <= 0.0) {
            return;
        }

        // 386.09 is gravity in inches/sec^2.
        double velocityInchesPerSecond = Math.sqrt(
                (386.09 * Math.pow(distanceInches, 2.0))
                / denominator
        );

        LinearVelocity launchVelocity =
                Units.MetersPerSecond.of(
                        velocityInchesPerSecond / 39.37
                );

        Angle hoodAngle =
                Units.Degrees.of(hoodAngleDegrees);

        // Shooter points straight forward relative to the robot.
        Angle turretYaw =
                Units.Degrees.of(0.0);

        Distance launchHeight =
                Units.Inches.of(30.0);

        fuelSim.launchFuel(
                launchVelocity,
                hoodAngle,
                turretYaw,
                launchHeight
        );
    }

    @Override
    public void end(boolean interrupted) {
        shootTimer.stop();
    }
}