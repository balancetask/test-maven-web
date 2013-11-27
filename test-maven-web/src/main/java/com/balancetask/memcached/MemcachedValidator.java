package com.balancetask.memcached;

import java.util.Date;

import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedServerException;

public class MemcachedValidator {

	MemcachedClient client;

	public MemcachedValidator(MemcachedClient client) {
		this.client = client;
	}

	public void testAll() throws Exception {
		testSet();
		testAdd();
		testReplace();
		testAppend();
		testPrepend();
		testCas();
		testIncr();
		testDecr();
	}
	
	void testSet() throws Exception {
		String name = "testSet";
		String value = "testSetValue " + (new Date()).toString();

		// Intentionally call set() twice and make sure they both succeed
		for (int i = 0; i < 2; i++) {
			set(name, value);
		}

		verify(name, value);
	}
	
	void testAdd() throws Exception {
		String name = "testAdd";
		String value = "testAddValue " + (new Date()).toString();
		
		delete(name);
		
		if (!client.add(name, 0, value)) {
			throw new Exception("MemcachedClient.add returned false: " + name);
		}

		// The second add should return false
		if (client.add(name, 0, value)) {
			throw new Exception("MemcachedClient.add returned true unexpectedly: " + name);
		}

		verify(name, value);
	}
	
	void testReplace() throws Exception {
		String name = "testReplace";
		String value = "testReplaceValue " + (new Date()).toString();
		
		delete(name);

		if (client.replace(name, 0, value)) {
			throw new Exception("MemcachedClient.replace returned true unexpectedly: " + name);
		}
		
		set(name, "orig");
		if (!client.replace(name, 0, value)) {
			throw new Exception("MemcachedClient.replace returned false: " + name);
		}
		
		verify(name, value);
	}
	
	void testAppend() throws Exception {
		String name = "testAppend";
		String orig = "testAppendValue ";
		String value = (new Date()).toString();
		
		delete(name);

		if (client.append(name, value)) {
			throw new Exception("MemcachedClient.append returned true unexpectedly: " + name);
		}
		
		set(name, orig);
		if (!client.append(name, value)) {
			throw new Exception("MemcachedClient.append returned false: " + name);
		}
		
		verify(name, orig + value);
	}
	
	void testPrepend() throws Exception {
		String name = "testPrepend";
		String orig = "testPrependValue ";
		String value = (new Date()).toString();
		
		delete(name);

		if (client.prepend(name, value)) {
			throw new Exception("MemcachedClient.prepend returned true unexpectedly: " + name);
		}
		
		set(name, value);
		if (!client.prepend(name, orig)) {
			throw new Exception("MemcachedClient.prepend returned false: " + name);
		}
		
		verify(name, orig + value);
	}
	
	void testCas() throws Exception {
		String name = "testCas";
		String origValue = "testCasOrig " + (new Date()).toString();
		String newValue = "testCasNew " + (new Date()).toString();

		delete(name);
		
		set(name, origValue);
		
		GetsResponse<String> res = client.gets(name);
		if (res == null) {
			throw new Exception("MemcachedClient.gets returned null: " + name);
		}
		
		if (client.cas(name, 0, "foo", res.getCas() - 1)) {
			throw new Exception("MemcachedClient.cas returned true unexpectedly: " + name);
		}

		if (!client.cas(name, 0, newValue, res.getCas())) {
			throw new Exception("MemcachedClient.cas returned false unexpectedly: " + name);
		}
		
		verify(name, newValue);
	}

	void testIncr() throws Exception {
		String name = "testIncr";
		int value = 2;
		
		set(name, name);
		try {
			client.incr(name, value);
			
			throw new Exception("MemcachedServerException expected");
		}
		catch (MemcachedServerException e) {
			// Good to go
		}
		
		set(name, value);
		long val = client.incr(name, value);
		verify(val == value * 2, "MemcachedClient.incr returned " + val);
	}
	
	void testDecr() throws Exception {
		String name = "testDecr";
		
		set(name, name);
		try {
			client.decr(name, 2);
			
			throw new Exception("MemcachedServerException expected");
		}
		catch (MemcachedServerException e) {
			// Good to go
		}
		
		set(name, 6);
		long val = client.decr(name, 4);
		verify(val == 2, "MemcachedClient.incr returned " + val);
	}
	
	void delete(String name) throws Exception {
		client.delete(name);
	}
	
	void set(String name, int value) throws Exception {
		set(name, ((Integer)value).toString());
	}
	
	void set(String name, String value) throws Exception {
		if (!client.set(name, 0, value)) {
			throw new Exception("MemcachedClient.set returned false: " + name);
		}
	}
	
	void verify(String name, String value) throws Exception {
		String val = client.get(name);
		if (!value.equals(val)) {
			throw new Exception("Expected: " + value + "; Actual: " + val);
		}
	}
	
	void verify(boolean cond, String message) throws Exception {
		if (!cond) {
			throw new Exception("Condition failed " + message);
		}
	}
}
