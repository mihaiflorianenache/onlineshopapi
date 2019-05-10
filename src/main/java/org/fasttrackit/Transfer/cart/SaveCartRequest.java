package org.fasttrackit.Transfer.cart;

import org.fasttrackit.Transfer.Customer.CustomerIdentifier;
import org.fasttrackit.Transfer.Product.ProductIdentifier;

import java.util.Set;

public class SaveCartRequest {

    private long customerId;
    private Set<Long> productIds;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public Set<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(Set<Long> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "SaveCartRequest{" +
                "customerId=" + customerId +
                ", productIds=" + productIds +
                '}';
    }
}
