package frc.robot.subsystems.elevator;

import java.security.cert.X509CRL;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.subsystems.elevator.ElevatorSubsystem.ElevatorState;
import frc.robot.utils.GearsMath;

public class ElevatorConstants {

        public static final double MIN_HEIGHT_METERS = 0.0;
        public static final double MAX_HEIGHT_METERS = 1.15;
        public static final double IDLE_HEIGHT = 0;
        public static final double L1_HEIGHT = 0.2;
        public static final double L2_HEIGHT = 0.4;
        public static final double L3_HEIGHT = 0.9;
        public static final double L4_HEIGHT = 1.1;
        public static final double TOLERANCE_METERS = 0.01;
        public static final double SPOOL_DIAMETER_METERS = 3.23342 / 100.0;
        public static final double SPOOL_CIRCUMFERENCE_METERS = Math.PI * SPOOL_DIAMETER_METERS;
        public static final double CARRIAGE_MASS_KG = 2;
        public static final int[][] GEAR_CHAINS = { { 2, 9 } };
        public static final double SENSOR_TO_MECHANISM_RATIO = GearsMath.gearsToRatio(GEAR_CHAINS);

        public static final TalonFXConfiguration MOTOR_CONFIG = new TalonFXConfiguration()
                        .withFeedback(new FeedbackConfigs().withSensorToMechanismRatio(SENSOR_TO_MECHANISM_RATIO))
                        .withSlot0(new Slot0Configs().withKP(1.0).withKI(0).withKD(0).withKV(0).withKG(0
                        )
                                         .withGravityType(GravityTypeValue.Elevator_Static))
                        .withSoftwareLimitSwitch(new SoftwareLimitSwitchConfigs()
                                        .withForwardSoftLimitEnable(true)
                                        .withForwardSoftLimitThreshold(metersToRotations(MAX_HEIGHT_METERS))
                                        .withReverseSoftLimitEnable(true)
                                        .withReverseSoftLimitThreshold(metersToRotations(MIN_HEIGHT_METERS)))
                        .withMotorOutput(new MotorOutputConfigs()
                                        .withNeutralMode(NeutralModeValue.Brake).withInverted(InvertedValue.Clockwise_Positive))
                                        
                        .withMotionMagic(new MotionMagicConfigs().withMotionMagicCruiseVelocity(metersToRotations(1.5))
                                        .withMotionMagicAcceleration(metersToRotations(3.0)))
                        .withCurrentLimits(new CurrentLimitsConfigs()
                                        .withStatorCurrentLimit(60)
                                        .withStatorCurrentLimitEnable(true));

        public static double rotationsToMeters(double rotations) {
                return rotations * SPOOL_CIRCUMFERENCE_METERS;
        }

        public static double metersToRotations(double meters) {
                return meters / SPOOL_CIRCUMFERENCE_METERS;
        }

        

}
