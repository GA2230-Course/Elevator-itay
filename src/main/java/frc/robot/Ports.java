package frc.robot;

import com.ctre.phoenix6.CANBus;

public final class Ports {
    private Ports() {}

    public static final class ElevatorMotor {
        public static final int ID = 35;
        public static final CANBus BUS = new CANBus("rio");
    }
}