package it.logostech.wristbandproject.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by michele.schimd on 11/07/2014.
 */
public class SaveTagDialogFragment extends DialogFragment {

   private TagModel tagToSave;

    public SaveTagDialogFragment(TagModel tag) {
        super();
        this.tagToSave = tag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.save_dialog_title)
                .setPositiveButton(R.string.saveString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v("SaveTagDialog", "Sure!!");
                        TagUtility.saveTagToFile(tagToSave, "test", getActivity());

                    }
                })
                .setNegativeButton(R.string.cancelString, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.v("SaveTagDialog", "Naaah!!");
                    }
                });
        return builder.create();
    }

    private void save() {

    }
}
