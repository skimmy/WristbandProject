package it.logostech.wristbandproject.app.util;

import android.content.Context;

import java.io.File;

/**
 * Created by michele.schimd on 04/07/2014.
 */
public class FileSystemUtil {
    public static File descriptorFromInternalStorage(Context ctx, String path) {
        File root = ctx.getFilesDir();
        return new File(root, path);
    }
}
