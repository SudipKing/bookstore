/*
 * TCSS 305 - Programming Practicum
 * Assignment 3 - Bookstore
 */

package model;
import java.util.Objects;

/**
 * Represents an order for a particular item and quantity.
 * Provides accessors for both item and quantity.
 * @author Sudip Chaudhary
 * @version Autumn 2025
 */
public class ItemOrder {

    /** The item being ordered. */
    private final Item myItem;

    /** The number of units ordered. */
    private final int myQuantity;

    /**
     * Constructs an item order with the given item and quantity.
     * @param theItem the item being ordered
     * @param theQuantity the quantity requested
     * @throws IllegalArgumentException if quantity < 0
     * @throws NullPointerException if item is null
     */
    public ItemOrder(final Item theItem, final int theQuantity) {
        if (theItem == null) {
            throw new NullPointerException("Item cannot be null.");
        }
        if (theQuantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative.");
        }
        myItem = theItem;
        myQuantity = theQuantity;
    }

    /**
     * Returns the item being ordered.
     * @return the item
     */
    public Item getItem() {
        return myItem;
    }

    /**
     * Returns the quantity ordered.
     * @return the quantity
     */
    public int getQuantity() {
        return myQuantity;
    }

    /**
     * Returns a formatted string representation of this order.
     * @return formatted string
     */
    @Override
    public String toString() {
        return myQuantity + " × " + myItem.toString();
    }

    /**
     * Calculates total cost for this order depending on membership.
     * @param hasMembership whether membership discount applies
     * @return total cost as BigDecimal
     */
    public java.math.BigDecimal calculateTotal(final boolean hasMembership) {
        java.math.BigDecimal total;
        final java.math.BigDecimal single = myItem.getPrice();
        if (hasMembership && myItem.isBulk()) {
            int bulkQty = myItem.getBulkQuantity();
            java.math.BigDecimal bulkPrice = myItem.getBulkPrice();
            int bulkGroups = myQuantity / bulkQty;
            int remainder = myQuantity % bulkQty;
            total = bulkPrice.multiply(java.math.BigDecimal.valueOf(bulkGroups))
                    .add(single.multiply(java.math.BigDecimal.valueOf(remainder)));
        } else {
            total = single.multiply(java.math.BigDecimal.valueOf(myQuantity));
        }
        return total;
    }
}
