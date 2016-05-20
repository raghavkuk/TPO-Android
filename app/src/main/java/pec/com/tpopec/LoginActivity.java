package pec.com.tpopec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pec.com.tpopec.general.Common;
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.home.HomeActivity;

/**
 * Created by Raghav on 09-05-2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private ProgressBar progressBar;

    private EditText sid;
    private EditText password;
    private MySharedPreferences sp;

    public LoginActivity() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        sp = new MySharedPreferences(this);
        sid = (EditText) findViewById(R.id.sid_input);
        password = (EditText) findViewById(R.id.password_input);
        Button login_button = (Button) findViewById(R.id.login_button);


        login_button.setOnClickListener(this);

        return;
    }



    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.login_button){

            final String sidText = this.sid.getText().toString();
            final String passwordText = this.password.getText().toString();

            progressBar.setVisibility(View.VISIBLE);

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = Constants.BASE_URL+"authenticate-user.php";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressBar.setVisibility(View.GONE);
                            JSONObject json = Common.stringToJsonobj(response);
                            try {
                                String sidRec = json.getString(Constants.KEY_SID);
                                String passwordRec = json.getString(Constants.KEY_PASSWORD);
                                String bracnchRec = json.getString(Constants.KEY_BRANCH);

                                Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                loginIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                loginIntent.putExtra(Constants.KEY_SID, sidRec);
                                loginIntent.putExtra(Constants.KEY_PASSWORD, passwordRec);
                                loginIntent.putExtra(Constants.KEY_BRANCH, bracnchRec);

                                startActivity(loginIntent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this,"Couldn't log in!",Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put(Constants.KEY_SID,sidText);
                    params.put(Constants.KEY_PASSWORD,passwordText);
                    return params;
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }

}
