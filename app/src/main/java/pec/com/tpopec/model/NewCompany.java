package pec.com.tpopec.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Raghav on 09-05-2016.
 */
public class NewCompany {

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

    public NewCompany(JSONObject jsonObject){

        try {
            companyName = jsonObject.getString("company_name");
            designation = jsonObject.getString("job_designation");
            description = jsonObject.getString("job_description");
            ctc = jsonObject.getDouble("ctc");
            gross = jsonObject.getDouble("gross");
            perks = jsonObject.getString("perks");
            bond = jsonObject.getString("bond");
            deadline = jsonObject.getString("deadline");
            dateOfVisit = jsonObject.getString("dateofvisit");
            cgpa = jsonObject.getDouble("cgpa");

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
