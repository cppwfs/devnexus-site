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

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.devnexus.ting.core.dao.EventDao;
import com.devnexus.ting.core.model.Event;

/**
 *
 * @author Gunnar Hillert
 * @since  1.0
 *
 */
@Repository("eventDao")
public class EventDaoJpa extends GenericDaoJpa< Event, Long>
						   implements EventDao {

	/** Constructor. */
	public EventDaoJpa() {
		super(Event.class);
	};

	/** {@inheritDoc} */
	@Override
	public List<Event> getAllEventsOrderedByName() {
		return super.entityManager.createQuery("select event from Event event "
											 + "order by event.title ASC", Event.class)
								  .getResultList();
	}

	/** {@inheritDoc} */
	@Override
	public List<Event> getAllNonCurrentEvents() {

		return super.entityManager.createQuery("select event from Event event "
				+ "where event.current = :current "
				+ "order by event.title ASC", Event.class)
								  .setParameter("current", false)
								  .setHint("org.hibernate.cacheable", true)
								  .getResultList();
	}

	/** {@inheritDoc} */
	@Override
	public Event getCurrentEvent() {

		final Event currentEvent;

		try {
			currentEvent = super.entityManager.createQuery("select event from Event event "
					+ "where event.current = :current "
					+ "order by event.title ASC", Event.class)
									  .setParameter("current", true)
									  .setHint("org.hibernate.cacheable", true)
									  .getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}

		return currentEvent;
	}

	/** {@inheritDoc} */
	@Override
	public Event getByEventKey(String eventKey) {
		return super.entityManager.createQuery("select event from Event event where event.eventKey = :eventKey", Event.class)
								  .setParameter("eventKey", eventKey)
								  .getSingleResult();
	}

}
