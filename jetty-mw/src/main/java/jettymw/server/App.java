package jettymw.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.client.ClientExecutor;
import vn.rever.thrift.client.ThriftClient;

public class App {

  private static final int JETTY_PORT = 8080;
  static Logger logger = LoggerFactory.getLogger(App.class);
  private static ThriftClient tClient;
  private static JettyServer server;

  public static void main(String[] args) throws InterruptedException {
    singleClient();
  }

  private static void singleClient() throws InterruptedException {
    server = new JettyServer(JETTY_PORT);
    try {
      server.start();
      Thread.sleep(1000);
      if (!server.isStarted()) {
        logger.error("Cannot start Jetty Server");
        throw new Exception("Cannot start Jetty Server");
      }
      logger.debug("Jetty Server started");
    } catch (Exception e) {
      e.printStackTrace();
    }
    Thread.sleep(2000);
    tClient = new ThriftClient();
    tClient.connectServer();
    Thread.sleep(2000);
    ClientExecutor executor = new ClientExecutor(tClient.getClient());
    logger.info("Client Connected");
    server.setExecutor(executor);
    logger.info("Executor is set");
  }

  private void multiClient() {
    Thread jettyThread = new Thread(() -> {
      Server server = new JettyServer(JETTY_PORT);
      try {
        server.start();
        Thread.sleep(1000);
        if (false == server.isStarted()) {
          logger.error("Cannot start Jetty Server");
          throw new Exception("Cannot start Jetty Server");
        }
        logger.debug("Jetty Server started");
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Thread thriftThread = new Thread(() -> {
      try {
        ExecutorService exec = Executors.newFixedThreadPool(12);

        List<Runnable> execWorks = new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
          int finalI = i;
          execWorks.add(() -> {
            ThriftClient tClient = new ThriftClient();
            tClient.connectServer();
            ClientExecutor executor = new ClientExecutor(tClient.getClient());
            executor.doDemoWork();
            logger.debug("Client {} connected", finalI);
          });
        }

        execWorks.forEach(
            exec::execute
        );
      } catch (Exception ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
      }
    });
    jettyThread.start();
    thriftThread.start();
  }
}
