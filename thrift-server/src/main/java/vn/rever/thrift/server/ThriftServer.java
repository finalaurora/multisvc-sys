package vn.rever.thrift.server;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.common.Definition.ThriftConstant;
import vn.rever.thrift.leadservice.LeadService;
import vn.rever.thrift.leadservice.LeadService.Iface;
import vn.rever.thrift.leadservice.LeadService.Processor;

public class ThriftServer {

  static Logger logger = LoggerFactory.getLogger(ThriftServer.class);
  static LeadServiceHandler handler;
  static LeadService.Processor<Iface> processor;
  static MongoAccess mongo;

  public static void main(String[] args) {
    try {
      handler = new LeadServiceHandler();
      processor = new Processor<>(handler);
      mongo = new MongoAccess();
      doStartServer(processor);
      mongo.connectMongoDB();
      handler.setMongoCollection(mongo.getMongoCollection());
    } catch (Exception ex) {
      logger.error(ex.getMessage());
    }
  }

  /**
   * Initialize a server transport through a predefined socket
   *
   * @param processor Calculator processor for this simple server
   */
  public static void doStartServer(LeadService.Processor<Iface> processor) {
    try (TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(
        ThriftConstant.THRIFT_SERVER_PORT)) {
      ExecutorService executor = new ThreadPoolExecutor(2, ThriftConstant.MAX_THREAD_ALLOW, ThriftConstant.SERVER_KEEP_ALIVE_TIME,
          TimeUnit.SECONDS,
          new ArrayBlockingQueue<>(10));
      Args args = new Args(serverTransport).maxWorkerThreads(ThriftConstant.MAX_THREAD_ALLOW)
          .protocolFactory(new TBinaryProtocol.Factory())
          .inputTransportFactory(new TFramedTransport.Factory()).processor(processor)
          .outputTransportFactory(new TFramedTransport.Factory())
          .executorService(executor);
      TServer server = new THsHaServer(args);
      logger.debug("Start server processing ...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
