package it.logostech.wristbandproject.app.util;

import android.app.DialogFragment;

/**
 * Created by michele.schimd on 16/07/2014.
 */
public interface DialogResponder {
    public void onDialogPositiveClick(DialogFragment dialog);

    public void onDialogNegativeClick(DialogFragment dialog);
}
