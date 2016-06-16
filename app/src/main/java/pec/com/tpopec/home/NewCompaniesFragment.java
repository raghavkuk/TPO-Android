package pec.com.tpopec.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Map;

import pec.com.tpopec.R;
import pec.com.tpopec.general.Common;
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.DividerItemDecoration;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.model.NewCompany;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewCompaniesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewCompaniesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewCompaniesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private OnFragmentInteractionListener mListener;
    private NewCompaniesAdapter newCompaniesAdapter;
    private ArrayList<NewCompany> newCompanies;
    private MySharedPreferences sp;
    private String sid, branch;
    private SwipeRefreshLayout swipeRefreshLayout;

    public NewCompaniesFragment() {
        // Required empty public constructor
    }


    public static NewCompaniesFragment newInstance() {
        NewCompaniesFragment fragment = new NewCompaniesFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_new_companies, container, false);
        sp = new MySharedPreferences(getActivity());
        sid = sp.getSid();
        branch = sp.getBranch();
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        newCompanies = new ArrayList<NewCompany>();
        getCompanies();
        RecyclerView newCompaniesRecyclerView = (RecyclerView) rootView.findViewById(R.id.new_companies_recycler);
        newCompaniesAdapter = new NewCompaniesAdapter(newCompanies);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        newCompaniesRecyclerView.setLayoutManager(mLayoutManager);
        newCompaniesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newCompaniesRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        newCompaniesRecyclerView.setAdapter(newCompaniesAdapter);

        newCompaniesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), newCompaniesRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CompanyDetailActivity.class);
                intent.putExtra("name", newCompanies.get(position).getCompanyName());
                intent.putExtra("desig", newCompanies.get(position).getDesignation());
                intent.putExtra("desc", newCompanies.get(position).getDescription());
                intent.putExtra("ctc", newCompanies.get(position).getCtc());
                intent.putExtra("deadline", newCompanies.get(position).getDeadline());
                intent.putExtra("date", newCompanies.get(position).getDateOfVisit());
                intent.putExtra(Constants.KEY_JAF_ID, newCompanies.get(position).getJaf_id());
                intent.putExtra(Constants.KEY_COMPANY_ID, newCompanies.get(position).getCompany_id());
                startActivity(intent);
            }
        }));
        return rootView;
    }

    private void getCompanies(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = Constants.BASE_URL+"get-new-companies.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            swipeRefreshLayout.setRefreshing(false);
                            newCompanies.clear();
                            newCompaniesAdapter.notifyDataSetChanged();
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                NewCompany company = new NewCompany(jsonObject);
                                newCompanies.add(company);
                                newCompaniesAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);

                        if(error.getClass().getName().equals("com.android.volley.NoConnectionError")){
                            Toast.makeText(getContext(), "No network connection", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(), "Unable to get companies", Toast.LENGTH_LONG).show();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Constants.KEY_SID,sid);
                params.put(Constants.KEY_BRANCH,branch);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {

        if(Common.isNetworkConnectionAvailable(getActivity())){
            getCompanies();
        }else{
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "No network connection", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }


    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private NewCompaniesFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final NewCompaniesFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
