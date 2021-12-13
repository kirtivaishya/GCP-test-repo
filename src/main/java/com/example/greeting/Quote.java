package com.example.greeting;

import java.util.Date;
import java.sql.Timestamp;

public class Quote {
    String quote ;
    String createdBy;
    Timestamp date;

    public Quote(String quote, String createdBy) {
        this.quote = quote;
        this.createdBy = createdBy;
        this.date = new Timestamp(new Date().getTime());
    }

    public Quote(String quote) {
        this.quote = quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return this.quote;
    }
    @Override
    public String toString() {
        return "Quote{" +
                "Quote='" +quote + '\'' +
                '}';
    }


}
