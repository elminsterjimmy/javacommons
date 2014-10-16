package com.elminster.common.util.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.w3c.dom.Document;

import com.elminster.common.util.XMLUtil;

public class XMLUtilTest {

  @Test
  public void testXML2Map() {
    try {
      Document doc = XMLUtil.convert2Document(this.getClass().getResourceAsStream("/test.xml"));
      Map<String, Object> map = XMLUtil.dom2Map(doc);
      
      Map<String, Object> exceptMap = new HashMap<String, Object>();
      Map<String, Object> attMap = new HashMap<String, Object>();
      attMap.put("at", "at");
      exceptMap.put("#attribute", attMap);
      Map<String, Object> bMap = new HashMap<String, Object>();
      exceptMap.put("bbb", bMap);
      attMap = new HashMap<String, Object>();
      attMap.put("bt", "bt");
      bMap.put("#attribute", attMap);
      Map<String, Object> cMap = new HashMap<String, Object>();
      bMap.put("ccc", cMap);
      attMap = new HashMap<String, Object>();
      attMap.put("ct", "ct");
      cMap.put("#attribute", attMap);
      cMap.put("#text", "ctext");
      Map<String, Object> dMap = new HashMap<String, Object>();
      bMap.put("ddd", dMap);
      attMap = new HashMap<String, Object>();
      attMap.put("dt1", "dt1");
      attMap.put("dt2", "dt2");
      dMap.put("#attribute", attMap);
      dMap.put("#text", "dtext");
      
      Assert.assertEquals(map, exceptMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
