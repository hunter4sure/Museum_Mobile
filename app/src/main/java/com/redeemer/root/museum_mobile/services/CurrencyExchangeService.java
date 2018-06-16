package com.redeemer.root.museum_mobile.services;




import com.redeemer.root.museum_mobile.model.CurrencyExchange;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CurrencyExchangeService {
    @GET("latest?access_key=3c2b7194956b7610b70e8f6272a17620")
    Call<CurrencyExchange> loadCurrencyExchange();
}
