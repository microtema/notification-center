package it.de.seven.fate.cache.infinispan;

import de.seven.fate.cache.UserCacheService;
import de.seven.fate.cache.enums.AttributeName;

import javax.ejb.Singleton;
import javax.enterprise.inject.Alternative;

/**
 * Created by Mario on 19.02.2016.
 */
@Singleton(name = "UserCacheService")
@Alternative
public class UserInfinispanCacheAlternativeService implements UserCacheService {
    @Override
    public boolean exist(String userName) {
        return false;
    }

    @Override
    public <O> O getAttribute(String userName, AttributeName attributeName) {
        return null;
    }

    @Override
    public void setAttribute(String userName, AttributeName attributeName, Object attributeValue) {

    }

    @Override
    public void removeAttribute(String userName, AttributeName attributeName) {

    }

    @Override
    public void removeAttributes(String userName) {

    }

    @Override
    public void remove(String userName) {

    }

    @Override
    public void clear() {

    }
}
