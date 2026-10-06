/*
 * TCSS 305 - Programming Practicum
 * Assignment 3 - Bookstore
 * 
 */

package model;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a shopping cart that stores item orders.
 * Supports membership discount and total cost calculation.
 * Author: Sudip Chaudhary
 * UWNetID: sudipc
 */
public class Cart {

    /** Collection of item orders. */
    private final List<ItemOrder> myOrders;

    /** Whether the customer has a membership. */
    private boolean myMembership;

    /**
     * Constructs an empty shopping cart.
     */
    public Cart() {
        myOrders = new ArrayList<>();
        myMembership = false;
    }

    /**
     * Adds an order to the cart, replacing any previous order for the same item.
     * @param theOrder the order to add
     * @throws NullPointerException if order is null
     */
    public void add(final ItemOrder theOrder) {
        if (theOrder == null) {
            throw new NullPointerException("Order cannot be null.");
        }
        final Item newItem = theOrder.getItem();
        for (int i = 0; i < myOrders.size(); i++) {
            if (myOrders.get(i).getItem().equals(newItem)) {
                myOrders.set(i, theOrder);
                return;
            }
        }
        myOrders.add(theOrder);
    }

    /**
     * Sets whether this cart has a membership discount.
     * @param theValue true if the customer has membership
     */
    public void setMembership(final boolean theValue) {
        myMembership = theValue;
    }

    /**
     * Calculates the total price of all items in the cart.
     * @return total cost as BigDecimal rounded to two decimal places
     */
    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemOrder order : myOrders) {
            total = total.add(order.calculateTotal(myMembership));
        }
        return total.setScale(2, RoundingMode.HALF_EVEN);
    }

    /**
     * Removes all orders from the cart.
     */
    public void clear() {
        myOrders.clear();
    }

    /**
     * Returns the number of item orders in the cart.
     * @return number of orders
     */
    public int getCartSize() {
        return myOrders.size();
    }

    /**
     * Returns a string summary of all orders.
     * @return cart contents as string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cart contents:\n");
        for (ItemOrder order : myOrders) {
            sb.append(order).append('\n');
        }
        sb.append("Membership: ").append(myMembership)
          .append("\nTotal: ").append(calculateTotal());
        return sb.toString();
    }
}
