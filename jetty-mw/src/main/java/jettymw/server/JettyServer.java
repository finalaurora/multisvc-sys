package jettymw.server;

import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.client.ThriftClient;

public class JettyServer {
  private static final int jettyPort = 8080;

  static Logger logger = LoggerFactory.getLogger(JettyServer.class);

  public static Server createServer(int port) {
    Server server = new Server(port);
    // This has a connector listening on port specified
    // and no handlers, meaning all requests will result
    // in a 404 response
    server.setHandler(new SampleHandler());
    return server;
  }

  public static void main(String[] args) throws InterruptedException {
    Thread jettyThread = new Thread(()->{
      Server server = createServer(jettyPort);
      try {
        server.start();
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


    Thread thriftThread = new Thread(()->{
      try{
        ThriftClient tClient =  new ThriftClient();
        tClient.connectServer();
      }
      catch (Exception ex)
      {
        logger.error(ex.getMessage());
        ex.printStackTrace();
      }
    });
    jettyThread.start();
    thriftThread.start();
    jettyThread.join();
    thriftThread.join();
  }
}
