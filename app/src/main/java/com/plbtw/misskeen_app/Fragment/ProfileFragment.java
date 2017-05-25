package com.plbtw.misskeen_app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.plbtw.misskeen_app.Model.UserObject;
import com.plbtw.misskeen_app.R;

/**
 * Created by Paulina on 5/22/2017.
 */
public class ProfileFragment extends Fragment {
    private ImageView imageprofile;
    private TextView txtprofilename,txtprofilemail,txtprofiletelp,txtprofilealamat;


    public ProfileFragment() {
    }

    public static ProfileFragment newInstance() {

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_profile, container, false);
        return view;
    }
    private void getDataProfile() {
        UserObject userObject = new UserObject();
        userObject = getSavedObjectFromPreference(getContext(), "mPreference", "mObjectKey", UserObject.class);
        txtprofilename.setText(userObject.getFullname());
        txtprofilemail.setText(userObject.getEmail());
        txtprofiletelp.setText(userObject.getTelp());
        txtprofilealamat.setText(userObject.getAlamat());
    }

    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){
        imageprofile = (ImageView)getView().findViewById(R.id.imageprofile);
        txtprofilename = (TextView)getView().findViewById(R.id.profilename);
        txtprofilemail = (TextView)getView().findViewById(R.id.profilemail);
        txtprofiletelp=(TextView)getView().findViewById(R.id.profiletelp);
        txtprofilealamat=(TextView)getView().findViewById(R.id.profilealamat);
        getDataProfile();
    }
}
