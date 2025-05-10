package lk.jiat.web.ejb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import lk.jiat.web.core.remote.CurrencyConverter;

@Stateless
public class CurrencyConverterBean implements CurrencyConverter {


    public CurrencyConverterBean() {
        System.out.println("CurrencyConverterBean Constructor called: " + this.hashCode());
    }

    @PostConstruct
    public void init(){
        System.out.println("CurrencyConverterBean Created");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("CurrencyConverterBean Destroyed");
    }

    private static final double USD_TO_LKR_RATE = 300.00;

    @Override
    public double convertToLKR(double amount) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return amount * USD_TO_LKR_RATE;
    }

    @Override
    public double convertToUSD(double amount) {
        return amount / USD_TO_LKR_RATE;
    }
}
