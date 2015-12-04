package snapchattapp.texnlog.com.snapchatapp.Camera;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audiofx.BassBoost;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;

import snapchattapp.texnlog.com.snapchatapp.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends Activity {

    private CheckBox faceBox,eyeBox;
    private Spinner sharpSpinner, saturationSpinner, psizeSpinner, contrastSpinner, effectSpinner;
    private  HashMap paramsTable=new HashMap();
    private CameraParameters parameters=CameraParameters.getInstance();
    private String optionValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
       Intent intent=getIntent();
       String parameters=intent.getStringExtra("cameraparams");
       Log.d("nikos",parameters);

        paramsTable= parametersTohashmap(parameters);


    }
    public void onClickCheckBox(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        faceBox=(CheckBox)findViewById(R.id.faceBox);
        eyeBox=(CheckBox) findViewById(R.id.redeyeBox);
        switch (view.getId()) {
            case R.id.faceBox:
                if (checked) {
                    optionValue = "on";
                    parameters.setFacedetection(optionValue);
                } else {
                    faceBox.setChecked(false);
                    optionValue = "off";
                    parameters.setFacedetection(optionValue);
                }
            case R.id.redeyeBox:
                if (checked) {
                    optionValue = "enabled";
                    parameters.setRedeye(optionValue);
                } else {
                    eyeBox.setChecked(false);
                    optionValue = "disabled";
                    parameters.setRedeye(optionValue);
                }


        }
    }


    public HashMap parametersTohashmap(String parameters){
        HashMap<String,String> paramsTable=new HashMap<>();
        String[] pairs =parameters.split(";");
        String novalue="no value found";
        for(int i=0;i<pairs.length;i++){
            String pair=pairs[i];
            String[] keyvalue=pair.split("=");
           try{
               paramsTable.put(keyvalue[0],keyvalue[1]);
           }catch(ArrayIndexOutOfBoundsException e){
               paramsTable.put(keyvalue[0],novalue);
           }

        }

        return paramsTable;
    }


        }
    }
}
