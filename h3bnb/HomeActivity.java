package com.example.h3bnb;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.h3bnb.fragments.DatePickerFragment;
import com.example.h3bnb.models.BienModel;
import com.example.h3bnb.models.CountryModel;
import com.example.h3bnb.online.APIClient;
import com.example.h3bnb.online.APIInterface;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends Activity implements AdapterView.OnItemSelectedListener{

    private EditText filterCountry, filterBeds, filterType, filterCity, prixMin, prixMax;
    private TextView textCountry;
    private Spinner spinnerCountry, spinnerCity;
    private Button buttonDateArrivee, buttonDateDepart, buttonSearch, buttonMenu;
    private ArrayList<String> countryList;
    private String[] countries = {"Netherlands", "Spain", "France"};
    private String[] cities = {"Barcelona", "Amsterdam", "Paris"};
    private Boolean keepOn;
    private String citySelected, countrySelected;
    ArrayAdapter<String> adapterCities, adapterCountries;
    private ArrayList<BienModel> selectedBiens;
    private int idUser;

    private int lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth, targetCityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent iin= getIntent();
        Bundle extras = iin.getExtras();

        idUser = extras.getInt("idUser");

        getCountryList();

        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerCity = findViewById(R.id.spinnerCity);
        filterType = findViewById(R.id.filterType);
        filterBeds = findViewById(R.id.filterBeds);
        buttonMenu = findViewById(R.id.buttonMenu);
        buttonDateArrivee = findViewById(R.id.buttonDateArrivee);
        buttonDateDepart = findViewById(R.id.buttonDateDepart);
        prixMin = findViewById(R.id.priceMin);
        prixMax = findViewById(R.id.priceMax);
        buttonSearch = findViewById(R.id.buttonSearch);

        spinnerCity.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapterCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapterCountries);

        ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        adapterCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapterCities);

        this.buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent menu = new Intent(HomeActivity.this, MenuActivity.class);
                menu.putExtra("idUser", idUser);
                HomeActivity.this.startActivity(menu);
            }
        });



        this.buttonDateArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate(buttonDateArrivee);
            }
        });

        this.buttonDateArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSelectDate(buttonDateDepart);
            }
        });

        this.buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSearch();
            }
        });

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        this.lastSelectedYear = c.get(Calendar.YEAR);
        this.lastSelectedMonth = c.get(Calendar.MONTH);
        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);

        buttonDateArrivee.setText(lastSelectedYear + "-" + (lastSelectedMonth) + "-" + lastSelectedDayOfMonth);
        buttonDateDepart.setText(lastSelectedYear + "-" + (lastSelectedMonth + 1) + "-" + lastSelectedDayOfMonth);
    }

    private void buttonSelectDate(Button buttonDate) {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {

                buttonDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                lastSelectedYear = year;
                lastSelectedMonth = monthOfYear;
                lastSelectedDayOfMonth = dayOfMonth;
            }
        };

        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(this,
                dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);

        datePickerDialog.show();
    }

    private void buttonSearch() {

        //Editable country = this.filterCountry.getEditableText();
        //Editable city = this.filterCity.getEditableText();
        //Editable type = this.filterType.getEditableText();
        //Editable prixMin = this.prixMin.getEditableText();
        //Editable prixMax = this.prixMax.getEditableText();
        // Check if fields are good

        Boolean fieldsGood = true;

       /* if (country.equals("")){fieldsGood = false;}
        if (city.equals("")){fieldsGood = false;}
        if (type.equals("")){fieldsGood = false;}
        if (country.equals("")){fieldsGood = false;}
        if (prixMin.equals("")){fieldsGood = false;}
        if (prixMax.equals("")){fieldsGood = false;}*/

        if (fieldsGood){



            int idBarca = 1;
            int idParis = 4;
            int idDam = 2;

            if (citySelected == "Barcelona" ){targetCityId = idBarca;}
            if (citySelected == "Amsterdam" ){targetCityId = idDam;}
            if (citySelected == "Paris" ){targetCityId = idParis;}

            Intent recherche = new Intent(HomeActivity.this, MapActivity.class);
            recherche.putExtra("idCity", targetCityId);
            recherche.putExtra("idUser", idUser);
            HomeActivity.this.startActivity(recherche);


        }

    }

    private void getCountryList(){

        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<List<CountryModel>> userCall = service.getCountries();

        userCall.enqueue(new Callback<List<CountryModel>> () {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                int rep = response.body().size();
                if (rep > 0) {

                    for (int i = 0; i < rep; i++) {
                        CountryModel country = response.body().get(i);
                        //countryList.add(country.getName());
                    }

                }
            }
            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {
                Log.d("register", t.getMessage());
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        citySelected = cities[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
