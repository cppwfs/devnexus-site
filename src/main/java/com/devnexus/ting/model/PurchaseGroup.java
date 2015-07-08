package com.devnexus.ting.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A purchase group is a collection if Items of which only one may be purchased.
 *
 * @author summers
 */
@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class PurchaseGroup extends BaseModelObject {

    public static PurchaseGroup fromId(Long valueOf) {
        PurchaseGroup group = new PurchaseGroup();
        group.setId(valueOf);
        return group;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "purchaseGroup", targetEntity = PurchaseItem.class)
    private Set<PurchaseItem> items = new HashSet<>();

    @ManyToOne
    //@JoinColumn(name="EVENT_ID")
    @NotNull
    @XmlTransient
    private Event event;

    @NotEmpty
    @Size(max = 255)
    protected String label;

    @ManyToOne
    @XmlTransient
    protected EventSignup eventSignup;
    
    public Set<PurchaseItem> getItems() {
        return items;
    }

    public void setItems(Set<PurchaseItem> items) {
        this.items = items;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public EventSignup getEventSignup() {
        return eventSignup;
    }

    public void setEventSignup(EventSignup eventSignup) {
        this.eventSignup = eventSignup;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    
    
    
}
