package org.usfirst.frc.team2077.util.SmartDash;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

//import edu.wpi.first.networktables.NetworkTableType;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.*;

public class SmartDashString implements SmartDashValue<String> {
    private static NetworkTable table = NetworkTableInstance.getDefault().getTable("SmartDashboard");

    private final NetworkTableEntry entry;
    private String value;

    public SmartDashString(String key, String defaultValue, boolean persistent) {
//        if(SmartDashboard.getNumber(key, Double.MIN_VALUE) == Double.MIN_VALUE) {
        SmartDashboard.putString(key, defaultValue);
//        }

        entry = table.getEntry(key);
        value = defaultValue;

        if(persistent) entry.setPersistent();
        else entry.clearPersistent();

//        var events = EnumSet.of(
//                Kind.kImmediate,
//                Kind.kValueAll
//        );
    }

    @Override public String get() {
        return value;
    }

    @Override public Optional<String> getNullable() {
        return Optional.ofNullable(value);
    }

    @Override public void set(String to) {
        entry.setString(to);
    }
}
