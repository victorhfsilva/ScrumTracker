package com.db.scrumtrackerapi.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.db.scrumtrackerapi.model.enums.Priority;
import com.db.scrumtrackerapi.model.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Represents an item in the Scrum Tracker system's backlog.
 * An item backlog contains information about the status, priority, name, criteria for acceptance,
 * effort estimation, associated sprint, and a description.
 */
@Entity
@Table(name = "itens_backlog")
public class ItemBacklog extends BaseEntity {

    /**
     * The status of the backlog item.
     */
    @Column(name = "status", nullable = false)
    private Status status;

    /**
     * The priority of the backlog item.
     */
    @Column(name = "priority", nullable = false)
    private Priority priority;

    /**
     * The name of the backlog item.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * The criteria for acceptance of the backlog item.
     */
    @Column(name = "criteria_acceptance", nullable = false)
    private List<String> criteriaAcceptance;

    /**
     * The effort estimation for the backlog item.
     */
    @Column(name = "effort_estimation", nullable = false)
    private String effortEstimation;

    /**
     * List of sprints associated with the backlog item.
     */
    @ManyToMany(mappedBy = "itensBacklog")
    private List<Sprint> sprints;

    /**
     * The description of the backlog item.
     */
    @Column(name = "description", nullable = false)
    private String description;

    /**
     * The product backlog associated with the backlog item.
     */
    @ManyToOne
    @JoinColumn(name = "product_backlog_id")
    ProductBacklog productBacklog;

    /**
     * Updates the attributes of the current item backlog with the attributes of the provided item backlog.
     *
     * @param itemBacklog The item backlog with updated values.
     * @return The updated item backlog.
     */
    public ItemBacklog updateItemBacklog(ItemBacklog itemBacklog) {
        this.status = itemBacklog.getStatus();
        this.priority = itemBacklog.getPriority();
        this.name = itemBacklog.getName();
        this.criteriaAcceptance = itemBacklog.getCriteriaAcceptance();
        this.effortEstimation = itemBacklog.getEffortEstimation();
        this.sprints = itemBacklog.getSprints();
        this.description = itemBacklog.getDescription();
        return this;
    }

    /**
     * Protected empty constructor for use by persistence frameworks.
     */
    protected ItemBacklog() {
    }

    /**
     * Creates a new item backlog with specified attributes.
     *
     * @param status            The status of the backlog item.
     * @param priority          The priority of the backlog item.
     * @param name              The name of the backlog item.
     * @param criteriaAcceptance The criteria for acceptance of the backlog item.
     * @param effortEstimation  The effort estimation for the backlog item.
     * @param sprint            The sprint associated with the backlog item.
     * @param description       The description of the backlog item.
     * @param productBacklog    The product backlog associated with the backlog item.
     */
    public ItemBacklog(Status status, Priority priority, String name, List<String> criteriaAcceptance, String effortEstimation, List<Sprint> sprint, String description, ProductBacklog productBacklog) {
        this.status = status;
        this.priority = priority;
        this.name = name;
        this.criteriaAcceptance = criteriaAcceptance;
        this.effortEstimation = effortEstimation;
        this.sprints = sprint;
        this.description = description;
        this.productBacklog = productBacklog;
        super.setActive(true);
        super.setTimestamp();
    }

    /**
     * Gets the status of the backlog item.
     *
     * @return The status of the backlog item.
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Sets the status of the backlog item.
     *
     * @param status The status to set.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the priority of the backlog item.
     *
     * @return The priority of the backlog item.
     */
    public Priority getPriority() {
        return this.priority;
    }

    /**
     * Sets the priority of the backlog item.
     *
     * @param priority The priority to set.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets the name of the backlog item.
     *
     * @return The name of the backlog item.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the backlog item.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the criteria for acceptance of the backlog item.
     *
     * @return The criteria for acceptance of the backlog item.
     */
    public List<String> getCriteriaAcceptance() {
        return this.criteriaAcceptance;
    }

    /**
     * Sets the criteria for acceptance of the backlog item.
     *
     * @param criteriaAcceptance The criteria for acceptance to set.
     */
    public void setCriteriaAcceptance(List<String> criteriaAcceptance) {
        this.criteriaAcceptance = criteriaAcceptance;
    }

    /**
     * Gets the effort estimation for the backlog item.
     *
     * @return The effort estimation for the backlog item.
     */
    public String getEffortEstimation() {
        return this.effortEstimation;
    }

    /**
     * Sets the effort estimation for the backlog item.
     *
     * @param effortEstimation The effort estimation to set.
     */
    public void setEffortEstimation(String effortEstimation) {
        this.effortEstimation = effortEstimation;
    }

    /**
     * Gets the sprint associated with the backlog item.
     *
     * @return The sprint associated with the backlog item.
     */
    public List<Sprint> getSprints() {
        return this.sprints;
    }

    /**
     * Sets the sprint associated with the backlog item.
     *
     * @param sprint The sprint to set.
     */
    public void setSprints(List<Sprint> sprint) {
        this.sprints = sprint;
    }

    /**
     * Gets the description of the backlog item.
     *
     * @return The description of the backlog item.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the backlog item.
     *
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the product backlog associated with the backlog item.
     *
     * @return The product backlog associated with the backlog item.
     */
    public ProductBacklog getProductBacklog() {
        return this.productBacklog;
    }

    /**
     * Sets the product backlog associated with the backlog item.
     *
     * @param productBacklog The product backlog to set.
     */
    public void setProductBacklog(ProductBacklog productBacklog) {
        this.productBacklog = productBacklog;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o The reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ItemBacklog)) return false;
        ItemBacklog itemBacklog = (ItemBacklog) o;
        return Objects.equals(status, itemBacklog.status) &&
                Objects.equals(priority, itemBacklog.priority) &&
                Objects.equals(name, itemBacklog.name) &&
                Objects.equals(criteriaAcceptance, itemBacklog.criteriaAcceptance) &&
                Objects.equals(effortEstimation, itemBacklog.effortEstimation) &&
                Objects.equals(Arrays.hashCode(sprints.stream().map(i -> i.getId()).toArray()), Arrays.hashCode(itemBacklog.sprints.stream().map(i -> i.getId()).toArray())) &&
                Objects.equals(description, itemBacklog.description) &&
                Objects.equals(productBacklog.getId(), itemBacklog.productBacklog.getId());
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, priority, name, criteriaAcceptance, effortEstimation, sprints, description, productBacklog.getId());
    }

    /**
     * Returns a string representation of the object.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "{" +
                " status='" + getStatus() + "'" +
                ", priority='" + getPriority() + "'" +
                ", name='" + getName() + "'" +
                ", criteriaAcceptance='" + getCriteriaAcceptance() + "'" +
                ", effortEstimation='" + getEffortEstimation() + "'" +
                ", sprintIds='" + getSprints().stream().map(i -> i.getId()) + "'" +
                ", description='" + getDescription() + "'" +
                ", productBacklogId='" + getProductBacklog().getId() + "'" +
                "}";
    }
}
