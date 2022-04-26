package cn.xlystar.config;

import cn.xlystar.util.RedisUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;


/**
 * @ClassName: RedisConfig
 * @Author: 99847
 * @Description: redis配置类
 * @Date: 2022/2/25 10:49
 * @Version: 1.0
 */
@Configuration
public class RedisConfig {

    //json序列化器
    private Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);


//    @Bean
//    public LettuceConnectionFactory lettuceConnectionFactory() {
//        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
//        genericObjectPoolConfig.setMaxIdle(maxIdle);
//        genericObjectPoolConfig.setMinIdle(minIdle);
//        genericObjectPoolConfig.setMaxTotal(maxActive);
//        genericObjectPoolConfig.setMaxWaitMillis(maxWait);
//        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(100);
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setDatabase(database);
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
//        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
//                .commandTimeout(Duration.ofMillis(timeout))
//                .shutdownTimeout(Duration.ofMillis(shutDownTimeout))
//                .poolConfig(genericObjectPoolConfig)
//                .build();
//
//        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
////        factory.setShareNativeConnection(true);
////        factory.setValidateConnection(false);
//        return factory;
//    }

    //redisTemplate模板提供给其他类对redis数据库进行操作
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //如果不配置Serializer，那么存储的时候缺省使用String，如果用User类型存储，那么会提示错误User can't cast to String！
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 开启事务
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setRedisTemplate(redisTemplate);
        return redisUtil;
    }


    //redis键序列化使用StrngRedisSerializer
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    //redis值序列化使用json序列化器
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    //缓存键自动生成器
    @Bean
    public KeyGenerator myKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }


//    /**
//     * redis消息监听器容器
//     * 点赞消息订阅处理器
//     *
//     * @param collectListenerAdapter 关注消息订阅处理器
//     * @return
//     */
//    @Bean
//    RedisMessageListenerContainer container(LettuceConnectionFactory redisConnectionFactory,
//                                            MessageListenerAdapter collectListenerAdapter,
//                                            MessageListenerAdapter commentListenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(redisConnectionFactory);
//
//        // 以下为修改默认的序列化方式，网上大多数消息发布订阅都是String类型,但是实际中数据类型肯定不止String类型
//        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
//                Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        // 收藏主题并绑定消息订阅处理器
//        collectListenerAdapter.setSerializer(jackson2JsonRedisSerializer);
//        container.addMessageListener(collectListenerAdapter, new ChannelTopic("TOPIC_COLLECT"));
//
//        return container;
//    }
//
//    /**
//     * 收藏消息订阅处理器,并指定处理方法
//     *
//     * @param receiver
//     * @return
//     */
//    @Bean
//    MessageListenerAdapter commentListenerAdapter(RedisReceiver receiver) {
//        MessageListenerAdapter commentListenerAdapter = new MessageListenerAdapter(receiver);
//        //消息的反序列化方式
//        commentListenerAdapter.setSerializer(jackson2JsonRedisSerializer);
//        return commentListenerAdapter;
//    }

}
