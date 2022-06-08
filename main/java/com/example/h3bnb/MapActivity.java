package com.example.h3bnb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.h3bnb.models.BienModel;
import com.example.h3bnb.models.CityModel;
import com.example.h3bnb.online.APIClient;
import com.example.h3bnb.online.APIInterface;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.simplefastpoint.LabelledGeoPoint;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlay;
import org.osmdroid.views.overlay.simplefastpoint.SimpleFastPointOverlayOptions;
import org.osmdroid.views.overlay.simplefastpoint.SimplePointTheme;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends Activity {


    /*double barcaLat = 41.3850639;
    double barcaLong = 2.1734035;
    double damLat = 52.370216;
    double damLong = 4.895168;
    double parisLat = 48.856614;
    double parisLong = 2.3522219;*/
    //double selectedLat = 41.3850639;
    //double selectedLong = 2.1734035;
    double selectedLat = 152155.15;
    double selectedLong = 152155.15;
    private CityModel selectedCity;
    private int idCity, idUser;
    private String cityName;

    private ArrayList<BienModel> selectedBiens;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent iin= getIntent();


        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);

        //getExtras()
        Bundle extras = iin.getExtras();

        idCity = extras.getInt("idCity");
        idUser = extras.getInt("idUser");
        getBiens(idCity);
        getCityCoordinates(idCity);



    }

    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

    public void getBiens(int idCity){
        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<List<BienModel>> userCall = service.getBiensByIdCity(idCity);


        userCall.enqueue(new Callback<List<BienModel>>() {
            @Override
            public void onResponse(Call<List<BienModel>> call, Response<List<BienModel>> response) {

                int rep = response.body().size();
                if (rep > 0) {
                    ArrayList<BienModel> selectedBiensForId = new ArrayList<BienModel>();
                    for (int i = 0; i <  response.body().size(); i++){
                        BienModel bien = response.body().get(i);
                        selectedBiensForId.add(bien);


                    }
                    selectedBiens = selectedBiensForId;

                    if (isDataLoaded()){
                        buildMap();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<BienModel>> call, Throwable t) {
                Log.d("register", t.getMessage());
            }
        });
    }

    public void getCityCoordinates(int idCity){
        APIInterface service = APIClient.getClient2().create(APIInterface.class);
        Call<List<CityModel>> userCall = service.getCityById(idCity);


        userCall.enqueue(new Callback<List<CityModel>>() {
            @Override
            public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                selectedCity = response.body().get(0);
                selectedLat = Double.parseDouble(selectedCity.getLatitude());
                selectedLong = Double.parseDouble(selectedCity.getLongitude());
                cityName = selectedCity.getName();

                if (isDataLoaded()){
                    buildMap();
                }
            }

            @Override
            public void onFailure(Call<List<CityModel>> call, Throwable t) {
                Log.d("register", t.getMessage());
            }

        });
    }

    public void buildMap(){

        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        //map.setUseDataConnection(false);

        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(16);
        GeoPoint startPoint = new GeoPoint(selectedLat, selectedLong);
        mapController.setCenter(startPoint);

        //put Biens on map
        List<IGeoPoint> points = new ArrayList<>();
        int size = selectedBiens.size();
        for (int i = 0; i<size; i++){
            BienModel property = selectedBiens.get(i);
            points.add(new LabelledGeoPoint(Double.parseDouble(property.getLatitude()), Double.parseDouble(property.getLongitude())
            , "Point #" + i));
        }

        // wrap them in a theme
        SimplePointTheme pt = new SimplePointTheme(points, true);

        // create label style
        Paint textStyle = new Paint();
        textStyle.setStyle(Paint.Style.FILL);
        textStyle.setColor(Color.parseColor("#0000ff"));
        textStyle.setTextAlign(Paint.Align.CENTER);
        textStyle.setTextSize(24);

        // set some visual options for the overlay
        // we use here MAXIMUM_OPTIMIZATION algorithm, which works well with >100k points
        SimpleFastPointOverlayOptions opt = SimpleFastPointOverlayOptions.getDefaultStyle()
                .setAlgorithm(SimpleFastPointOverlayOptions.RenderingAlgorithm.MAXIMUM_OPTIMIZATION)
                .setRadius(7).setIsClickable(true).setCellSize(15).setTextStyle(textStyle);

        // create the overlay with the theme
        final SimpleFastPointOverlay sfpo = new SimpleFastPointOverlay(pt, opt);

        // onClick callback
        sfpo.setOnClickListener(new SimpleFastPointOverlay.OnClickListener() {
            @Override
            public void onClick(SimpleFastPointOverlay.PointAdapter points, Integer point) {
                // New activity BiensDetail
                // get BienModel from point Integer => index in selectedBiens
                BienModel property = selectedBiens.get(point);

                Intent detailsBien = new Intent(MapActivity.this, DetailsBienActivity.class);
                detailsBien.putExtra("idBien", property.getId());
                detailsBien.putExtra("cityname", cityName);
                detailsBien.putExtra("idUser", idUser);
                MapActivity.this.startActivity(detailsBien);

                //Toast.makeText(map.getContext()
                //        , "You clicked " + property.getType()
                //        , Toast.LENGTH_SHORT).show();
            }
        });

        // add overlay
        map.getOverlays().add(sfpo);
    }

    private Boolean isDataLoaded(){
        Boolean keepGoing = true;
        if (selectedLong == 152155.15 || selectedLat == 152155.15 || selectedBiens == null ){
            keepGoing = false;
        }
        return keepGoing;
    }
}