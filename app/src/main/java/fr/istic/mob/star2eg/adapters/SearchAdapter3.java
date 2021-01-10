package fr.istic.mob.star2eg.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import fr.istic.mob.star2eg.modeles.StarContract;
import fr.istic.mob.star2eg.modeles.Stop;


public class SearchAdapter3 extends CursorAdapter {

    private LayoutInflater flater;
    private List<Stop> listStops;
    private int listItemLayoutResource;
    private int textViewItemShortName;
    private int textViewItemShortHour ;
    private Cursor cursor   ;

    public SearchAdapter3(Activity context, Cursor cursor, int listItemLayoutResource, int textViewItemName, int textViewItemShortHour) {
        super(context, cursor, 0);
        this.cursor = cursor ;
        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemShortName = textViewItemName;
        this.textViewItemShortHour = textViewItemShortHour;
        this.flater = context.getLayoutInflater();
    }




    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(this.listItemLayoutResource, parent, false);
    }



    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBody = (TextView) view.findViewById(this.textViewItemShortName);
        //TextView tvPriority = (TextView) view.findViewById(R.id.tvPriority);
        // Extract properties from cursor

        Log.i("Test","Index Name  : "+cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)) ;

        Log.i("Test","Index Name  : "+cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME)) ;

        String name = cursor.getString(cursor.getColumnIndex(StarContract.Stops.StopColumns.NAME));
        //int priority = cursor.getInt(cursor.getColumnIndexOrThrow("priority"));
        // Populate fields with extracted properties
        tvBody.setText(name);
        //tvPriority.setText(String.valueOf(priority));
    }
}