package pec.com.tpopec.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pec.com.tpopec.R;
import pec.com.tpopec.model.Application;

/**
 * Created by Raghav on 10-05-2016.
 */
public class AppliedCompaniesAdapter extends RecyclerView.Adapter<AppliedCompaniesAdapter.AppliedCompanyViewHolder>{

    private ArrayList<Application> appliedCompaniesList;


    public AppliedCompaniesAdapter(ArrayList<Application> appliedCompanies) {
        this.appliedCompaniesList = appliedCompanies;
    }

    @Override
    public AppliedCompanyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_applied_company, parent, false);

        return new AppliedCompanyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(AppliedCompanyViewHolder holder, int position) {
        Application company = appliedCompaniesList.get(position);
        holder.name.setText(company.getName());
        holder.designation.setText(company.getDesignation());
    }

    @Override
    public int getItemCount() {
        return appliedCompaniesList.size();
    }

    public static class AppliedCompanyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, designation;

        public AppliedCompanyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name_new_company);
            designation = (TextView) view.findViewById(R.id.designation_new_company);

        }

    }
}
