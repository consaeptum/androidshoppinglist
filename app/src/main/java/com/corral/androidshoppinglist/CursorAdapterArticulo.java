package com.corral.androidshoppinglist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import persistencia.dao.ArticuloContract;
import persistencia.dao.FamiliaDao;
import persistencia.jb.Familia;

public class CursorAdapterArticulo extends CursorAdapter {

    Context contexto;

    public CursorAdapterArticulo(Context context, Cursor cursor) {
        super(context, cursor, 0);
        contexto = context;
    }



    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        view.setBackgroundColor(Color.WHITE);

        TextView tv_list_item_articulo = (TextView) view.findViewById(R.id.list_item_articulo);
        TextView tv_list_item_descripcion = (TextView) view.findViewById(R.id.list_item_descripcion);
        TextView tv_list_item_familia = (TextView) view.findViewById(R.id.list_item_familia);

        Cursor c = getCursor();
        tv_list_item_articulo.setText(c.getString(
                c.getColumnIndex(ArticuloContract.ArticuloEntry.COLUMN_NAME_NOMBRE)));
        tv_list_item_descripcion.setText(c.getString(
                c.getColumnIndex(ArticuloContract.ArticuloEntry.COLUMN_NAME_DESCRIPCION)));

        FamiliaDao fd = new FamiliaDao(contexto);
        String id_familia = c.getString(c.getColumnIndex(ArticuloContract.ArticuloEntry.COLUMN_NAME_ID_FAMILIA));
        Familia familia = fd.read(Long.parseLong(id_familia));
        if (familia != null)
            tv_list_item_familia.setText((familia.getNombre()));

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                // Animate the background color of clicked Item
                ColorDrawable[] color = {
                        new ColorDrawable(contexto.getResources().getColor(R.color.listview_color_4)),
                        new ColorDrawable(contexto.getResources().getColor(R.color.listview_color_1))
                };
                TransitionDrawable trans = new TransitionDrawable(color);
                v.setBackground(trans);
                trans.startTransition(500); // duration 2 seconds

                // Go back to the default background color of Item
                ColorDrawable[] color2 = {
                        new ColorDrawable(contexto.getResources().getColor(R.color.listview_color_3)),
                        new ColorDrawable(contexto.getResources().getColor(R.color.listview_color_4))
                };
                TransitionDrawable trans2 = new TransitionDrawable(color2);
                v.setBackground(trans2);
                trans2.startTransition(500); // duration 2 seconds

                return false;
            }
        });

        return view;
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cursor_item_articulo, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find fields to populate in inflated template
        TextView tvArticulo = (TextView) view.findViewById(R.id.list_item_articulo);
        // Extract properties from cursor
        String articulo = cursor.getString(cursor.getColumnIndexOrThrow(
                ArticuloContract.ArticuloEntry.COLUMN_NAME_NOMBRE));
        // Populate fields with extracted properties
        tvArticulo.setText(articulo);

        TextView tvDescripcion = (TextView) view.findViewById(R.id.list_item_descripcion);
        // Extract properties from cursor
        String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(
                ArticuloContract.ArticuloEntry.COLUMN_NAME_DESCRIPCION));
        // Populate fields with extracted properties
        tvArticulo.setText(descripcion);

        TextView tvFamili = (TextView) view.findViewById(R.id.list_item_familia);
        // Extract properties from cursor

        FamiliaDao fd = new FamiliaDao(contexto);
        String id_familia = cursor.getString(
                cursor.getColumnIndex(ArticuloContract.ArticuloEntry.COLUMN_NAME_ID_FAMILIA));
        Familia familiajb = fd.read(Long.parseLong(id_familia));
        if (familiajb != null) {
            tvArticulo.setText(familiajb.getNombre());
        }
        fd.close();
    }
}