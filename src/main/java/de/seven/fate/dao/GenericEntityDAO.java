package de.seven.fate.dao;

import de.seven.fate.util.ClassUtil;
import de.seven.fate.util.CollectionUtil;
import de.seven.fate.util.EntityUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * Created by Mario on 14.02.2016.
 */
public class GenericEntityDAO<E extends IdAble<I>, I> {

    private static final Logger logger = Logger.getLogger(GenericEntityDAO.class);

    private Class<E> entityType;
    private Class<I> entityIdType;
    private String primaryKeyColumnName;

    @PersistenceContext(unitName = "wcmsds")
    protected EntityManager em;

    /**
     * Persist takes an entity instance,
     * adds it to the context and makes that instance managed
     * (ie future updates to the entity will be tracked).
     *
     * @param entity
     */
    public void save(E entity) {
        Validate.notNull(entity, entityType + " should not be null");

        saveImpl(entity);
    }

    public void save(Collection<E> entities) {
        if (CollectionUtil.isEmpty(entities)) {
            return;
        }

        for (E entity : entities) {
            save(entity);
        }
    }

    protected void saveImpl(E entity) {
        assert entity != null;

        em.persist(entity);
    }

    /**
     * Check for existing entity
     * and if found copy properties to existing one
     * and then merge else save entity
     *
     * @param entity
     * @return entity
     */
    public E saveOrUpdate(E entity) {
        Validate.notNull(entity, "entity should not be null");

        if (entity.getId() == null) {
            save(entity);
        } else {
            return mergeImpl(entity);
        }

        return saveOrUpdate(get(entity), entity);
    }

    public E saveOrUpdate(E recent, E entity) {
        Validate.notNull(entity);

        if (recent == null) {
            save(entity);
            return entity;
        } else if (recent.equals(entity)) {
            return recent;
        }

        try {
            BeanUtils.copyProperties(recent, entity);
        } catch (Exception e) {

            logger.warn("unable to update properties from: "+entity+" to "+recent);
            throw new IllegalStateException(e);
        }

        return recent;
    }

    protected E mergeImpl(E entity) {
        assert entity != null;

        return em.merge(entity);
    }

    /**
     * @param id
     * @return founded entity or null
     */
    public E get(I id) {
        Validate.notNull(id, entityType + "#id should not be null");

        return em.find(entityType, id);
    }

    /**
     * @return founded entity by id or throw NoSuchEntityException
     */
    public E get(E entity) {
        Validate.notNull(entity, entityType + " should not be null");

        if (em.contains(entity)) {
            return entity;
        }

        return get(entity.getId());
    }

    /**
     * @param entity
     */
    public void remove(E entity) {
        Validate.notNull(entity, " entity should not be null");

        removeImpl(entity);
    }

    /**
     * remove entity by entityId
     *
     * @param entityId
     */
    public void remove(I entityId) {
        Validate.notNull(entityId, " entityId should not be null");

        removeImpl(get(entityId));
    }

    /**
     * @param entities
     */
    public void remove(Collection<E> entities) {
        if (CollectionUtil.isEmpty(entities)) {
            return;
        }

        for (E entity : entities) {
            remove(entity);
        }
    }

    public void removeAll() {
        remove(list());
    }

    protected void removeImpl(E entity) {
        assert entity!=null;

        E e = get(entity);

        if (e == null) {
            return;
        }

        em.remove(e);
    }

    private List<E> list() {

        CriteriaBuilder builder = em.getCriteriaBuilder();

        CriteriaQuery<E> criteria = builder.createQuery(entityType);
        Root<E> from = criteria.from(entityType);

        criteria.select(from);

        TypedQuery<E> typedQuery = em.createQuery(criteria);

        return typedQuery.getResultList();
    }

    public Long count() {

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);

        Root<E> from = criteria.from(entityType);

        criteria.select(builder.count(from));

        return em.createQuery(criteria).getSingleResult();
    }

    @PostConstruct
    private void init() {

        entityType = ClassUtil.getGenericType(getClass(), 0);
        entityIdType = ClassUtil.getGenericType(getClass(), 1);
        primaryKeyColumnName = EntityUtil.getPrimaryKeyColumnName(entityType);

    }
}
