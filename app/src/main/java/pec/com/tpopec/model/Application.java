package pec.com.tpopec.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Raghav on 10-05-2016.
 */
public class Application {

    private String name, designation;

    public Application(JSONObject jsonObject) {

        try {
            name = jsonObject.getString("company_name");
            designation = jsonObject.getString("job_designation");
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
