// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.elevator.ElevatorIO;
import frc.robot.subsystems.elevator.ElevatorIOCTRE;
import frc.robot.subsystems.elevator.ElevatorIOSim;
import frc.robot.subsystems.elevator.ElevatorSubsystem;
import frc.robot.subsystems.elevator.ElevatorSubsystem.ElevatorState;

public class RobotContainer {
  private final CommandXboxController controller = new CommandXboxController(0);

  private final ElevatorIO elevatorIO =
      Robot.isReal() ? new ElevatorIOCTRE() : new ElevatorIOSim();
  private final ElevatorSubsystem elevator = new ElevatorSubsystem(elevatorIO);

  public RobotContainer() {
    configureBindings();
    
  }

  private void configureBindings() {
    controller.a().onTrue(elevator.setStateCommand(ElevatorState.IDLE));
    controller.b().onTrue(elevator.setStateCommand(ElevatorState.L1));
    controller.x().onTrue(elevator.setStateCommand(ElevatorState.L2));
    controller.y().onTrue(elevator.setStateCommand(ElevatorState.L3));
    controller.leftBumper().onTrue(elevator.setStateCommand(ElevatorState.L4));  
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}