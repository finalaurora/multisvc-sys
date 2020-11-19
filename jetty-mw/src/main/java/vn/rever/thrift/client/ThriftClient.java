package vn.rever.thrift.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.leadservice.LeadService;
import vn.rever.thrift.leadservice.LeadService.Client;

public class ThriftClient {

  static Logger logger = LoggerFactory.getLogger(ThriftClient.class);

  LeadService.Iface  client;

  public LeadService.Iface getClient(){
    return this.client;
  }

  public void connectServer()
  {
    try{
      try (TTransport transport = new TSocket("localhost", 8088)) {
        logger.debug("Client socket created on port 8088");
        TFramedTransport framedTransport = new TFramedTransport(transport);
        TProtocol proto = new TBinaryProtocol(framedTransport);
        client = new Client(proto);
        transport.open();
        logger.debug("Client transport opened .");
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}
