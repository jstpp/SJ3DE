package SJ3DE_engine;

import SJ3DE_environment.Environment;

import java.util.TimerTask;

public class TimeUpdate extends TimerTask {
    @Override
    public void run() {
        Environment.parent_engine.updateObjects();
        Environment.parent_engine.repaint();
    }
}
