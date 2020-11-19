/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package vn.rever.thrift.leadservice;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum Status implements org.apache.thrift.TEnum {
  LOOKING_FOR_RENTAL(0),
  RENTED(1),
  DO_NOT_CALL(2),
  NOT_INTERESTED(3),
  ARCHIVED(4);

  private final int value;

  private Status(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static Status findByValue(int value) { 
    switch (value) {
      case 0:
        return LOOKING_FOR_RENTAL;
      case 1:
        return RENTED;
      case 2:
        return DO_NOT_CALL;
      case 3:
        return NOT_INTERESTED;
      case 4:
        return ARCHIVED;
      default:
        return null;
    }
  }
}