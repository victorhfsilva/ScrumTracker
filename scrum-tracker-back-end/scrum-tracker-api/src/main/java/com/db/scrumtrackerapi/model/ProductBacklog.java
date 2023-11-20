package com.db.scrumtrackerapi.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * Represents the product backlog in the Scrum Tracker system.
 * A product backlog contains information about a specific product and
 * the list of items associated with that product.
 */
@Entity
@Table(name = "product_backlogs")
public class ProductBacklog extends BaseEntity {
    
    /**
     * The list of backlog items associated with this product.
     */
    @OneToMany(mappedBy = "productBacklog")
    private List<ItemBacklog> itensBacklog;

    /**
     * The product associated with this product backlog.
     */
    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    /**
     * Protected empty constructor for use by persistence frameworks.
     */
    protected ProductBacklog() {
    }

    /**
     * Constructor to create a new product backlog with a product and a list of items.
     * 
     * @param product The product associated with this backlog.
     * @param itensBacklog The list of items associated with this backlog.
     */
    public ProductBacklog(List<ItemBacklog> itensBacklog, Product product) {
        this.itensBacklog = itensBacklog;
        this.product = product;
        super.setActive(true);
        super.setTimestamp();
    }

    /**
     * Gets the product associated with this backlog.
     * 
     * @return The associated product.
     */
    public Product getProduct() {
        return this.product;
    }

    /**
     * Sets the product associated with this backlog.
     * 
     * @param product The product to be associated.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Gets the list of items associated with this backlog.
     * 
     * @return The list of associated items.
     */
    public List<ItemBacklog> getItensBacklog() {
        return this.itensBacklog;
    }

    /**
     * Sets the list of items associated with this backlog.
     * 
     * @param items The list of items to be associated.
     */
    public void setItensBacklog(List<ItemBacklog> items) {
        this.itensBacklog = items;
    }

    /**
     * Overridden to compare equality based on the product and the list of items.
     * 
     * @param o The object to be compared.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductBacklog)) {
            return false;
        }
        ProductBacklog productBacklog = (ProductBacklog) o;
        return Objects.equals(product, productBacklog.product) 
        && Objects.equals(Arrays.hashCode(itensBacklog.stream().map(ItemBacklog::getId).toArray()), Arrays.hashCode(productBacklog.getItensBacklog().stream().map(ItemBacklog::getId).toArray()));
    }

    /**
     * Overridden to generate a hash code based on the product and the list of items.
     * 
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(product, Arrays.hashCode(itensBacklog.stream().map(ItemBacklog::getId).toArray()));
    }

    /**
     * Overridden to generate a string representation of the object.
     * 
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "{" +
            " productId='" + getProduct().getId() + "'" +
            ", itemBacklogIds='" + getItensBacklog().stream().map(i -> i.getId()) + "'" +
            "}";
    }

}
