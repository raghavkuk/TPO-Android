package pec.com.tpopec.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pec.com.tpopec.R;
import pec.com.tpopec.model.NewCompany;

/**
 * Created by Raghav on 09-05-2016.
 */
public class NewCompaniesAdapter extends RecyclerView.Adapter<NewCompaniesAdapter.NewCompanyViewHolder> {

    private ArrayList<NewCompany> newCompanyList;


    public NewCompaniesAdapter(ArrayList<NewCompany> newCompanies) {
        this.newCompanyList = newCompanies;
    }

    @Override
    public NewCompanyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_new_company, parent, false);

        return new NewCompanyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewCompanyViewHolder holder, int position) {
        NewCompany company = newCompanyList.get(position);
        holder.name.setText(company.getCompanyName());
        holder.designation.setText(company.getDesignation());
        holder.date.setText(company.getDateOfVisit());
    }

    @Override
    public int getItemCount() {
        return newCompanyList.size();
    }

    public static class NewCompanyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, date, designation;

        public NewCompanyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_new_company);
            date = (TextView) view.findViewById(R.id.date_new_company);
            designation = (TextView) view.findViewById(R.id.designation_new_company);

        }

    }
}
