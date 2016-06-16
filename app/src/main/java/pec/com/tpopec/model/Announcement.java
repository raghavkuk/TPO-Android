package pec.com.tpopec.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Raghav on 26-05-2016.
 */
public class Announcement {

    private String msg, date;

    public String getMsg() {
        return msg;
    }

    public Announcement(JSONObject jsonObject) {

        try {
            //company_name = jsonObject.getString("company_name");
            msg = jsonObject.getString("msg");
            date = jsonObject.getString("date");
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    //public String getCompany_name() {
//        return company_name;
//    }

//    public void setCompany_name(String company_name) {
//        this.company_name = company_name;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
