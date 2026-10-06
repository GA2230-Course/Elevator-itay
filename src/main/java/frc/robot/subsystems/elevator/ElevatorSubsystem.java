package frc.robot.subsystems.elevator;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.wpilibj.Alert;
import edu.wpi.first.wpilibj.Alert.AlertType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {
    public enum ElevatorState {
        IDLE,
        L1,
        L2,
        L3,
        L4
    }

    private final ElevatorIO io;
    private final ElevatorIOInputsAutoLogged inputs = new ElevatorIOInputsAutoLogged();

    private final Alert disconnectedAlert = new Alert("elevator motor disconnected", AlertType.kError);

    private ElevatorState wantedState = ElevatorState.IDLE;
    private ElevatorState currentState = ElevatorState.IDLE;

    private double setpoint;

    public ElevatorSubsystem(ElevatorIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Elevator", inputs);

        handleStateTransition();
        applyCurrentState();

        Logger.recordOutput("Elevator/WantedState", wantedState.name());
        Logger.recordOutput("Elevator/CurrentState", currentState.name());
        Logger.recordOutput("Elevator/AtGoal", isAtGoal());
        disconnectedAlert.set(!inputs.connected);

    }

    private void handleStateTransition() {
        if (wantedState != currentState) {
            currentState = wantedState;
        }
    }

    private void applyCurrentState() {
        switch (currentState) {
            case IDLE:
                setpoint = ElevatorConstants.IDLE_HEIGHT;
                io.setHeight(setpoint);
                break;
            case L1:
                setpoint = ElevatorConstants.L1_HEIGHT;
                io.setHeight(setpoint);
                break;
            case L2:
                setpoint = ElevatorConstants.L2_HEIGHT;
                io.setHeight(setpoint);
                break;
            case L3:
                setpoint = ElevatorConstants.L3_HEIGHT;
                io.setHeight(setpoint);
                break;
            case L4:
                setpoint = ElevatorConstants.L4_HEIGHT;
                io.setHeight(setpoint);
                break;
        }

    }

    public void setWantedState(ElevatorState state) {
        wantedState = state;
    }

    public boolean isAtGoal() {
        return Math.abs(inputs.positionMeters
                - setpoint) < ElevatorConstants.TOLERANCE_METERS;
    }

    public ElevatorState getCurrentState() {
        return currentState;
    }

    public Command setStateCommand(ElevatorState state) {
        return runOnce(() -> setWantedState(state));

    }

    public Command goToStateCommand(ElevatorState state) {
        return setStateCommand(state).andThen(run(() -> {
        }).until(this::isAtGoal));
    }

}
