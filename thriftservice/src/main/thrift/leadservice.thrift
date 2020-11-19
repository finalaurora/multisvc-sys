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

service LeadService {
  /* Get all lead from current list */
  map<UserId,Lead>  getAll(),

  /* Get lead by their id */
  Lead getById(1:UserId id),

  /**
  * Add new lead to DB
**/
  void addNew(1:Lead newLead),

  /**
  * Remove Lead from DB by their id
**/
  void removeById(1:UserId id)

  /**
  * Update lead using new info
**/
  void updateLead(1:Lead lead)

}



