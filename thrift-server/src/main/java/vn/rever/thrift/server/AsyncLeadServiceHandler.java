package vn.rever.thrift.server;

import java.util.Map;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService.AsyncIface;

public class AsyncLeadServiceHandler implements AsyncIface {

  @Override
  public void getAll(AsyncMethodCallback<Map<Long, Lead>> resultHandler) throws TException {

  }

  @Override
  public void getById(long id, AsyncMethodCallback<Lead> resultHandler) throws TException {

  }

  @Override
  public void addNew(Lead newLead, AsyncMethodCallback<Void> resultHandler) throws TException {

  }

  @Override
  public void removeById(long id, AsyncMethodCallback<Void> resultHandler) throws TException {

  }

  @Override
  public void updateLead(Lead lead, AsyncMethodCallback<Void> resultHandler) throws TException {

  }
}
