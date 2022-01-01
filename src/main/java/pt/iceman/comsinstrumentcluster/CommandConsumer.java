package pt.iceman.comsinstrumentcluster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.comsinstrumentcluster.dashboard.Dashboard;
import pt.iceman.middleware.cars.BaseCommand;

import java.util.concurrent.BlockingQueue;

public class CommandConsumer extends Thread{
    private static final Logger logger = LogManager.getLogger(CommandConsumer.class);

    private Dashboard dashboard;
    private BlockingQueue<BaseCommand> commandQueue;

    public CommandConsumer(Dashboard dashboard, BlockingQueue<BaseCommand> commandQueue) {
        this.dashboard = dashboard;
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                BaseCommand baseCommand = commandQueue.take();
                dashboard.applyCommand(baseCommand);
            } catch (InterruptedException e) {
                logger.error("Problem getting command from command queue", e);
            }
        }
    }
}
