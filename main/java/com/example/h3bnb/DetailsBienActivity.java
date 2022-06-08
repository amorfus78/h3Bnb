package com.example.h3bnb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Base64;

import androidx.annotation.RequiresApi;
import androidx.core.util.Pair;
import androidx.fragment.app.FragmentActivity;

import com.example.h3bnb.models.BienModel;
import com.example.h3bnb.online.APIClient;
import com.example.h3bnb.online.APIInterface;
import com.example.h3bnb.online.MSG;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsBienActivity extends FragmentActivity {

    private int idBien, idUser;
    private BienModel selectedBien;
    private TextView title, city, price, type, description, mShowSelectedDateText;
    private String cityName, datesSelected;
    private ImageView image;
    private Button mPickDateButton, reserverButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_biens);
        Intent iin= getIntent();
        Bundle extras = iin.getExtras();
        idUser = extras.getInt("idUser");

        title = findViewById(R.id.title);
        city = findViewById(R.id.city);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);
        reserverButton = findViewById(R.id.reserver_button);


        idBien = extras.getInt("idBien");
        cityName = extras.getString("cityname");
        city.setText(cityName);
        getBien(idBien);
        showDateSelection();
        this.reserverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserver();
            }
        });


    }

    private void getBien(int idBien) {
        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<List<BienModel>> bienCall = service.getBienById(idBien);

        bienCall.enqueue(new Callback<List<BienModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<BienModel>> call, Response<List<BienModel>> response) {
                selectedBien = response.body().get(0);
                price.setText(String.valueOf(selectedBien.getPrice()) + "€/jour");
                //price.setText("100");
                type.setText(selectedBien.getType());
                if (selectedBien.getDescription().length() > 150) {
                    description.setText(selectedBien.getDescription().substring(0, 150) + "...");
                } else {
                    description.setText(selectedBien.getDescription());
                }
                // Sending side
                // byte[] data = text.getBytes("UTF-8");
                // String base64 = Base64.encodeToString(data, Base64.DEFAULT);

                // Receiving side
                byte[] bytes=Base64.decode(selectedBien.getImages(),Base64.DEFAULT);
                // Initialize bitmap
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                // set bitmap on imageView
                image.setImageBitmap(bitmap);


            }

            @Override
            public void onFailure(Call<List<BienModel>> call, Throwable t) {

            }
        });

    }
    private void showDateSelection(){
        mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        CalendarConstraints.Builder calendarConstraintBuilder = new CalendarConstraints.Builder();
        calendarConstraintBuilder.setValidator(DateValidatorPointForward.now());


        // now create instance of the material date picker
        // builder make sure to add the "datePicker" which
        // is normal material date picker which is the first
        // type of the date picker in material design date
        // picker
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE");

        // now create the instance of the material date
        // picker
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        // handle select date button which opens the
        // material design date picker
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");

                    }
                });

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        // in the above statement, getHeaderT
                        Pair<Long, Long> trueSelection = (Pair<Long, Long>)selection;
                        Long valStart = trueSelection.first;
                        Long valEnd = trueSelection.second;
                        Date dateStart=new Date(valStart);
                        Date dateEnd = new Date(valEnd);
                        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
                        datesSelected = df2.format(dateStart) + " - " + df2.format(dateEnd);
                        // datesSelected = materialDatePicker.getSelection().toString();
                        // is the selected date preview from the
                        // dialog
                    }
                });
    }

    private void reserver(){
        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<MSG> contratCall = service.newContrat(" ", datesSelected,
                "", idUser,idBien );
        contratCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                //onSignupSuccess();
                int rep = response.body().getSuccess();

                if (rep > 0) {

                    Log.d("contrat", response.body().getSuccess() + "-->" + response.body().getMessage());
                    Intent connexion = new Intent(DetailsBienActivity.this, HomeActivity.class);
                    connexion.putExtra("idUser", idUser);
                    DetailsBienActivity.this.startActivity(connexion);

                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                Log.d("register", t.getMessage());
                //new CustomToast().Show_Toast(NewAccountActivity.this
                //        , linearLayout,
                //        t.getMessage());

            }
        });
    }

}
