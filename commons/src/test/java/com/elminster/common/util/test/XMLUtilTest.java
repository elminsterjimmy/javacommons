package com.elminster.common.util.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;

import com.elminster.common.util.XMLUtil;

public class XMLUtilTest {

  @Test
  public void testXML2Map() {
    try {
      Document doc = XMLUtil.convert2Document(this.getClass().getResourceAsStream("/test.xml"));
      Map<String, Object> map = XMLUtil.dom2Map(doc);
      
      
      Map<String, Object> expectMap = new HashMap<String, Object>();
      Map<String, Object> aMap = new HashMap<String, Object>();
      Map<String, Object> attMap = new HashMap<String, Object>();
      attMap.put("at", "at");
      aMap.put("#attribute", attMap);
      
      List<Object> bl = new ArrayList<Object>();
      Map<String, Object> bMap = new HashMap<String, Object>();
      // dMap
      List<Object> cl = new ArrayList<Object>();
      Map<String, Object> dMap = new HashMap<String, Object>();
      attMap = new HashMap<String, Object>();
      attMap.put("dt1", "dt1");
      attMap.put("dt2", "dt2");
      dMap.put("#attribute", attMap);
      dMap.put("#text", "dtext");
      cl.add(dMap);
      bMap.put("ddd", cl);
      // cMap
      cl = new ArrayList<Object>();
      Map<String, Object> cMap = new HashMap<String, Object>();
      attMap = new HashMap<String, Object>();
      attMap.put("ct", "ct1");
      cMap.put("#attribute", attMap);
      cMap.put("#text", "ctext1");
      cl.add(cMap);
      cMap = new HashMap<String, Object>();
      attMap = new HashMap<String, Object>();
      attMap.put("ct", "ct2");
      cMap.put("#attribute", attMap);
      cMap.put("#text", "ctext2");
      cl.add(cMap);
      cMap = new HashMap<String, Object>();
      attMap = new HashMap<String, Object>();
      attMap.put("ct", "ct3");
      cMap.put("#attribute", attMap);
      cMap.put("#text", "ctext3");
      cl.add(cMap);
      cMap = new HashMap<String, Object>();
      attMap = new HashMap<String, Object>();
      attMap.put("ct", "ct4");
      cMap.put("#attribute", attMap);
      cMap.put("#text", "ctext4");
      cl.add(cMap);
      bMap.put("ccc", cl);
      // bMap
      attMap = new HashMap<String, Object>();
      attMap.put("bt", "bt");
      bMap.put("#attribute", attMap);
      bl.add(bMap);
      bMap = new HashMap<String, Object>();
      bMap.put("#text", "empty");
      bl.add(bMap);
      aMap.put("bbb", bl);
      expectMap.put("aaa", aMap);
      
      System.out.println(map);
      System.out.println(expectMap);
      Assert.assertEquals(map, expectMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
