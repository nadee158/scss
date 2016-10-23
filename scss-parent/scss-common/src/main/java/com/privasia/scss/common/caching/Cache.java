package com.privasia.scss.common.caching;

public interface Cache {
	
	public final int DEFAULT_EXPIRATION_PERIOD = 100000;

	public <V> V get(String key);

	public <V> void set(String key, V value);

	public <V> void set(String key, V value, int expiry);

	public void remove(String key);
	
}
