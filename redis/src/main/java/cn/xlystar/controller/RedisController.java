package cn.xlystar.controller;

import cn.xlystar.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: RedisController
 * @Author: 99847
 * @Description:
 * @Date: 2022/2/25 11:04
 * @Version: 1.0
 */
@RestController
public class RedisController {
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping("/testRedisAdd")
    Object testRedisAdd(String key,String value){
        redisUtil.set(key,value);
        return redisUtil.get(key);
    }


}
