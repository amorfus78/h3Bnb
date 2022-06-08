package com.example.h3bnb;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.h3bnb.models.UserModel;
import com.example.h3bnb.online.APIClient;
import com.example.h3bnb.online.APIInterface;
import com.example.h3bnb.online.MSG;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private EditText loginEdit, passwordEdit;
    private TextView loginText, passwordText;
    private Button btnLogin, btnNewAccount, btnForgotPassword;
    private ProgressDialog pDialog;
    private int idToConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        loginEdit = findViewById(R.id.emailEntry);
        passwordEdit = findViewById(R.id.passwordEntry);
        btnLogin = findViewById(R.id.buttonLogin);
        btnNewAccount = findViewById(R.id.createAccountButton);
        btnForgotPassword = findViewById(R.id.forgotPasswordButton);

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vérification des champs ( si remplis)
                String email = loginEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (!email.equals("") || !password.equals("") ){
                    loginTry();
                }


            }
        });

        this.btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newAcc();
            }
        });

        this.btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPass();
            }
        });
    }

    private void loginTry(){

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("NEW USER...");
        pDialog.setCancelable(false);


        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<List<UserModel>> userCall = service.getUsers();


        userCall.enqueue(new Callback<List<UserModel>> () {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                hidepDialog();
                //onSignupSuccess();
                int rep = response.body().size();

                if (rep > 0) {
                    Boolean toConnect = false;
                    // check if id et password sont dans la liste

                    for (int i = 0; i <  response.body().size(); i++){
                        if (loginEdit.getText().toString().equals(response.body().get(i).getEmail()) &&
                            passwordEdit.getText().toString().equals(response.body().get(i).getPassword())){
                            idToConnect = response.body().get(i).getId();
                            toConnect = true;
                        }
                    }

                    if (toConnect) {
                        Intent connexion = new Intent(MainActivity.this, HomeActivity.class);
                        connexion.putExtra("idUser", idToConnect);
                        MainActivity.this.startActivity(connexion);
                    }


                }
                //Log.d("register", response.body().getSuccess() + "-->" + response.body().getMessage());

            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {

                hidepDialog();
                Log.d("register", t.getMessage());
                //new CustomToast().Show_Toast(NewAccountActivity.this
                //        , linearLayout,
                //        t.getMessage());


            }
        });


    }

    private void newAcc() {
        Intent newAccount = new Intent (MainActivity.this, NewAccountActivity.class);
        MainActivity.this.startActivity(newAccount);
    }

    private void forgotPass(){
        Intent newPass = new Intent (MainActivity.this, NewPasswordActivity.class);
        MainActivity.this.startActivity(newPass);
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