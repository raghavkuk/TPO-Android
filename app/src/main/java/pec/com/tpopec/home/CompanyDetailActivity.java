package pec.com.tpopec.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import pec.com.tpopec.R;

/**
 * Created by Raghav on 10-05-2016.
 */
public class CompanyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        setContentView(R.layout.activity_company_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView cName = (TextView) findViewById(R.id.c_name);
        cName.setText(intent.getStringExtra("name"));

        TextView cDesig = (TextView) findViewById(R.id.c_desig);
        cDesig.setText(intent.getStringExtra("desig"));

        TextView cDesc = (TextView) findViewById(R.id.c_desc);
        cDesc.setText(intent.getStringExtra("desc"));

        TextView cCtc = (TextView) findViewById(R.id.c_ctc);
        cCtc.setText(String.valueOf(intent.getDoubleExtra("ctc", 0)));

        TextView cDeadline = (TextView) findViewById(R.id.c_deadline);
        cDeadline.setText(intent.getStringExtra("deadline"));

        TextView cDate = (TextView) findViewById(R.id.c_date);
        cDate.setText(intent.getStringExtra("date"));

        Button applyButton = (Button) findViewById(R.id.apply_button);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "Your application has been sent.";
                Toast.makeText(CompanyDetailActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        getSupportActionBar().setTitle(cName.getText());
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
}
