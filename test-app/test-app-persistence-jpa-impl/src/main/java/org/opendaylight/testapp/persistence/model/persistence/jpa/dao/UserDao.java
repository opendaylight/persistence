/*
 * Copyright (c) 2015 Hewlett-Packard Development Company, L.P. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.testapp.persistence.model.persistence.jpa.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.opendaylight.persistence.jpa.dao.JpaMappedKeyDao;
import org.opendaylight.persistence.util.common.type.Id;
import org.opendaylight.testapp.common.type.Username;
import org.opendaylight.testapp.common.model.User;
import org.opendaylight.testapp.common.model.UserFilter;
import org.opendaylight.testapp.common.model.UserFilter.ByEnabledStatus;
import org.opendaylight.testapp.common.model.UserFilter.All;
import org.opendaylight.testapp.persistence.model.persistence.jpa.entity.UserEntity;
import org.opendaylight.testapp.persistence.model.persistence.jpa.entity.UserEntity_;

public class UserDao extends JpaMappedKeyDao<Username, User, String, UserEntity, UserFilter, Void> {
	 /**
     * Creates a DAO.
     */
    public UserDao() {
        super(UserEntity.class);
    }

    @Override
    protected String mapKey(Username key) {
        return key.getValue();
    }

    @Override
    protected Username getId(UserEntity entity) {
        return entity.getId();
    }

    @Override
    protected UserEntity create(User user) {
        UserEntity entity = new UserEntity(user.getIdentifier()); // Double check again
        		//new UserEntity(user.getId().getValue());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());
        entity.setDescription(user.getDescription());
        entity.setEnabled(user.isEnabled());
        return entity;
    }

    @Override
    protected void conform(UserEntity target, User source) {
        target.setPassword(source.getPassword());
        target.setEmail(source.getEmail());
        target.setDescription(source.getDescription());
        target.setEnabled(source.isEnabled());
    }

    @Override
    protected User doConvert(UserEntity source) {
        Id<User, Username> id = Id.valueOf(source.getId());
        User user = new User(id.getValue()); // check again if User is Implement from UserName or both User and UserName
        user.setPassword(source.getPassword());
        user.setEmail(source.getEmail());
        user.setDescription(source.getDescription());
        user.setEnabled(source.isEnabled());
        return user;
    }

    @Override
    protected Predicate getQueryPredicate(UserFilter userFilter, final CriteriaBuilder builder,
            final Root<UserEntity> root) {
        UserFilter.Visitor<Predicate> visitor = new UserFilter.Visitor<Predicate>() {

            @Override
            public Predicate visit(All filter) {
                return null;
            }

            @Override
            public Predicate visit(ByEnabledStatus filter) {
                return getQueryPredicateGenerator().getPredicate(filter.getEnabledStatusCondition(),
                       UserEntity_.enabled, builder, root);
            }
        };

        return nonnull(userFilter).accept(visitor);
    }

    @Override
    protected SingularAttribute<? super UserEntity, ?> getSingularAttribute(Void sortKey) {
        return null;
    }

    private static UserFilter nonnull(UserFilter filter) {
        if (filter != null) {
            return filter;
        }
        return UserFilter.all();
    }
}
