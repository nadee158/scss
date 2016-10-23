package com.privasia.scss.common.caching;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.common.config.PropFileUtil;




public final class CacheFactory {

  private static String PROP_FILE_NAME = "cacheserver";
  private static String cacheMode = null;

  private CacheFactory() {}


  static {
    cacheMode = StringUtils.trim(PropFileUtil.getValueFromFile(PROP_FILE_NAME, "cachemode"));
  }

  public static Cache getCache() {
    System.out.println("cacheMode :" + cacheMode);
    if (cacheMode.equals("inmemory")) {
      return new HashMapCache();
    } else if (cacheMode.equals("mamcached")) {
      String serverNames =
          PropFileUtil.getValueFromFile(PROP_FILE_NAME, "cacheserver.servername.list");
      return new SpyMemcachedCache(serverNames);
    } else {
      throw new IllegalStateException("invalid.cache.mode");
    }

  }
}
