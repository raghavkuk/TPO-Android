package pec.com.tpopec.model;

import org.json.JSONException;
import org.json.JSONObject;

import pec.com.tpopec.general.Constants;

/**
 * Created by Raghav on 10-05-2016.
 */
public class Application {

    private int company_id, jaf_id, cv_id;
    private String company_name, job_designation, student_name, student_prog, student_branch, sid;

    public Application(){

    }

    public Application(JSONObject jsonObject) {

        try {
            company_name = jsonObject.getString(Constants.KEY_COMPANY_NAME);
            job_designation = jsonObject.getString(Constants.KEY_JOB_DESIGNATION);
            company_id = jsonObject.getInt(Constants.KEY_COMPANY_ID);
            jaf_id = jsonObject.getInt(Constants.KEY_JAF_ID);
            sid = jsonObject.getString(Constants.KEY_SID);
            cv_id = jsonObject.getInt(Constants.KEY_CV_ID);
            student_name = jsonObject.getString(Constants.KEY_STUDENT_NAME);
            student_prog = jsonObject.getString(Constants.KEY_STUDENT_PROGRAMME);
            student_branch = jsonObject.getString(Constants.KEY_BRANCH);
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getCompanyName() {
        return company_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getJaf_id() {
        return jaf_id;
    }

    public void setJaf_id(int jaf_id) {
        this.jaf_id = jaf_id;
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getJob_designation() {
        return job_designation;
    }

    public void setJob_designation(String job_designation) {
        this.job_designation = job_designation;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getStudent_prog() {
        return student_prog;
    }

    public void setStudent_prog(String student_prog) {
        this.student_prog = student_prog;
    }

    public String getStudent_branch() {
        return student_branch;
    }

    public void setStudent_branch(String student_branch) {
        this.student_branch = student_branch;
    }

    public void setName(String c_name) {
        this.company_name = c_name;
    }

    public String getJobDesignation() {
        return job_designation;
    }

    public void setJobDesignation(String designation) {
        this.job_designation = designation;
    }
}
