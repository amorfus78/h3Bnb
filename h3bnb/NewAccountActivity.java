package com.example.h3bnb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.h3bnb.online.APIClient;


import com.example.h3bnb.online.APIInterface;
import com.example.h3bnb.online.MSG;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewAccountActivity extends Activity {

    private EditText emailEdit, passwordEdit, fnameEdit, lnameEdit, phoneEdit;
    private Button newAccButton;
    private ProgressDialog pDialog;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_create_account);

        fnameEdit = findViewById(R.id.fnameEntry);
        lnameEdit = findViewById(R.id.lnameEntry);

        emailEdit = findViewById(R.id.emailEntry);
        passwordEdit = findViewById(R.id.passwordEntry);
        phoneEdit = findViewById(R.id.phoneEntry);
        newAccButton = findViewById(R.id.buttonNewAcc);

        linearLayout = findViewById(R.id.linear);

        this.newAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vérification des champs ( si remplis)

                register();
                finish();


            }
        });
    }

    private void register() {
        pDialog = new ProgressDialog(NewAccountActivity.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("NEW USER...");
        pDialog.setCancelable(false);


        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<MSG> userCall = service.newUser(" ", fnameEdit.getText().toString(),
                lnameEdit.getText().toString(), emailEdit.getText().toString(),
                passwordEdit.getText().toString(), phoneEdit.getText().toString());


        userCall.enqueue(new Callback<MSG>() {
            @Override
            public void onResponse(Call<MSG> call, Response<MSG> response) {
                hidepDialog();
                //onSignupSuccess();
                int rep = response.body().getSuccess();

                if (rep > 0) {
                Log.d("register", response.body().getSuccess() + "-->" + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<MSG> call, Throwable t) {
                hidepDialog();
                Log.d("register", t.getMessage());
                //new CustomToast().Show_Toast(NewAccountActivity.this
                //        , linearLayout,
                //        t.getMessage());


            }
        });
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())

            pDialog.show();
    }
}
