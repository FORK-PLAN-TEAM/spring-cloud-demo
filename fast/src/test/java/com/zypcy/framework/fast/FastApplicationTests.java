package com.zypcy.framework.fast;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.level.Level;
import com.zypcy.framework.fast.common.util.LogUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void md5(){
		String data = "123456" + "123456";
		//System.out.println(SecureUtil.md5(data));
		String newStr = SecureUtil.md5(data);
		//debug(newStr);
		LogUtil.debug("debug:" + newStr);
		LogUtil.info("info:" + newStr);
		LogUtil.warn("warn:" + newStr);
		LogUtil.error("error:" + newStr);
		LogUtil.trace("trace:" + newStr);
	}
}
