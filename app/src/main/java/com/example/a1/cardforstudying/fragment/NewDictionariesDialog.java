package com.example.a1.cardforstudying.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.example.a1.cardforstudying.R;
import com.example.a1.cardforstudying.model.DictionaryLab;

/**
 * Created by user on 13.07.2018.
 */

public class NewDictionariesDialog extends DialogFragment {
    final String TAG = getClass().getSimpleName();
    EditText newNameField;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        newNameField = findViewById(R.id.name_new_dictionaries);

        builder.setView(inflater.inflate(R.layout.new_dictionaries_dialog, null))
                // Add action buttons
                .setPositiveButton(R.string.menu_save, (dialog, id) -> {
                    DictionaryLab.get(getActivity()).putNewDictionary(newNameField.getText().toString());
                })
                .setNegativeButton(R.string.menu_cancel, (dialog, id) -> {
                    NewDictionariesDialog.this.getDialog().cancel();
                });
        return builder.create();
    }
}
