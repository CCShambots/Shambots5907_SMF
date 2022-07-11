package frc.robot.util.Shambots5907_SMF;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

import java.util.*;

public class SubsystemManager {
    private static SubsystemManager instance;
    private List<StatedSubsystem<?>> subsystems = new ArrayList<>();

    SubsystemManager() {}

    public static synchronized SubsystemManager getInstance() {
        if(instance == null) {
            instance = new SubsystemManager();
        }
        return instance;
    }

    public void registerSubsystem(StatedSubsystem<?> subsystem) {
        //Send the subsystem itself to network tables
        SmartDashboard.putData(subsystem.getName(), subsystem);

        //Send any additional sendables that should be included in the subsystem
        for(Map.Entry<String, Sendable> e : subsystem.additionalSendables().entrySet()) {
            SmartDashboard.putData(subsystem.getName() + "/" + e.getKey(), e.getValue());
        }

        subsystems.add(subsystem);
    }

    /**
     * Register a number of subsystems at once
     * @param subsystems array of stated subsystems 
     */
    public void registerSubsystems(StatedSubsystem<?> ...subsystems) {
        for(StatedSubsystem<?> s : subsystems) {
            registerSubsystem(s);
        }
    }

    /**
     * Compose a parallel command group  that will determine the state of every subsystem that has not yet determined itself
     * @return the command group that will determined every subsystem
     */
    public Command determineAllSubsystems() {
        List<Command> commands = new ArrayList<>();

        for(StatedSubsystem s : subsystems) {
            if(s.isUndetermined()) {  
                commands.add(s.goToState(s.getEntryState()));
            }
        }
        return new ParallelCommandGroup(commands.toArray(new Command[commands.size()]));
    }

    public void enableAllSubsystems() {
        for(StatedSubsystem s : subsystems) {
            s.enable();
        }
    }

    public void disableAllSubsystems() {
        for(StatedSubsystem s : subsystems) {
            s.disable();
        }
    }

    /**
     * Enable all subsystems and return the command to determine all subsystems
     */
    public Command prepSubsystems() {
        enableAllSubsystems();
        return determineAllSubsystems();
    }

}
