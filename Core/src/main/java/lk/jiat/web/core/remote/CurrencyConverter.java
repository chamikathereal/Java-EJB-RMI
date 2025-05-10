package lk.jiat.web.core.remote;

import jakarta.ejb.Remote;

@Remote
public interface CurrencyConverter {
    public double convertToLKR(double amount);
    public double convertToUSD(double amount);
}
