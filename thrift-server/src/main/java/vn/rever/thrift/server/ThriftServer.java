package vn.rever.thrift.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.leadservice.LeadService;
import vn.rever.thrift.leadservice.LeadService.Processor;

public class ThriftServer {
  static Logger logger = LoggerFactory.getLogger(ThriftServer.class);
  static LeadServiceHandler handler;
  static LeadService.Processor processor;

  public static void main(String[] args) {
    try {
      handler = new LeadServiceHandler();
      processor = new Processor(handler);
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          doStartServer(processor);
        }
      };

      Thread runThread = new Thread(runnable);
      runThread.start();
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
  }

  /**
   * Initialize a server transport through a predefined socket
   *
   * @param processor Calculator processor for this simple server
   */
  public static void doStartServer(LeadService.Processor processor) {
    try {
      TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(9090);
      THsHaServer.Args args = new Args(serverTransport).maxWorkerThreads(10)
          .inputProtocolFactory(TBinaryProtocol::new).processor(processor);
      TServer server = new THsHaServer(args);

      // Use this for a multithreaded server
      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

      logger.info("Start server processing ...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
