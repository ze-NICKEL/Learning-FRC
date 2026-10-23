package frc.robot.constants;

import java.util.Optional;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class UtilConstants {

        //GOAL POSITIONs(TRANSLATION2D) FOR RED AND BLUE ALLIANCE

    public static final Translation2d redTargetPosition = new Translation2d(
        AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded).getTagPose(10).get().getX(),
        AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded).getTagPose(10).get().getY()
    );


        public static final Translation2d blueTargetPosition = new Translation2d(
        AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded).getTagPose(26).get().getX(),
        AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded).getTagPose(26).get().getY()
    );

//Checks if the alliance is present, and returns correct target position based on alliance color. If no alliance is present, it defaults to blue target position.
public static final Translation2d getTargetPosition() {

    var allianceOpt = DriverStation.getAlliance();
    
    if (allianceOpt.isPresent()) {
        if (allianceOpt.get() == Alliance.Red) {
            return redTargetPosition;
        } else {
            return blueTargetPosition;
        }
    }
    
    return blueTargetPosition; 
}


public enum ZONE {
    CLOSE,
    //MEDIUM,
    FAR
}


public ZONE getZone(Pose2d currentPose) {

    double dx = getTargetPosition().getX() - currentPose.getX();

    double dy = getTargetPosition().getY() - currentPose.getY();

    double xsq = Math.pow(dx, 2);
    double ysq = Math.pow(dy, 2);



    //TODO: TUNE CLOSE ZONE LOCATION
    if (Math.sqrt(xsq + ysq) <= 0.9144) {
        return ZONE.CLOSE;
    }
    /*else if (Math.sqrt(xsq + ysq) <= 1.8288) {
        return ZONE.MEDIUM;
    }*/

    else {
        return ZONE.FAR;
    }
}

    public Alliance getAlliance() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        if (alliance.isPresent()) {
            return alliance.get();
        }
        // Return a default fallback (e.g., Blue) if the FMS/Driver Station hasn't sent data yet
        return Alliance.Blue; 
    }

    public static Rotation2d getTargetAngleRelativeToRobot(Pose2d robotPose, Translation2d targetPosition) {
        double deltaX = targetPosition.getX() - robotPose.getX();
        double deltaY = targetPosition.getY() - robotPose.getY();

        double targetAngle = Math.atan2(deltaY, deltaX);

        targetAngle = Math.toDegrees(targetAngle);
        return new Rotation2d(((targetAngle + robotPose.getRotation().getRadians())));
    }
}