package pt.iceman.comsinstrumentcluster.dashboard;

import java.util.concurrent.ExecutionException;

public interface State {
    void transitState() throws ExecutionException, InterruptedException;
}

