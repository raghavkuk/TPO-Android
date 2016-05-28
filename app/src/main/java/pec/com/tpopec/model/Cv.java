package pec.com.tpopec.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Raghav on 24-05-2016.
 */
public class Cv {

    private String filename;
    private int cv_id;

    public Cv(JSONObject jsonObject) {

        try {
            filename = jsonObject.getString("filename");
            cv_id = jsonObject.getInt("cv_id");
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String name) {
        this.filename = name;
    }

    public int getCvId() {
        return cv_id;
    }

}
