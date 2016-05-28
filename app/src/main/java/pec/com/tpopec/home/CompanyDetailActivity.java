package pec.com.tpopec.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pec.com.tpopec.R;
import pec.com.tpopec.general.Common;
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.model.Cv;

/**
 * Created by Raghav on 10-05-2016.
 */
public class CompanyDetailActivity extends AppCompatActivity {

    private Spinner cvSpinner;
    private ArrayList<Cv> cvs;
    private MySharedPreferences sp;
    private String sid;
    private ArrayAdapter<Cv> cvArrayAdapter;

    private int cId, jafId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        sp = new MySharedPreferences(this);
        sid = sp.getSid();
        cvs = new ArrayList<Cv>();

        setContentView(R.layout.activity_company_detail);
        cvSpinner = (Spinner)findViewById(R.id.cv_spinner);
        getCvs();
        cvArrayAdapter = new CvSpinnerAdapter(this, R.layout.item_spinner, R.id.item_spinner_text, cvs);
        cvArrayAdapter.setDropDownViewResource(R.layout.item_spinner_drop_down);
        cvSpinner.setAdapter(cvArrayAdapter);

        cId = intent.getIntExtra(Constants.KEY_COMPANY_ID, 0);
        jafId = intent.getIntExtra(Constants.KEY_JAF_ID, 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final TextView cName = (TextView) findViewById(R.id.c_name);
        cName.setText(intent.getStringExtra("name"));

        final TextView cDesig = (TextView) findViewById(R.id.c_desig);
        cDesig.setText(intent.getStringExtra("desig"));

        TextView cDesc = (TextView) findViewById(R.id.c_desc);
        cDesc.setText(intent.getStringExtra("desc"));

        TextView cCtc = (TextView) findViewById(R.id.c_ctc);
        cCtc.setText(String.valueOf(intent.getDoubleExtra("ctc", 0)));

        TextView cDeadline = (TextView) findViewById(R.id.c_deadline);
        cDeadline.setText(intent.getStringExtra("deadline"));

        TextView cDate = (TextView) findViewById(R.id.c_date);
        cDate.setText(intent.getStringExtra("date"));

        FloatingActionButton applyButton = (FloatingActionButton) findViewById(R.id.apply_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(CompanyDetailActivity.this);
                String url = Constants.BASE_URL+"apply-for-company.php";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = Common.stringToJsonobj(response);
                                try {
                                    String result = jsonObject.getString("result");
                                    if(result.equals("Success")){
                                        Toast.makeText(CompanyDetailActivity.this, "Application sent successfully.", Toast.LENGTH_LONG).show();
                                        Intent appSentIntent = new Intent(CompanyDetailActivity.this, HomeActivity.class);
                                        startActivity(appSentIntent);
                                        CompanyDetailActivity.this.finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(CompanyDetailActivity.this, "Unable to send application.", Toast.LENGTH_LONG).show();

                            }
                        }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();
                        params.put(Constants.KEY_SID, sid);
                        params.put(Constants.KEY_COMPANY_ID, String.valueOf(cId));
                        params.put(Constants.KEY_JAF_ID, String.valueOf(jafId));
                        params.put(Constants.KEY_JOB_DESIGNATION, (String) cDesig.getText());
                        params.put(Constants.KEY_COMPANY_NAME, (String) cName.getText());
                        params.put(Constants.KEY_STUDENT_NAME, sp.getStudentName());
                        params.put(Constants.KEY_BRANCH, sp.getBranch());
                        params.put(Constants.KEY_STUDENT_PROGRAMME, sp.getStudentProgramme());
                        params.put(Constants.KEY_CV_ID, String.valueOf(((Cv)cvSpinner.getSelectedItem()).getCvId()));
                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        getSupportActionBar().setTitle(cName.getText());
    }

    private void getCvs(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = Constants.BASE_URL+"get-cvs.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Cv cv = new Cv(jsonObject);
                                cvs.add(cv);
                                cvArrayAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("Unable to get CVs.");
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_SID,sid);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private static class CvSpinnerAdapter extends ArrayAdapter<Cv> {

        private ArrayList<Cv> cvs;
        private Context context;

        public CvSpinnerAdapter(Context cntxt, int resource, int textViewResourceId, List<Cv> objects) {
            super(cntxt, resource, textViewResourceId, objects);
            cvs = (ArrayList<Cv>) objects;
            context = cntxt;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.item_spinner, parent, false);
            }
            TextView textViewName = (TextView) convertView.findViewById(R.id.item_spinner_text);
            textViewName.setText(cvs.get(position).getFilename());

            TextView textCvId = (TextView) convertView.findViewById(R.id.cv_id);
            textCvId.setText(String.valueOf(cvs.get(position).getCvId()));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                convertView = inflater.inflate(R.layout.item_spinner_drop_down, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.item_spinner_dd_text);
            textView.setText(cvs.get(position).getFilename());
            return convertView;
        }
    }

}
