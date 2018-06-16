package com.redeemer.root.museum_mobile.model;

import java.util.ArrayList;
import java.util.List;

public class CurrencyExchange {

    private String base;
    private String date;

    private RatesEntity rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public RatesEntity getRates() {
        return rates;
    }

    public void setRates(RatesEntity rates) {
        this.rates = rates;
    }

    public List<Currency> getCurrencyList(){
        List<Currency> currencyList = new ArrayList<>();

        currencyList.add(new Currency("USD", rates.getUSD()));
        currencyList.add(new Currency("GBP", rates.getGBP()));

        return  currencyList;


    }

    public static class RatesEntity {

        private double GBP;
        private double USD;

        public double getGBP() {
            return GBP;
        }

        public void setGBP(double GBP) {
            this.GBP = GBP;
        }

        public double getUSD() {
            return USD;
        }

        public void setUSD(double USD) {
            this.USD = USD;
        }
    }
}
