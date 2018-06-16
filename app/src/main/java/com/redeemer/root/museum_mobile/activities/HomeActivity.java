package com.redeemer.root.museum_mobile.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.redeemer.root.museum_mobile.R;
import com.redeemer.root.museum_mobile.adapters.CurrencyAdapter;
import com.redeemer.root.museum_mobile.model.Currency;
import com.redeemer.root.museum_mobile.model.CurrencyExchange;
import com.redeemer.root.museum_mobile.services.CurrencyExchangeService;
import com.redeemer.root.museum_mobile.services.CurrencyItemClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Callback<CurrencyExchange>, CurrencyItemClickListener {


    //When this Card is clicked dialog will pop up to in orderto change the price of adult
    CardView cardView;

    //This listview will containt supported currencies and their rates
    private ListView CurrencylistView;


    //hardcode price price
    double   standardPrice = 290.90;

    //student price will be calculated by subracting 30 % of standardPrice
    double studenPrice = 0.0;


    //Two textviews to display the priceces for students and andulas
    TextView adultPriceTextView;
    TextView studentPriceTextview;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        cardView = (CardView) findViewById(R.id.cardchangeprice);
        //on create activity we  initialize all view componets

        //View initialisation Section
        adultPriceTextView =(TextView) findViewById(R.id.adultPrice);
        studentPriceTextview =(TextView) findViewById(R.id.studentPrice);
        CurrencylistView = (ListView) findViewById(R.id.currecylistview);


        //because we have hardcoded our price here we have to culculate the student price
        // by the formula student ticket = standardPrice - 30/100 * standardPrice
        //when ever the HomeActivity is created theis will be calculated
        studenPrice = standardPrice - standardPrice * 30/100;



        //set the adultPriceTextView text with the  standard price
        adultPriceTextView.setText("USD: " + String.valueOf(standardPrice));

         //set the studentPricetextview Text with the calculated studenPrice
        studentPriceTextview.setText("USD: " + String.valueOf(studenPrice));


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] item ={"Change Adult Price"};

                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                builder.setTitle("Choose Action");

                builder.setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        if(i == 0){

                            OpenChangePriceDialog(HomeActivity.this);
                        }

                    }
                });


            }
        });


    }

    private void OpenChangePriceDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_price_dialog);
        dialog.setTitle("Update Painting");


        final EditText painter_name  = (EditText) dialog.findViewById(R.id.changePriceEd);
        final Button paint_title= (Button) dialog.findViewById(R.id.btnChangeprice);

        int width = (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.95);

        int height = (int)(activity.getResources().getDisplayMetrics().widthPixels * 0.75);


        dialog.getWindow().setLayout(width,height);

        dialog.show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    // We then override onstart method so that have the activity is create the it stard thecurrency data is loaded
    @Override
    public void onStart() {
        super.onStart();
        AccessExchangeRatesData();
    }

    //AccessExchangeRatesData


    //This method pulls exchange rate data from an API
    //The Api used is fixer.io
    private void AccessExchangeRatesData() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//3c2b7194956b7610b70e8f6272a17620
        CurrencyExchangeService service = retrofit.create(CurrencyExchangeService.class);
        Call<CurrencyExchange> call = service.loadCurrencyExchange();
        call.enqueue(this);

        //http://www.apilayer.net/api/live?access_key=02ccd6f11df9092b9757368bc5da2a2d&format=1

        //http://data.fixer.io/api/latest?access_key=3c2b7194956b7610b70e8f6272a17620
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCurrencyItemClick(Currency c) {


        //when the user clicks an item on a list view we check the currency and change the ticket price according to the rates

        if(c.getName().equals("USD")){

            adultPriceTextView.setText("USD: " + String.valueOf(standardPrice));

            studentPriceTextview .setText("USD: " + String.valueOf(studenPrice));


        }else if(c.getName().equals("GBP")) {


            adultPriceTextView.setText("GBP: "+ String.valueOf(standardPrice * c.getRate()));
            studentPriceTextview.setText("GBP: "+ String.valueOf(studenPrice * c.getRate()));

        }


    }

    @Override
    public void onResponse(Call<CurrencyExchange> call, Response<CurrencyExchange> response) {

        CurrencyExchange currencyExchange = response.body();
        CurrencylistView.setAdapter(new CurrencyAdapter(getApplicationContext(), currencyExchange.getCurrencyList(), this));

    }

    @Override
    public void onFailure(Call<CurrencyExchange> call, Throwable t) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.open_paintings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.open:
              Intent  intent = new Intent(getApplicationContext(), GalleryCollection.class);
                startActivity(intent);
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
