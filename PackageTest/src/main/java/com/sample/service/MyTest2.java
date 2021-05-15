package com.sample.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyTest2 {

  //1. 分布式锁的实现


  public static void main(String[] args) throws JSONException {
    String jsonStr = "";
    JSONObject jsonObject = new JSONObject(jsonStr);
    Iterator iterator = jsonObject.keys();
    List<String> result = new ArrayList<>();
    while (iterator.hasNext()) {
      String key = iterator.next().toString();
      Object object = jsonObject.get(key);

    }


  }


  public static void iterator(Object obj, String parentKey, List<String> result) throws JSONException {
    if (null == parentKey) {
      parentKey = "";
    }

    if (obj instanceof String) {
      result.add(parentKey + "." + obj.toString());
    } else {
      JSONObject jsonObject = new JSONObject(obj.toString());
      Iterator iterator = jsonObject.keys();

    }


  }
}
