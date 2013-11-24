package com.balancetask.memcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemcachedController {

	final String MEMCACHED_SERVER = "test-maven-web-cache.t2k1d5.0001.use1.cache.amazonaws.com:11211";
	
	@RequestMapping(value="/memcached")
	@ResponseBody 
	public String[] getHello(
			@RequestParam(value = "cmd", required = false, defaultValue = "set") String cmd,
			@RequestParam(value = "name", required = false, defaultValue = "a") String name,
			@RequestParam(value = "value", required = false, defaultValue = "foo") String value) {
		
		Object result = null;
		
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(MEMCACHED_SERVER));
		try {
			MemcachedClient client = builder.build();
			
			if (cmd == "set") {
				result = client.set(name, 0, value);
			}
			else if (cmd == "add") {
				result = client.add(name, 0, value);
			}
			else if (cmd == "replace") {
				result = client.replace(name, 0, value);
			}
			else if (cmd == "delete") {
				result = client.delete(name);
			}
			else if (cmd == "get") {
				result = client.get(name);
			}
		}
		catch (TimeoutException e) {
			e.printStackTrace();
			result = e;
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			result = e;
		}
		catch (MemcachedException e) {
			e.printStackTrace();
			result = e;
		}
		catch (IOException e) {
			e.printStackTrace();
			result = e;
		}
		
		return new String[] { cmd, name, value, result != null ? result.toString() : null };
	}

}
