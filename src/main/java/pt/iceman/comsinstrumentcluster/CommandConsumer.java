package pt.iceman.comsinstrumentcluster;

import javafx.application.Platform;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.iceman.comsinstrumentcluster.dashboard.Dashboard;
import pt.iceman.middleware.cars.BaseCommand;
import pt.iceman.middleware.cars.SimpleCommand;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandConsumer extends Thread {
    private static final Logger logger = LogManager.getLogger(CommandConsumer.class);

    private Dashboard dashboard;
    private BlockingQueue<SimpleCommand> commandQueue;

    public CommandConsumer(Dashboard dashboard, BlockingQueue<SimpleCommand> commandQueue) {
        this.dashboard = dashboard;
        this.commandQueue = commandQueue;
    }

    @Override
    public void run() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Map<String, SimpleCommand> commandMap = new HashMap<>();
        Stream.generate(() -> {
                  try {
                      return commandQueue.take();
                  } catch (InterruptedException e) {
                      logger.error("Stream interrupted!");
                      return null;
                  }
              })
              .filter(Objects::nonNull)
              .forEach(baseCommand -> {
                  commandMap.put(baseCommand.getType(), baseCommand);

                  if(counter.get() > 14) {
                      Platform.runLater(() -> {
                          final Map<String, SimpleCommand> commandMap2 = new HashMap<>(commandMap);
                          commandMap2.values().stream().forEach(baseCommand1 -> {
                              try {
                                  dashboard.applyCommand(baseCommand1);
                              } catch (InterruptedException | ExecutionException e) {
                                  logger.error("Problem getting command from command queue", e);
                              }
                          });
                      });
                      counter.set(0);
                  }

                  counter.incrementAndGet();
              });
    }
}
