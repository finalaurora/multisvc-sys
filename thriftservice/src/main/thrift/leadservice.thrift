# Sample Lead Service Definition

namespace java vn.rever.thrift.leadservice

enum Status {
  LOOKING_FOR_RENTAL = 0
  RENTED
  DO_NOT_CALL
  NOT_INTERESTED
  ARCHIVED
}

typedef i64 UserId

struct Lead {
  1: UserId id
  2: string firstName
  3: string lastName
  4: string email
  5: string phoneNumber
  6: Status userStatus
  7: string source
  8: i64 createdDate
  9: i64 updatedDate
}

exception OperationalException {
  1: string reason;
  2: OperationalError type;
}

enum OperationalError{
  SAME_ID_EXISTED
  REQUESTED_ID_NOT_EXISTS
  NO_RECORD_FOUND
}

service LeadService {
  /* Get all lead from current list */
  map<UserId,Lead>  getAll() throws (1: OperationalException opEx),

  /* Get lead by their id */
  Lead getById(1:UserId id) throws (1: OperationalException opEx),

  /**
  * Add new lead to DB
**/
  void addNew(1:Lead newLead) throws (1: OperationalException opEx),

  /**
  * Remove Lead from DB by their id
**/
  void removeById(1:UserId id) throws (1: OperationalException opEx)

  /**
  * Update lead using new info
**/
  void updateLead(1:Lead lead) throws (1: OperationalException opEx)

}



