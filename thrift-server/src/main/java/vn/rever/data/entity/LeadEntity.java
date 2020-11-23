package vn.rever.data.entity;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import vn.rever.thrift.leadservice.Lead;
import vn.rever.thrift.leadservice.Status;

public class LeadEntity {

  public long id; // required
  public java.lang.String firstName; // required
  public java.lang.String lastName; // required
  public java.lang.String email; // required
  public java.lang.String phoneNumber; // required
  /**
   * @see Status
   */
  public Status userStatus; // required
  public java.lang.String source; // required
  public long createdDate; // required
  public long updatedDate; // required

  public LeadEntity(){

  }

  public LeadEntity(long id, String firstName, String lastName, String email,
      String phoneNumber, Status userStatus, String source, long createdDate, long updatedDate) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.userStatus = userStatus;
    this.source = source;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public LeadEntity setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  public Status getUserStatus() {
    return userStatus;
  }

  public LeadEntity setUserStatus(Status userStatus) {
    this.userStatus = userStatus;
    return this;
  }

  public String getSource() {
    return source;
  }

  public LeadEntity setSource(String source) {
    this.source = source;
    return this;
  }

  public long getCreatedDate() {
    return createdDate;
  }

  public LeadEntity setCreatedDate(long createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  public long getUpdatedDate() {
    return updatedDate;
  }

  public LeadEntity setUpdatedDate(long updatedDate) {
    this.updatedDate = updatedDate;
    return this;
  }

  @Override
  public String toString() {
    SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'");
    Date update = Date.from(Instant.ofEpochSecond(updatedDate));
    Date created = Date.from(Instant.ofEpochSecond(createdDate));
    return String.format(
        "{ id: %d, firstName: %s, lastName: %s, email: %s, source: %s, status: %s, createdDate: %s, updatedDate: %s}",
        id, firstName, lastName, email, source, userStatus.toString(), timeFormat.format(created),
        timeFormat.format(update));
  }

  public Lead toLead(){
    return new Lead(this.id,this.firstName,this.lastName,this.email,this.phoneNumber,this.userStatus,this.source, this.createdDate, this.updatedDate);
  }

  public static LeadEntity fromLead(Lead lead)
  {
    LeadEntity entity = new LeadEntity();
    entity.id = lead.getId();
    entity.firstName = lead.getFirstName();
    entity.lastName = lead.getLastName();
    entity.email = lead.getEmail();
    entity.phoneNumber = lead.getPhoneNumber();
    entity.source = lead.getSource();
    entity.userStatus = lead.getUserStatus();
    entity.createdDate = lead.getCreatedDate();
    entity.updatedDate = lead.getUpdatedDate();
    return entity;
  }
}
