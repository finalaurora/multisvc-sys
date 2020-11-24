package vn.rever.thrift.client;

import static java.util.Arrays.asList;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.LeadService;
import vn.rever.thrift.leadservice.Status;

public class ClientExecutor implements IClientExecutor {

  private static final String EXCEPTION_DEFAULT_FORMAT = "Encounter error while processing, Exception = {}";

  private static final String NULL_CLIENT_PROVIDED = "Client instance being used for communication is null";

  Logger logger = LoggerFactory.getLogger(ClientExecutor.class);
  LeadService.AsyncIface client;

  public ClientExecutor(LeadService.AsyncIface client) {
    this.client = client;
  }

  /**
   * Demo some action to thrift channel
   */
  public void doDemoWork() {
    List<Lead> leadgroup = asList(
        new Lead(1, "Rick", "Shaw", "rshaw@nortonfield.uk", "123-3242-123",
            Status.LOOKING_FOR_RENTAL, "google.com",
            Instant.now().getEpochSecond(), Instant.now().getEpochSecond()),
        new Lead(2, "Json", "Statham", "jstatham@fast10.com", "134-213-4356", Status.ARCHIVED,
            "mannerrealestate.com", Instant.now().getEpochSecond(), Instant.now().getEpochSecond()),
        new Lead(3, "Steve", "Roger", "captain_of_america@avenger.com", "2345-213-235",
            Status.ARCHIVED, "rever.com", Instant.now().getEpochSecond(),
            Instant.now().getEpochSecond()),
        new Lead(4, "Tony", "Stark", "ironman@starkindustries.com", "134-234-4356",
            Status.ARCHIVED, "kragerrealestate.com", Instant.now().getEpochSecond(),
            Instant.now().getEpochSecond())
    );
    try {
      AsyncMethodCallback<Void> addNewCallback = new AsyncMethodCallback<>() {
        @Override
        public void onComplete(Void response) {
          logger.info("Add new item complete");
        }

        @Override
        public void onError(Exception exception) {
          logger.error(EXCEPTION_DEFAULT_FORMAT, exception.toString(),
              exception);
        }
      };
      client.addNew(leadgroup.get(0), addNewCallback);
      Thread.sleep(2000);
      client.addNew(leadgroup.get(1), addNewCallback);
      Thread.sleep(2000);
      client.addNew(leadgroup.get(2), addNewCallback);
      Thread.sleep(2000);
      client.addNew(leadgroup.get(3), addNewCallback);

    } catch (Exception ex) {
      logger.error("Encounter error", ex);
    }
  }

  @Override
  public List<Lead> getAllLeads() {
    if (client != null) {
      List<Lead> results = new ArrayList<>();
      AsyncMethodCallback<Map<Long, Lead>> getAllCallback = new AsyncMethodCallback<>() {
        @Override
        public void onComplete(Map<Long, Lead> response) {
          response.forEach((t, v) -> results.add(v));
          logger.debug("Get all Leads success , number of leads = {}", results.size());
        }

        @Override
        public void onError(Exception exception) {
          logger.error(EXCEPTION_DEFAULT_FORMAT, exception.toString(),
              exception);
        }
      };
      try {
        client.getAll(getAllCallback);
        Thread.sleep(1000);
      } catch (TException | InterruptedException e) {
        logger.error(EXCEPTION_DEFAULT_FORMAT, e.toString(), e);
      }
      return results;
    } else {
      throw new NullPointerException(NULL_CLIENT_PROVIDED);
    }
  }

  @Override
  public Lead getLead(int id) {
    if (client != null) {
      final Lead[] lead = {new Lead()};
      try {

        AsyncMethodCallback<Lead> getByIdCallback = new AsyncMethodCallback<>() {
          @Override
          public void onComplete(Lead response) {
            lead[0] = response;
            logger.debug("Get lead with id = {} success lead details: {}",response.getId(),response);
          }

          @Override
          public void onError(Exception exception) {
            lead[0] = null;
            logger.error(EXCEPTION_DEFAULT_FORMAT, exception.toString(),
                exception);
          }
        };

        client.getById(id, getByIdCallback);

      } catch (TException e) {
        logger.error(EXCEPTION_DEFAULT_FORMAT, e.toString(),
            e);
        return null;
      }
      return lead[0];
    } else {
      throw new NullPointerException(NULL_CLIENT_PROVIDED);
    }
  }

  @Override
  public boolean addLead(Lead newLead) {
    if (client != null) {
      AsyncMethodCallback<Void> addNewCallback = new AsyncMethodCallback<>() {
        @Override
        public void onComplete(Void response) {
          logger.info("Add new item complete");
        }

        @Override
        public void onError(Exception exception) {
          logger.error(EXCEPTION_DEFAULT_FORMAT, exception.toString(),
              exception);
        }
      };

      try {
        client.addNew(newLead, addNewCallback);
        return true;
      } catch (TException e) {
        logger.error(EXCEPTION_DEFAULT_FORMAT, e.toString(),
            e);
        return false;
      }

    } else {
      throw new NullPointerException(NULL_CLIENT_PROVIDED);
    }
  }

  @Override
  public boolean removeLead(int id) {
    if(client != null)
    {
      AsyncMethodCallback<Void> removeCallback = new AsyncMethodCallback<>() {
        @Override
        public void onComplete(Void response) {
          logger.debug("Remove lead with id = {} success",id);
        }

        @Override
        public void onError(Exception exception) {
          logger.error(EXCEPTION_DEFAULT_FORMAT, exception.toString(),
              exception);
        }
      };
      try {
        client.removeById(id, removeCallback);
        return true;
      } catch (TException e) {
        e.printStackTrace();
        return false;
      }
    } else {
      throw new NullPointerException(NULL_CLIENT_PROVIDED);
    }
  }

  @Override
  public boolean replaceLead(Lead lead) {
    if(client != null)
    {
      AsyncMethodCallback<Void> replaceCallback = new AsyncMethodCallback<>() {
        @Override
        public void onComplete(Void response) {
          logger.debug("Replace lead with id = {} by new lead with details: {}",lead.getId(), lead);
        }

        @Override
        public void onError(Exception exception) {
          logger.error(EXCEPTION_DEFAULT_FORMAT, exception.toString(),
              exception);
        }
      };
      try {
        client.updateLead(lead, replaceCallback);
        return true;
      } catch (TException e) {
        e.printStackTrace();
        return false;
      }
    } else {
      throw new NullPointerException(NULL_CLIENT_PROVIDED);
    }
  }
}
