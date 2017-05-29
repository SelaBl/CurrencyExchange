package com.salit.currencyexchange;

import java.util.Date;
import java.util.List;

/**
 * Created by salit on 19/04/2017.
 */

    public class CurrencyExchangeData {

    List<Currency> currencies;
    Date lastUpdate;

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "CurrencyExchangeData{" +
                "currencies=" + currencies +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
