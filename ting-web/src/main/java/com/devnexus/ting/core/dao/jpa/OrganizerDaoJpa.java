/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.devnexus.ting.core.dao.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.devnexus.ting.core.dao.OrganizerDao;
import com.devnexus.ting.core.model.Organizer;

@Repository("organizerDao")
public class OrganizerDaoJpa extends GenericDaoJpa< Organizer, Long>
                           implements OrganizerDao {

    /** Constructor. */
    private OrganizerDaoJpa() {
        super(Organizer.class);
    }

    @Override
    public List<Organizer> getAllOrganizers() {

        return super.entityManager
        .createQuery("select o from Organizer o "
                   + "order by o.sortOrder ASC", Organizer.class)
        .getResultList();
    }

    @Override
    public Organizer getOrganizerWithPicture(Long organizerId) {
        return super.entityManager
        .createQuery("select o from Organizer o left outer join fetch o.picture where o.id = :id", Organizer.class)
        .setParameter("id", organizerId)
        .getSingleResult();
    }

}
