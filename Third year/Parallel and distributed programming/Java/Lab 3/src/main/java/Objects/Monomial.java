package Objects;

import java.util.Objects;

public class Monomial {
    private int coefficient;
    private int exponent;

    public Monomial() {
        coefficient = 0;
        exponent = 0;
    }

    public Monomial(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    int getCoefficient() {
        return coefficient;
    }


    void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monomial monomial = (Monomial) o;
        return coefficient == monomial.coefficient &&
                exponent == monomial.exponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, exponent);
    }

    @Override
    public String toString() {
        return coefficient + "," + exponent;
    }
}