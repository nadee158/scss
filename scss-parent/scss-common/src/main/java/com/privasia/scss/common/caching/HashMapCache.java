package com.privasia.scss.common.caching;

import java.util.HashMap;
import java.util.Map;

final class HashMapCache extends BaseCache implements Cache {
	
	private static Map<String, Object> client = new HashMap<String, Object>();

	@Override
	public <V> V get(String key) {
		return (V)HashMapCache.client.get(key);
	}

	@Override
	public <V> void set(String key, V value) {
		HashMapCache.client.put(key, value);
	}

	@Override
	public <V> void set(String key, V value, int expiry) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(String key) {
		HashMapCache.client.remove(key);
	}
    

}
