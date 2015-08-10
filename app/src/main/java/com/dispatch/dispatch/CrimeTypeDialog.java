package com.dispatch.dispatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dispatch.dispatch.models.Crime;

/**
 * Created by Daniel on 8/10/2015.
 */
public class CrimeTypeDialog extends AlertDialog.Builder {
    private Dialog dialog;

    public CrimeTypeDialog(Context context, final CrimeTypeDialogListener listener) {
        super(context);

        setTitle("What's going on?");


        Crime.Type[] types = Crime.Type.values();
        int length = types.length;
        final String[] crimes = new String[length];

        for(int i = 0; i < length; i++) {
            crimes[i] = types[i].toString();
        }

        ListView crimeListView = new ListView(context);
        crimeListView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, crimes));
        crimeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                listener.sendHelp(Crime.Type.valueOf(crimes[position]));
            }
        });

        setView(crimeListView);

        dialog = show();
    }

    public static interface CrimeTypeDialogListener {
        void sendHelp(Crime.Type type);
    }
}
