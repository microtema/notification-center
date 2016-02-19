package de.seven.fate.cache.infinispan;

import de.seven.fate.cache.UserCacheService;
import de.seven.fate.cache.enums.AttributeName;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;
import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mario on 19.02.2016.
 */
@Startup
@Lock(LockType.READ)
@Singleton(name = "UserCacheService")
public class UserInfinispanCacheService implements UserCacheService {


    private static Logger logger = Logger.getLogger(UserInfinispanCacheService.class);

    //expiration lifespan="3600000" interval="-1"
    @Resource(lookup = "java:jboss/infinispan/container/userCache")
    private CacheContainer container;

    private Cache<String, Map<AttributeName, Object>> cache;


    @PostConstruct
    private void init() {

        logger.info("Init user Cache.");

        cache = container.getCache();

    }

    @Override
    public boolean exist(String userName) {

        return cache.containsKey(userName);
    }

    @Override
    public <O> O getAttribute(String userName, AttributeName attributeName) {
        Validate.notNull(attributeName);

        if (!cache.containsKey(userName)) {

            return null;
        }

        return (O) cache.get(userName).get(attributeName);
    }


    @Override
    public void removeAttribute(String userName, AttributeName attributeName) {
        Validate.notNull(attributeName);

        if (!cache.containsKey(userName)) {

            cache.put(userName, new HashMap<AttributeName, Object>());
        }

        cache.get(userName).remove(attributeName);
    }


    @Override
    public void setAttribute(String userName, AttributeName attributeName, Object attributeValue) {
        Validate.notNull(userName);
        Validate.notNull(attributeName);

        if (!cache.containsKey(userName)) {

            cache.put(userName, new HashMap<AttributeName, Object>());
        }

        if (attributeValue == null) {
            cache.get(userName).remove(attributeName);
        } else {
            cache.get(userName).put(attributeName, attributeValue);
        }

    }

    @Override
    public void removeAttributes(String userName) {
        Validate.notNull(userName);

        if (!cache.containsKey(userName)) {

            return;
        }

        cache.get(userName).clear();
    }


    @Override
    @Asynchronous
    public void remove(String userName) {
        Validate.notNull(userName);

        if (!cache.containsKey(userName)) {

            return;
        }

        cache.remove(userName);
    }


    @Override
    public void clear() {

        cache.clear();
    }

}
