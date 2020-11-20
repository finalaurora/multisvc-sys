package vn.rever.thrift.server;

import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService.Iface;

public class LeadServiceHandler implements Iface {

  Map<Long, Lead> leadCollection;

  Logger logger = LoggerFactory.getLogger(LeadServiceHandler.class);

  public LeadServiceHandler() {
    leadCollection = new HashMap<>();
  }

  public Map<Long, Lead> getAll() throws TException {
    logger.debug("getAll - Get all lead from collection");
    return leadCollection;
  }

  public Lead getById(long id) throws TException {
    logger.debug("getById - Get lead with id={}", id);
    return leadCollection.get(id);
  }

  public void addNew(Lead newLead) throws TException {
    if (!leadCollection.containsKey(newLead.id)) {
      leadCollection.put(newLead.id, newLead);
      logger.debug("New Lead added id = {}, lead = {}",newLead.id,newLead);
    } else {
      throw new TException("Lead with same id has existed in current list collection", new IllegalStateException());
    }
  }

  public void removeById(long id) throws TException {
    leadCollection.remove(id);
  }

  public void updateLead(Lead lead) throws TException {
    leadCollection.replace(lead.id, lead);
  }
}