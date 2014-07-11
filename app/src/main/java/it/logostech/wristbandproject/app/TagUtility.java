package it.logostech.wristbandproject.app;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * Created by michele.schimd on 04/07/2014.
 */
public class TagUtility {
    public static void saveTagToFile(TagModel tag, String saveName, Context ctx) {
        try {
            FileOutputStream fos = ctx.openFileOutput("_nfc_tag_" + saveName, Context.MODE_PRIVATE);
            // write the length of id
            int length = tag.getId().length;
            Log.v("MY_DEBUG", "original length " + length);
            byte[] lengthArray = ByteBuffer.allocate(4).putInt(length).array();
            fos.write(lengthArray);
            fos.write(tag.getId());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TagModel loadTagFromFile(String saveName, Context ctx) {
        TagModel tagModel = null;
        try {
            FileInputStream is = ctx.openFileInput("_nfc_tag_" + saveName);
            byte[] lengthArray = new byte[4];
            is.read(lengthArray);
            int length = new BigInteger(lengthArray).intValue();
            Log.v("MY_DEBUG", "Read length " + length);
            byte[] id = new byte[length];
            is.read(id);
            tagModel = new TagModel(id);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tagModel;
    }
}
