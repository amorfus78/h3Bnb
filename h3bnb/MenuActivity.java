package com.example.h3bnb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.example.h3bnb.models.BienModel;
import com.example.h3bnb.online.APIClient;
import com.example.h3bnb.online.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends Activity {

    private int idUser;
    private List<BienModel> bienModelList;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent iin= getIntent();
        Bundle extras = iin.getExtras();
        idUser = extras.getInt("idUser");



        getBiensByIdUser(idUser);
    }

    private void getBiensByIdUser(int idUser){
        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<List<BienModel>> userCall = service.getBiensByIdUser(idUser);

        userCall.enqueue(new Callback<List<BienModel>>() {
            @Override
            public void onResponse(Call<List<BienModel>> call, Response<List<BienModel>> response) {

                int rep = response.body().size();
                if (rep > 0) {
                    ArrayList<BienModel> selectedBiensForId = new ArrayList<BienModel>();
                    for (int i = 0; i < response.body().size(); i++) {
                        BienModel bien = response.body().get(i);
                        selectedBiensForId.add(bien);


                    }
                    bienModelList = selectedBiensForId;

                    //callBienAdapter();
                    lv = findViewById(R.id.lv_items);
                    BienAdapter adapt = new BienAdapter(bienModelList, MenuActivity.this);
                    lv.setAdapter(adapt);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                            //Toast.makeText(MainActivity.this,i+"",Toast.LENGTH_SHORT).show();
                            //Afficher dans un Toast l'email de l'étudiant sélectionné
                            BienModel selected = (BienModel) lv.getAdapter().getItem(i);

                            showDetailBiens(selected);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<BienModel>> call, Throwable t) {
                Log.d("register", t.getMessage());
            }

        });
    }

    private void showDetailBiens(BienModel selected){
        Intent detailsBien = new Intent(MenuActivity.this, DetailsBienActivity.class);
        detailsBien.putExtra("idBien", selected.getId());
        detailsBien.putExtra("cityname", selected.getIdCity());
        detailsBien.putExtra("idUser", idUser);
        MenuActivity.this.startActivity(detailsBien);
        }
}
