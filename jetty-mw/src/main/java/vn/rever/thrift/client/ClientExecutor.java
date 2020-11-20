package vn.rever.thrift.client;

import java.time.Instant;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService;
import vn.rever.thrift.leadservice.Status;

public class ClientExecutor {
  Logger logger = LoggerFactory.getLogger(ClientExecutor.class);

  LeadService.AsyncIface client;

  public ClientExecutor(LeadService.AsyncIface client){
    this.client = client;
  }

  public void doDemoWork() throws TException {
    Lead l1 = new Lead(1,"Rick","Shaw","rshaw@nortonfield.uk","123-3242-123", Status.LOOKING_FOR_RENTAL,"google.com",
        Instant.now().getEpochSecond(),Instant.now().getEpochSecond());
    client.addNew(l1, new AsyncMethodCallback<Void>() {
      @Override
      public void onComplete(Void response) {
        logger.debug("Add new lead complete");
      }

      @Override
      public void onError(Exception exception) {
        logger.error(exception.getMessage());
      }
    });
  }
}
