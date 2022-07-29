package pt.iceman.comsinstrumentcluster.dashboard;

import java.util.concurrent.Future;

public interface ScreenState {
    Future<Boolean> transitState();
    void stop();
}
