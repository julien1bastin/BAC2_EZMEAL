package be.lsinf1225.julien.ezmeal.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import be.lsinf1225.julien.ezmeal.R;
import be.lsinf1225.julien.ezmeal.model.Recette;

import static be.lsinf1225.julien.ezmeal.helpers.Utility.getBytePhoto;

/**
 * Created by Yannick on 10-05-17.
 */

public class RecetteAdapter extends ArrayAdapter<Recette> {
    private Context mContext;
    private LayoutInflater mInflater;
    private Recette[] mDataSource;

    public RecetteAdapter(Context context, Recette[] items) {
        super(context, R.layout.list_item_recipe, items);
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // nb d'items affiches
    @Override
    public int getCount() {
        return mDataSource.length;
    }

    @Override
    public Recette getItem(int position) {
        return mDataSource[position];
    }

    // id de chaque row (position)
    @Override
    public long getItemId(int position) {
        return position;
    }

    // cree un View qui sera utilise comme un row
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = mInflater.inflate(R.layout.list_item_recipe, parent, false);


        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.recipe_list_title);

        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.recipe_list_subtitle);

        AppCompatImageView thumbnailImageView = (AppCompatImageView) rowView.findViewById(R.id.recipe_list_thumbnail);

        Recette recette = getItem(position);

        titleTextView.setText(recette.getNom());
        subtitleTextView.setText(recette.getDescription());

        /** Mettre image dans tumbnailImageView */
        if(recette.getPhoto() != null){
            thumbnailImageView.setImageBitmap(getBytePhoto(recette.getPhoto()));
        }
        return rowView;
    }
}
