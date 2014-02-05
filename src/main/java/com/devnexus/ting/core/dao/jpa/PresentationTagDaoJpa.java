/*
 * Copyright 2002-2014 the original author or authors.
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.devnexus.ting.core.dao.PresentationTagDao;
import com.devnexus.ting.core.model.PresentationTag;

/**
 *
 * @author Gunnar Hillert
 *
 */
@Repository("presentationTagDao")
public class PresentationTagDaoJpa extends GenericDaoJpa< PresentationTag, Long>
						   implements PresentationTagDao {

	/** Constructor. */
	public PresentationTagDaoJpa() {
		super(PresentationTag.class);
	}

	@Override
	public List<PresentationTag> getPresentationsTagsForPresentation(Long presentationId) {
		return super.entityManager
		.createQuery("select p from PresentationTag p "
				   + "    join p.presentations e "
				   + "where e.id = :presentationId "
				   + "order by p.title ASC", PresentationTag.class)
		.setParameter("presentationId", presentationId)
		.getResultList();
	}

	@Override
	public PresentationTag getPresentationTag(String tagName) {

		try {
			return super.entityManager
				.createQuery("select pt from PresentationTag pt "
						   + "where pt.name = :tagName", PresentationTag.class)
						.setParameter("tagName", tagName)
						.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Map<PresentationTag, Long> getPresentationTagCountForEvent(Long eventId) {

		List<Object[]> list = super.entityManager
		.createQuery("select pt, count(p.id) as cc from PresentationTag pt join pt.presentations p"
				+ " join p.event e where e.id = :eventId"
				+ " group by pt.id order by cc DESC, pt.name ASC", Object[].class)
		.setParameter("eventId", eventId)
		.getResultList();

		Map<PresentationTag, Long> results = new LinkedHashMap<>();

		for (Object[] o : list) {
			results.put((PresentationTag) o[0], (Long) o[1]);
		}

		return results;
	}

}
