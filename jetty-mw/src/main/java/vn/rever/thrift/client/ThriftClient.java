package vn.rever.thrift.client;

import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.leadservice.LeadService;
import vn.rever.thrift.leadservice.LeadService.AsyncClient;

public class ThriftClient {

  static Logger logger = LoggerFactory.getLogger(ThriftClient.class);

  LeadService.AsyncIface  client;

  public LeadService.AsyncIface getClient(){
    return this.client;
  }

  public void connectServer()
  {
    try{
      TNonblockingSocket socket = new TNonblockingSocket("localhost", 8088);
        TTransport framedTransport = new TFramedTransport(socket);
        logger.debug("Client socket created on port 8088");
        TAsyncClientManager async_CM = new TAsyncClientManager();
        client = new AsyncClient.Factory(async_CM, new TBinaryProtocol.Factory())
            .getAsyncClient(socket);
      logger.debug("Client transport opened .");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
