package com.privasia.scss.common.caching;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;

final class SpyMemcachedCache extends BaseCache implements Cache {

  private MemcachedClient client = null;

  public SpyMemcachedCache(String serverNames) {
    try {
      this.client = new MemcachedClient(AddrUtil.getAddresses(serverNames));
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }

  @Override
  public <V> V get(String key) {
    System.out.println("CALEEDc egtg: " + key);
    return (V) this.client.get(key);
  }

  @Override
  public <V> void set(String key, V value) {
    this.set(key, value, Cache.DEFAULT_EXPIRATION_PERIOD);
  }

  @Override
  public <V> void set(String key, V value, int expiry) {
    this.client.set(key, expiry, value);
  }

  @Override
  public void remove(String key) {
    this.client.delete(key);
  }

}
