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
public class SubscriptionListLoader {
    private String filename;
    private Context context;
    private Gson gson;
    
    public SubscriptionListLoader(Context context, String filename) {
        this.context = context;
        this.filename = filename;
        this.gson = new Gson();
    }
    
    public SubscriptionList load() throws IOException {
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
    
    public void save(SubscriptionList list) throws IOException {
        FileOutputStream stream = context.openFileOutput(this.filename, Context.MODE_PRIVATE);
        FileWriter fileWriter = new FileWriter(stream.getFD());
        fileWriter.write(this.gson.toJson(list));
        fileWriter.close();
    }
}
