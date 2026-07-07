package SJ3DE_environment;

import SJ3DE_engine.Engine;

import java.util.Random;

public abstract class Environment {
    public static float render_radius = 1000;
    public static float gap = 3;
    public static Engine parent_engine;
    public static Random random = new Random();
    public static float time_ticks = 0;
    public static int time_tick_duration = 100; // Tick duration in miliseconds
}
