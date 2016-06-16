package pec.com.tpopec.model;

import org.json.JSONException;
import org.json.JSONObject;

import pec.com.tpopec.general.Constants;

/**
 * Created by Raghav on 09-05-2016.
 */
public class NewCompany {

    private int jaf_id, company_id;
    private String companyName, dateOfVisit, description, designation, perks, bond, deadline;
    private Double ctc, gross, cgpa;

    public NewCompany(){}

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPerks() {
        return perks;
    }

    public void setPerks(String perks) {
        this.perks = perks;
    }

    public String getBond() {
        return bond;
    }

    public void setBond(String bond) {
        this.bond = bond;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Double getCtc() {
        return ctc;
    }

    public void setCtc(Double ctc) {
        this.ctc = ctc;
    }

    public Double getGross() {
        return gross;
    }

    public void setGross(Double gross) {
        this.gross = gross;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
    }

    public int getJaf_id() {
        return jaf_id;
    }

    public void setJaf_id(int jaf_id) {
        this.jaf_id = jaf_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public NewCompany(JSONObject jsonObject){

        try {
            companyName = jsonObject.getString(Constants.KEY_COMPANY_NAME);
            designation = jsonObject.getString(Constants.KEY_JOB_DESIGNATION);
            description = jsonObject.getString(Constants.KEY_JOB_DESCRIPTION);
            ctc = jsonObject.getDouble(Constants.KEY_CTC);
            gross = jsonObject.getDouble(Constants.KEY_GROSS);
            perks = jsonObject.getString(Constants.KEY_PERKS);
            bond = jsonObject.getString(Constants.KEY_BOND);
            deadline = jsonObject.getString(Constants.KEY_DEADLINE);
            dateOfVisit = jsonObject.getString(Constants.KEY_DATE_OF_VISIT);
            cgpa = jsonObject.getDouble(Constants.KEY_CGPA);
            jaf_id = jsonObject.getInt(Constants.KEY_JAF_ID);
            company_id = jsonObject.getInt(Constants.KEY_COMPANY_ID);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDateOfVisit() {
        return dateOfVisit;
    }

    public void setDateOfVisit(String dateOfVisit) {
        this.dateOfVisit = dateOfVisit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
