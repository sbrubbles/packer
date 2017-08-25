package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/**
 * Abstract superclass for types containing weight and cost parameters.
 * <p>
 * It also provides an implementation of {@link Comparable}, taking into account the highest cost, then the lowest weight.
 */
public abstract class Parameterized implements Comparable<Parameterized> {
    private final static Comparator<Parameterized> COMPARATOR =
            Comparator.comparing(Parameterized::getCost)
                    .reversed()
                    .thenComparing(Parameterized::getWeight);
    private static final Comparator<BigDecimal> BIG_DECIMAL_COMPARATOR = Comparator.naturalOrder();

    private BigDecimal weight;
    private BigDecimal cost;

    public Parameterized(BigDecimal weight, BigDecimal cost) {
        this.weight = weight;
        this.cost = cost;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    /**
     * Comparing by highest cost, then by lowest weight
     *
     * @param o the object to be compared with the instance
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Parameterized o) {
        return COMPARATOR.compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameterized that = (Parameterized) o;
        return BIG_DECIMAL_COMPARATOR.compare(weight, that.weight) == 0 &&
                BIG_DECIMAL_COMPARATOR.compare(cost, that.cost) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, cost);
    }
}
