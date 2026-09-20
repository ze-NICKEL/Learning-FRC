package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CommandSwerveDrivetrain;

public class AimSwerve extends Command {

    CommandSwerveDrivetrain drivetrain;
    Translation2d targetPosition;
    Pose2d currentPosition;

    //TODO: TUNE

    ProfiledPIDController thetaController = new ProfiledPIDController(
        1, 0, 0, 
        new Constraints(
            Math.PI, Math.PI
        )
    );
    

    public AimSwerve(CommandSwerveDrivetrain drivetrain, Translation2d targetPosition) {
        addRequirements(drivetrain);

        this.drivetrain = drivetrain;
        this.targetPosition = targetPosition;

        thetaController.enableContinuousInput(-Math.PI, Math.PI);

    }


    @Override   
    public void initialize() {
        Rotation2d currentHeading = drivetrain.getState().Pose.getRotation();
        
        thetaController.reset(currentHeading.getRadians(), drivetrain.getState().Speeds.omegaRadiansPerSecond);
    }

    @Override
    public void execute() {

        currentPosition = drivetrain.getState().Pose;

        Rotation2d currentHeading = currentPosition.getRotation();

        Rotation2d targetAngle = new Rotation2d(
            targetPosition.getX() - currentPosition.getX(), 
            targetPosition.getY() - currentPosition.getY()
        );

        double rotationSpeed = thetaController.calculate(
            currentHeading.getRadians(), 
            targetAngle.getRadians()
        );

        ChassisSpeeds ChassisRotationSpeeds = new ChassisSpeeds(
            0.0, 0.0, 
            rotationSpeed);

        drivetrain.drive(ChassisRotationSpeeds);




        //drivetrain.drive(new ChassisSpeeds(0, 0, rotationSpeed));

    }




}