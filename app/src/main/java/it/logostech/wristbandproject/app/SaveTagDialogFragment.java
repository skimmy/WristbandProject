package it.logostech.wristbandproject.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.util.DialogResponder;

/**
 * Created by michele.schimd on 11/07/2014.
 */
public class SaveTagDialogFragment extends DialogFragment {

    private TagModel tagToSave;

    private DialogResponder mResponder = null;

    public SaveTagDialogFragment(TagModel tag) {
        super();
        this.tagToSave = tag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.save_dialog, null);
        builder.setView(view)
                .setPositiveButton(R.string.saveString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TagUtility.saveTagToFile(tagToSave, "test", getActivity());
                        if (mResponder != null) {
                            mResponder.onDialogPositiveClick(SaveTagDialogFragment.this);
                        }
                    }
                })
                .setNegativeButton(R.string.cancelString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (mResponder != null) {
                            mResponder.onDialogNegativeClick(SaveTagDialogFragment.this);
                        }
                    }
                });
        TextView idText = (TextView) view.findViewById(R.id.dialogIdTextView);
        idText.setText(this.tagToSave.getStringId());
        EditText nameEditText = (EditText) view.findViewById(R.id.dialogNameInputText);
        nameEditText.setText("ID_" + this.tagToSave.getStringId());
        return builder.create();
    }

    public TagModel getTagToSave() {
        return tagToSave;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mResponder = (DialogResponder) getActivity();
        } catch (ClassCastException e) {

        }
    }
}
