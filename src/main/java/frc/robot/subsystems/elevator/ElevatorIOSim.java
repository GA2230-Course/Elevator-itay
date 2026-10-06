package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import frc.robot.subsystems.elevator.ElevatorConstants;

public class ElevatorIOSim extends ElevatorIOCTRE{
    private static final double loopLength = 0.02;

    private final ElevatorSim elevatorSim = new ElevatorSim(
        DCMotor.getKrakenX60(1),
        ElevatorConstants.SENSOR_TO_MECHANISM_RATIO,
        ElevatorConstants.CARRIAGE_MASS_KG,
        ElevatorConstants.SPOOL_DIAMETER_METERS / 2.0,
        ElevatorConstants.MIN_HEIGHT_METERS,
        ElevatorConstants.MAX_HEIGHT_METERS,
        true,
        ElevatorConstants.MIN_HEIGHT_METERS

    );

    public ElevatorIOSim() {
        super();
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        TalonFXSimState simState = elevatorMotor.getSimState();
        simState.setSupplyVoltage(RobotController.getBatteryVoltage());
        elevatorSim.setInputVoltage(simState.getMotorVoltage());
        elevatorSim.update(loopLength);

        double spoolRotations = ElevatorConstants.metersToRotations(elevatorSim.getPositionMeters());
        double spoolRps = ElevatorConstants.metersToRotations(elevatorSim.getVelocityMetersPerSecond());
        simState.setRawRotorPosition(spoolRotations * ElevatorConstants.SENSOR_TO_MECHANISM_RATIO);
        simState.setRotorVelocity(spoolRps * ElevatorConstants.SENSOR_TO_MECHANISM_RATIO);

        super.updateInputs(inputs);
    }


    
}
