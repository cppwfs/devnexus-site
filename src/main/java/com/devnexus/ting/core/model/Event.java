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
package com.devnexus.ting.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Cacheable()
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE) //, include="non-lazy"
@XmlAccessorType(value=XmlAccessType.FIELD)
public class Event extends BaseModelObject {

	private static final long serialVersionUID = -6321137845389122221L;

	@Column(unique=true)
	@NotEmpty
	@Size(max=25)
	private String eventKey;

	@NotEmpty
	@Column(length=255)
	private String title;

	private boolean current;

	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY, mappedBy="event")
	private Set<Presentation>presentations = new HashSet<Presentation>(0);

	@ManyToMany(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
	private Set<Speaker>speakers = new HashSet<Speaker>(0);

	@OneToMany(mappedBy="event", targetEntity=Room.class,
	fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Room>rooms = new HashSet<Room>(0);

	@Override
	public String toString() {
		return String.valueOf(id);
	}


	/**
	 * @param id
	 */
	public Event() {
		super();
	}

	public Event(Long id, String eventKey, String title, boolean current) {
		super();
		this.id = id;
		this.eventKey = eventKey;
		this.title = title;
		this.current = current;
	}

	/**
	 * @param id
	 */
	public Event(Long id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the eventKey
	 */
	public String getEventKey() {
		return eventKey;
	}

	/**
	 * @param eventKey the eventKey to set
	 */
	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	/**
	 * @return the presentations
	 */
	public Set<Presentation> getPresentations() {
		return presentations;
	}

	/**
	 * @param presentations the presentations to set
	 */
	public void setPresentations(Set<Presentation> presentations) {
		this.presentations = presentations;
	}

	/**
	 * @return the speakers
	 */
	public Set<Speaker> getSpeakers() {
		return speakers;
	}

	/**
	 * @param speakers the speakers to set
	 */
	public void setSpeakers(Set<Speaker> speakers) {
		this.speakers = speakers;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}


	/**
	 * @return the current
	 */
	public boolean isCurrent() {
		return current;
	}

	public Set<Room> getRooms() {
		return rooms;
	}


	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

}
