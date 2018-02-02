package ca.ualberta.cmput301w18.jdorn_subbook;


import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility to load a SubscriptionList from a file
 * and save it back to a file.
 */
class SubscriptionListLoader {
    /** Target filename for saving and loading. */
    private final String filename;
    
    /** Context used to request file streams. */
    private final Context context;
    
    /** GSON de/serializer. */
    private final Gson gson;
    
    /**
     * Creates a SubscriptionListLoader for the given filename.
     *
     * @param context context used to request file streams
     * @param filename target file name
     */
    SubscriptionListLoader(Context context, String filename) {
        this.context = context;
        this.filename = filename;
        this.gson = new Gson();
    }
    
    /**
     * Restores the list of subscriptions stored in the target file.
     * If the file does not exist, returns an empty list. (This allows
     * this method to be transparent to whether any data has been saved
     * yet.)
     *
     * @return list restored from the target file (or empty list if file does not exist)
     * @throws IOException on failure to open or read the file
     */
    SubscriptionList load() throws IOException {
        try {
            FileInputStream stream = context.openFileInput(this.filename);
            FileReader fileReader = new FileReader(stream.getFD());
            SubscriptionList list = this.gson.fromJson(fileReader, SubscriptionList.class);
            fileReader.close();
            return list;
        } catch (FileNotFoundException e) {
            return new SubscriptionList();
        }
    }
    
    /**
     * Saves the given list of subscriptions to the target file.
     *
     * @param list list of subscriptions to save
     * @throws IOException on failure to open or write the file
     */
    void save(SubscriptionList list) throws IOException {
        FileOutputStream stream = context.openFileOutput(this.filename, Context.MODE_PRIVATE);
        FileWriter fileWriter = new FileWriter(stream.getFD());
        fileWriter.write(this.gson.toJson(list));
        fileWriter.close();
    }
}
