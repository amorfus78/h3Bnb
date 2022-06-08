package com.example.h3bnb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.h3bnb.models.BienModel;

import java.util.List;

public class BienAdapter extends BaseAdapter
{

    private List<BienModel> lcontenu;
    private Context context;

    public BienAdapter(List<BienModel> leBien, Context c){
        this.lcontenu = leBien;
        this.context = c;
    }

    //Cette méthode retourne le nb d'éléments à afficher
    @Override
    public int getCount() {
        return this.lcontenu.size();
    }

    //Cette méthode retourne l'élément à la position définie en paramètre
    @Override
    public Object getItem(int position) {
        return this.lcontenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        view = inflater.inflate(R.layout.row_bien, null);

        BienModel current = (BienModel) getItem(i);

        TextView type = view.findViewById(R.id.type);
        type.setText(current.getType());

        TextView prix = view.findViewById(R.id.price);
        prix.setText(current.getPrice() + "€/jour");

        TextView beds = view.findViewById(R.id.beds);
        beds.setText(current.getBeds() + " lit");

        return view;

    }
}
