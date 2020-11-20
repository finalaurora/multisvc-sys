package jettymw.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.thrift.TException;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.client.ClientExecutor;
import vn.rever.thrift.client.ThriftClient;

public class JettyServer {

  private static final int JETTY_PORT = 8080;

  static Logger logger = LoggerFactory.getLogger(JettyServer.class);

  public static Server createServer(int port) {
    Server server = new Server(port);
    // This has a connector listening on port specified
    // and no handlers, meaning all requests will result
    // in a 404 response
    server.setHandler(new SampleHandler());
    return server;
  }

  public static void main(String[] args) {
    Thread jettyThread = new Thread(() -> {
      Server server = createServer(JETTY_PORT);
      try {
        server.start();
        logger.debug("Jetty Server started");
      } catch (Exception e) {
        e.printStackTrace();
      }
      try {
        server.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
        Thread.currentThread().interrupt();
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
            try {
              executor.doDemoWork();
            } catch (TException e) {
              e.printStackTrace();
            }
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
