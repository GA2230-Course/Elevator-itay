package frc.robot.subsystems.elevator;


import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Current;
import frc.robot.Ports;
import frc.robot.Robot;

public class ElevatorIOCTRE implements ElevatorIO{
    protected final TalonFX elevatorMotor;
    private final StatusSignal<Current> currentSignal;
    private final StatusSignal<AngularVelocity> velocitySignal;
    private final StatusSignal<Angle> rawPositionSignal;
    private double setPoint = 0;
    private final MotionMagicVoltage request = new MotionMagicVoltage(0);

    private final StatusSignal<Double> referenceSignal; //tuning


    public ElevatorIOCTRE() {
        elevatorMotor = new TalonFX(Ports.ElevatorMotor.ID, Ports.ElevatorMotor.BUS);
        elevatorMotor.getConfigurator().apply(ElevatorConstants.MOTOR_CONFIG);
        currentSignal = elevatorMotor.getStatorCurrent();
        velocitySignal = elevatorMotor.getVelocity();
        rawPositionSignal = elevatorMotor.getPosition();
        if (Robot.isReal()) {
            elevatorMotor.setPosition(0);
        }

        referenceSignal = elevatorMotor.getClosedLoopReference();
    
    }

    @Override
    public void updateInputs(ElevatorIOInputs inputs) {
        inputs.connected = BaseStatusSignal.refreshAll(currentSignal, velocitySignal, rawPositionSignal, referenceSignal).isOK();
        inputs.currentAmps = currentSignal.getValueAsDouble();
        inputs.velocityMetersPerSec = ElevatorConstants.rotationsToMeters(velocitySignal.getValueAsDouble());
        double rawPosition = rawPositionSignal.getValueAsDouble();
        inputs.positionMeters = ElevatorConstants.rotationsToMeters(rawPosition);
        inputs.setpointMeters = setPoint;

        inputs.profileReferenceMeters = ElevatorConstants.rotationsToMeters(referenceSignal.getValueAsDouble());
        inputs.profileErrorMeters = inputs.profileReferenceMeters - inputs.positionMeters;
        
    }

    @Override
    public void setHeight(double height) {
        double clampedVal = MathUtil.clamp(height,
        ElevatorConstants.MIN_HEIGHT_METERS, ElevatorConstants.MAX_HEIGHT_METERS);
        setPoint = clampedVal;
        elevatorMotor.setControl(request.withPosition(ElevatorConstants.metersToRotations(clampedVal)));

    }

    
    
}
