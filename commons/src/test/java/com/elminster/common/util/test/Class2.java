package com.elminster.common.util.test;

public class Class2 {
  private Class1 test1;

  /**
   * @return the test1
   */
  public Class1 getTest1() {
    return test1;
  }
  /**
   * @param test1 the test1 to set
   */
  public void setTest1(Class1 test1) {
    this.test1 = test1;
  }
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((test1 == null) ? 0 : test1.hashCode());
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
    Class2 other = (Class2) obj;
    if (test1 == null) {
      if (other.test1 != null)
        return false;
    } else if (!test1.equals(other.test1))
      return false;
    return true;
  }
}