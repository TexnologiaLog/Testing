package snapchattapp.texnlog.com.snapchatapp.fragments;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import snapchattapp.texnlog.com.snapchatapp.Camera.TestingCameraActivity;
import snapchattapp.texnlog.com.snapchatapp.R;

/**
 * Created by User on 22/11/2015.
 */
public class Picture_size_fragment extends Fragment {
    @Nullable

    private Camera.Parameters params=TestingCameraActivity.customCameraParam;

    private List<String> listStrSupportedPictureSizes = new ArrayList<String>();
    private final Spinner spinnerSupportedPictureSizes =(Spinner)getView().findViewById(R.id.supportedpicturesizes);




    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.picture_size_layout,container,false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);












//
//
//        for (int i=0; i < params.getSupportedPictureSizes().size(); i++){
//
//            String strSize = String.valueOf(i) + " : "
//                    + String.valueOf(params.getSupportedPictureSizes().get(i).height)
//                    + " x "
//                    + String.valueOf(params.getSupportedPictureSizes().get(i).width);
//            listStrSupportedPictureSizes.add(strSize);
//
//        }
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(),
//                android.R.layout.simple_spinner_item, listStrSupportedPictureSizes);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerSupportedPictureSizes.setAdapter(adapter);
//
//        spinnerSupportedPictureSizes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//
//                int f1 = params.getSupportedPictureSizes().get(position).height;
//                int f2 =params.getSupportedPictureSizes().get(position).width;
//
//                params.setPictureSize(f2, f1);
//
//
//            }
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


    }
}

