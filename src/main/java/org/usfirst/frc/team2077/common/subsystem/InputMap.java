package org.usfirst.frc.team2077.common.subsystem;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class InputMap {
    private static final Map<Enum<?>, DoubleProvider> map = new IdentityHashMap<>();

    public static void bindAxis(Enum<?> key, DoubleProvider lambda){map.put(key, lambda);}

    public static double getInput(Enum<?> key){return map.get(key).get();}

    public interface DoubleProvider {double get();}

}
