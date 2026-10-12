package frc.robot.constants;
import com.ctre.phoenix6.configs.TalonFXConfiguration;


public class IntakeConstants {

    public int leftIntakeMotorID = 1;
    public int rightIntakeMotorID = 2;

    public static double intakeVelocityRPS = 50;





 public static TalonFXConfiguration getIntakeMotorConfig(boolean isInverted) {
        TalonFXConfiguration config = new TalonFXConfiguration();

        return config;
    }
    
}
