package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
    @AutoLog
    class ElevatorIOInputs {
        public boolean connected = false;
        public double setpointMeters = 0;
        public double positionMeters = 0;
        public double velocityMetersPerSec = 0;
        public double currentAmps = 0;

        public double profileReferenceMeters = 0;
        public double profileErrorMeters = 0; 
    }

    default void updateInputs(ElevatorIOInputs inputs) {}

    default void setHeight(double meters) {}
}