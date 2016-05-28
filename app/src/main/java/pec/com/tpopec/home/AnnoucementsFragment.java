package pec.com.tpopec.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import pec.com.tpopec.general.Constants;
import pec.com.tpopec.general.DividerItemDecoration;
import pec.com.tpopec.general.MySharedPreferences;
import pec.com.tpopec.model.Announcement;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AnnoucementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AnnoucementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnnoucementsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private OnFragmentInteractionListener mListener;
    private AnnouncementsAdapter announcementsAdapter;
    private ArrayList<Announcement> announcements;
    private MySharedPreferences sp;
    private String sid, branch;
    private SwipeRefreshLayout swipeRefreshLayout;

    public AnnoucementsFragment() {
        // Required empty public constructor
    }

    public static AnnoucementsFragment newInstance() {
        AnnoucementsFragment fragment = new AnnoucementsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_annoucements, container, false);
        sp = new MySharedPreferences(getActivity());
        sid = sp.getSid();
        branch = sp.getBranch();
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        announcements = new ArrayList<Announcement>();
        getAnnouncements();
        RecyclerView announcementsRecyclerView = (RecyclerView) rootView.findViewById(R.id.announcements_recycler);
        announcementsAdapter = new AnnouncementsAdapter(announcements);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        announcementsRecyclerView.setLayoutManager(mLayoutManager);
        announcementsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        announcementsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        announcementsRecyclerView.setAdapter(announcementsAdapter);

        return rootView;
    }

    private void getAnnouncements(){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = Constants.BASE_URL+"get-announcements.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            swipeRefreshLayout.setRefreshing(false);
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Announcement announcement = new Announcement(jsonObject);
                                announcements.add(announcement);
                                announcementsAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.print("That didn't work!");
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
        announcements = new ArrayList<Announcement>();
        getAnnouncements();
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
}
