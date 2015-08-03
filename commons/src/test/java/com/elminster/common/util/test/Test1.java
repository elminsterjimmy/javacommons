package com.elminster.common.util.test;

public class Test1 {
  private String strKey;
  private int intKey;
  private boolean boolKey;
  private long longKey;
  private byte byteKey;
  private short shortKey;
  private float floatKey;
  private double doubleKey;
  public Test1() {
    
  }
  /**
   * @return the strKey
   */
  public String getStrKey() {
    return strKey;
  }
  /**
   * @param strKey the strKey to set
   */
  public void setStrKey(String strKey) {
    this.strKey = strKey;
  }
  /**
   * @return the intKey
   */
  public int getIntKey() {
    return intKey;
  }
  /**
   * @param intKey the intKey to set
   */
  public void setIntKey(int intKey) {
    this.intKey = intKey;
  }
  /**
   * @return the boolKey
   */
  public boolean isBoolKey() {
    return boolKey;
  }
  /**
   * @param boolKey the boolKey to set
   */
  public void setBoolKey(boolean boolKey) {
    this.boolKey = boolKey;
  }
  /**
   * @return the longKey
   */
  public long getLongKey() {
    return longKey;
  }
  /**
   * @param longKey the longKey to set
   */
  public void setLongKey(long longKey) {
    this.longKey = longKey;
  }
  /**
   * @return the byteKey
   */
  public byte getByteKey() {
    return byteKey;
  }
  /**
   * @param byteKey the byteKey to set
   */
  public void setByteKey(byte byteKey) {
    this.byteKey = byteKey;
  }
  /**
   * @return the shortKey
   */
  public short getShortKey() {
    return shortKey;
  }
  /**
   * @param shortKey the shortKey to set
   */
  public void setShortKey(short shortKey) {
    this.shortKey = shortKey;
  }
  /**
   * @return the floatKey
   */
  public float getFloatKey() {
    return floatKey;
  }
  /**
   * @param floatKey the floatKey to set
   */
  public void setFloatKey(float floatKey) {
    this.floatKey = floatKey;
  }
  /**
   * @return the doubleKey
   */
  public double getDoubleKey() {
    return doubleKey;
  }
  /**
   * @param doubleKey the doubleKey to set
   */
  public void setDoubleKey(double doubleKey) {
    this.doubleKey = doubleKey;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (boolKey ? 1231 : 1237);
    result = prime * result + byteKey;
    long temp;
    temp = Double.doubleToLongBits(doubleKey);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + Float.floatToIntBits(floatKey);
    result = prime * result + intKey;
    result = prime * result + (int) (longKey ^ (longKey >>> 32));
    result = prime * result + shortKey;
    result = prime * result + ((strKey == null) ? 0 : strKey.hashCode());
    return result;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Test1 other = (Test1) obj;
    if (boolKey != other.boolKey)
      return false;
    if (byteKey != other.byteKey)
      return false;
    if (Double.doubleToLongBits(doubleKey) != Double.doubleToLongBits(other.doubleKey))
      return false;
    if (Float.floatToIntBits(floatKey) != Float.floatToIntBits(other.floatKey))
      return false;
    if (intKey != other.intKey)
      return false;
    if (longKey != other.longKey)
      return false;
    if (shortKey != other.shortKey)
      return false;
    if (strKey == null) {
      if (other.strKey != null)
        return false;
    } else if (!strKey.equals(other.strKey))
      return false;
    return true;
  }
}