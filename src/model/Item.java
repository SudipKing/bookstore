/*
 * TCSS 305 - Programming Practicum
 * Assignment 3 - Bookstore
 *
 */

package model;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a store item with optional bulk pricing.
 * Handles single-unit and bulk purchases.
 * Extra Credit: Implements Comparable<Item> and provides orderByPrice comparator.
 * author: Sudip Chaudhary
 * @version Autumn 2025
 */
public class Item implements Comparable<Item> {

    /** Formatter for currency display. */
    private static final NumberFormat NF = NumberFormat.getCurrencyInstance(Locale.US);

    /** Item name. */
    private final String myName;

    /** Single unit price. */
    private final BigDecimal myPrice;

    /** Bulk quantity threshold. */
    private final int myBulkQuantity;

    /** Bulk price for the bulk quantity. */
    private final BigDecimal myBulkPrice;

    /**
     * Constructs an item with only single pricing (no bulk discount).
     * @param theName the name of the item
     * @param thePrice the price per single item
     * @throws IllegalArgumentException if price < 0
     * @throws NullPointerException if name or price is null
     */
    public Item(final String theName, final BigDecimal thePrice) {
        this(theName, thePrice, 0, BigDecimal.ZERO);
    }

    /**
     * Constructs an item with both single and bulk pricing.
     * @param theName the name of the item
     * @param thePrice the single item price
     * @param theBulkQuantity the quantity needed for bulk pricing
     * @param theBulkPrice the total bulk price
     * @throws IllegalArgumentException if any price < 0 or bulk quantity < 0
     * @throws NullPointerException if any argument is null
     */
    public Item(final String theName, final BigDecimal thePrice,
                final int theBulkQuantity, final BigDecimal theBulkPrice) {
        if (theName == null || thePrice == null || theBulkPrice == null) {
            throw new NullPointerException("Arguments cannot be null.");
        }
        if (theName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (thePrice.compareTo(BigDecimal.ZERO) < 0
                || theBulkPrice.compareTo(BigDecimal.ZERO) < 0
                || theBulkQuantity < 0) {
            throw new IllegalArgumentException("Prices and quantity must be non-negative.");
        }

        myName = theName;
        myPrice = thePrice;
        myBulkQuantity = theBulkQuantity;
        myBulkPrice = theBulkPrice;
    }

    /**
     * Returns the single-item price.
     * @return the price per unit
     */
    public BigDecimal getPrice() {
        return myPrice;
    }

    /**
     * Returns the bulk quantity threshold.
     * @return the bulk quantity
     */
    public int getBulkQuantity() {
        return myBulkQuantity;
    }

    /**
     * Returns the bulk price.
     * @return the bulk price
     */
    public BigDecimal getBulkPrice() {
        return myBulkPrice;
    }

    /**
     * Checks if this item has bulk pricing.
     * @return true if item supports bulk pricing
     */
    public boolean isBulk() {
        return myBulkQuantity > 0;
    }

    /**
     * Returns a formatted string representation of this item.
     * @return the formatted string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(myName);
        sb.append(", ").append(NF.format(myPrice));
        if (isBulk()) {
            sb.append(" (").append(myBulkQuantity)
              .append(" for ").append(NF.format(myBulkPrice)).append(')');
        }
        return sb.toString();
    }

    /**
     * Compares this item with another for equality.
     * @param theOther the other object
     * @return true if both items are equal in all fields
     */
    @Override
    public boolean equals(final Object theOther) {
        if (this == theOther) {
            return true;
        }
        if (!(theOther instanceof Item)) {
            return false;
        }
        final Item other = (Item) theOther;
        return Objects.equals(myName, other.myName)
                && Objects.equals(myPrice, other.myPrice)
                && myBulkQuantity == other.myBulkQuantity
                && Objects.equals(myBulkPrice, other.myBulkPrice);
    }

    /**
     * Returns a hash code consistent with equals().
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(myName, myPrice, myBulkQuantity, myBulkPrice);
    }

    /**
     * Compares items by name, then price.
     * (Extra Credit)
     * @param theOther the item to compare to
     * @return comparison result
     */
    @Override
    public int compareTo(final Item theOther) {
        int result = myName.compareTo(theOther.myName);
        if (result == 0) {
            result = myPrice.compareTo(theOther.myPrice);
        }
        return result;
    }

    /**
     * Returns a comparator that orders items by price (ascending).
     * (Extra Credit)
     * @return comparator by price
     */
    public static java.util.Comparator<Item> orderByPrice() {
        return java.util.Comparator.comparing(Item::getPrice);
    }
}
