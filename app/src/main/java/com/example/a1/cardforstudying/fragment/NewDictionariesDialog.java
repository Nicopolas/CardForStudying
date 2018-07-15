package com.example.a1.cardforstudying.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.a1.cardforstudying.ListActivity;
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_dictionaries_dialog, null);
        newNameField = view.findViewById(R.id.name_new_dictionaries);

        builder.setView(view)
                .setPositiveButton(R.string.menu_save, (dialog, id) -> {
                    //переделать через интерфейс или через презентер
                    DictionaryLab.get(getActivity()).putNewDictionary(newNameField.getText().toString());
                    ((DictionariesListFragment) ((ListActivity) getActivity()).getActiveFragment()).initGUI();
                })
                .setNegativeButton(R.string.menu_cancel, (dialog, id) -> {
                    NewDictionariesDialog.this.getDialog().cancel();
                });
        return builder.create();
    }
}
