package com.balancetask.memcached;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemcachedController {

	final String MEMCACHED_SERVER = "test-maven-web-cache.t2k1d5.cfg.use1.cache.amazonaws.com:11211";
	
	@RequestMapping(value="/memcached")
	@ResponseBody 
	public String[] getHello(
			@RequestParam(value = "cmd", required = false, defaultValue = "set") String cmd,
			@RequestParam(value = "name", required = false, defaultValue = "a") String name,
			@RequestParam(value = "value", required = false, defaultValue = "foo") String value) {
		
		Object result = null;
		
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(MEMCACHED_SERVER));
		builder.setCommandFactory(new BinaryCommandFactory());
		
		try {
			MemcachedClient client = builder.build();
			
			if (cmd.equalsIgnoreCase("set")) {
				result = client.set(name, 0, value);
			}
			else if (cmd.equalsIgnoreCase("add")) {
				result = client.add(name, 0, value);
			}
			else if (cmd.equalsIgnoreCase("replace")) {
				result = client.replace(name, 0, value);
			}
			else if (cmd.equalsIgnoreCase("delete")) {
				result = client.delete(name);
			}
			else if (cmd.equalsIgnoreCase("get")) {
				result = client.get(name);
			}
			else {
				result = "Cmd not found";
			}
			
			client.shutdown();
		}
		catch (TimeoutException e) {
			result = e;
		}
		catch (InterruptedException e) {
			result = e;
		}
		catch (MemcachedException e) {
			result = e;
		}
		catch (IOException e) {
			result = e;
		}
		
		return new String[] { MEMCACHED_SERVER, cmd, name, value, (result != null ? result.toString() : null) };
	}

}
