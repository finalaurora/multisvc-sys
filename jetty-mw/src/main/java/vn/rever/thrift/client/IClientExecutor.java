package vn.rever.thrift.client;

import java.util.List;
import org.apache.thrift.TException;
import vn.rever.thrift.leadservice.Lead;

public interface IClientExecutor {
  public List<Lead> getAllLeads();
  public  Lead getLead(int id);
  public boolean addLead(Lead newLead);
  public boolean removeLead(int id);
  public boolean replaceLead(Lead lead);
}
