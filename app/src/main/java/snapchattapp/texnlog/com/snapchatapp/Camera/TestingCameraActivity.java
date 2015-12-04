package snapchattapp.texnlog.com.snapchatapp.Camera;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

import snapchattapp.texnlog.com.snapchatapp.UserConnection.MainActivity;
import snapchattapp.texnlog.com.snapchatapp.R;
import snapchattapp.texnlog.com.snapchatapp.UserConnection.UserLocalStore;


public  class TestingCameraActivity extends FragmentActivity{

    private static final String TAG ="Debug" ;
    private static final String PICTURE_TAKEN ="Picture" ;
    private  static   Camera customCamera=null;
    public static Camera.Parameters customCameraParam;
    private SurfaceView camPreview;
    private ImageButton btnCamera,btnPreviewImage,btnSettings;
    public static ImageView image;
    private File mediaStorageDir,mediaFile;
    private FrameLayout preview;
    private SeekBar zoomBar;
    LinearLayout layout;
    private ImageButton btnFrontCamera;
    private static int currentCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private Button btnLogout;
    private CameraParameters parameters;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_layout);
        state=CameraState.getCameraState();
        InitializeButtons();
        SettingUpButtonListeners();
        }

    public   static Camera getCameraInstance(){
        Camera c = null;
        try
        {   if(currentCameraId== Camera.CameraInfo.CAMERA_FACING_BACK)
        {
            currentCameraId= Camera.CameraInfo.CAMERA_FACING_FRONT;
        }
        else currentCameraId= Camera.CameraInfo.CAMERA_FACING_BACK;
            c = Camera.open(currentCameraId); //get a Camera instance

        }
        catch (Exception e){
            Log.d(TAG,"Camera not availlable");
            e.printStackTrace();
        }
        return c; // returns null if camera is unavailable
    }


    private void InitializeCameraPreview()
    {
        camPreview=new CameraPreview(this,customCamera);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(camPreview);

        preview.removeView(btnCamera);
        preview.addView(btnCamera);

        preview.removeView(zoomBar);
        preview.addView(zoomBar);



        preview.removeView(btnFrontCamera);
        preview.addView(btnFrontCamera);

        preview.removeView(btnLogout);
        preview.addView(btnLogout);

        preview.removeView(btnSettings);
        preview.addView(btnSettings);


    }

    private void InitializeButtons()
    {

        btnCamera=(ImageButton) findViewById(R.id.fab);
        btnPreviewImage=(ImageButton) findViewById(R.id.btnFlash);
        image=(ImageView) findViewById(R.id.image);
        zoomBar=(SeekBar) findViewById(R.id.zoomBar);
        layout=(LinearLayout) findViewById(R.id.cam_layout);
        btnFrontCamera=(ImageButton) findViewById(R.id.btnFrontCam);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnSettings=(ImageButton)findViewById(R.id.settings_button);
        mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "MyCameraApp");
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "Custom_"+ ".jpg");
        btnPreviewImage.setEnabled(false);

    }

    private void InitializeCamera()
    {
        String params;
        Camera.Size size;
        checkCameraHardware(this);
        customCamera=getCameraInstance();

        if(customCamera==null) Toast.makeText(this, "Camera not availlable", Toast.LENGTH_LONG).show();
        customCameraParam=customCamera.getParameters();
        customCameraParam.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        customCameraParam.setJpegQuality(100);
        params=customCameraParam.flatten();
        parameters.setParameters(params);
        zoomBar.setMax(customCameraParam.getMaxZoom());
        if(currentCameraId==Camera.CameraInfo.CAMERA_FACING_BACK) customCamera.setParameters(customCameraParam);
}
    private void SettingUpButtonListeners()
    {


        zoomBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                customCameraParam.setZoom(zoomBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                customCamera.setParameters(customCameraParam);
            }
        });



        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (LogOut())
                    Toast.makeText(getApplicationContext(), "Successfully Logged out", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TestingCameraActivity.this, MainActivity.class));
                System.exit(0);
            }
        });




        btnFrontCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customCamera.stopPreview();
                customCamera.release();

                InitializeCamera();
                try {
                    customCamera.setPreviewDisplay(camPreview.getHolder());
                    customCamera.setDisplayOrientation(90);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                customCamera.open();
                customCamera.startPreview();
            }
        });





        btnPreviewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked");
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TestingCameraActivity.this, PhotoPreview.class);
                intent.putExtra(PICTURE_TAKEN, mediaFile.getAbsolutePath());
                startActivity(intent);
                Log.d(TAG, "Starting Photo preview Activity");
            }


        });


        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked");
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
                customCamera.takePicture(null, null, null, new CameraCallback(customCamera));
                Log.d(TAG, "Returned from CameraCallback");
                btnPreviewImage.setEnabled(true);
                btnCamera.setEnabled(false);
                preview.removeView(btnPreviewImage);
                preview.addView(btnPreviewImage);

                preview.removeView(zoomBar);


                preview.removeView(zoomBar);

            }
        });
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Camera.Parameters params=customCamera.getParameters();
                String paramString=params.flatten();
                if(paramString==null){
                    Log.d(TAG,"blablabla");
                }else
                {
                    Log.d(TAG,"hehehehehe");
                }
                  Intent intent = new Intent(TestingCameraActivity.this, SettingsActivity.class);
               intent.putExtra("cameraparams",paramString);
                startActivity(intent);


            }
        });
    }




    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            Log.d(TAG,"Has Camera");
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(customCamera!=null) customCamera.release(); Log.d(TAG, "Camera Released OnDestroy");



    }

    @Override
    protected void onStop() {
        super.onStop();
        UserLocalStore tmp=MainActivity.userLocalStore;
        tmp.clearUserData();
        tmp.setUserLoggedIn(false);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        InitializeCamera();
        InitializeCameraPreview();
        btnPreviewImage.setEnabled(false);
        btnCamera.setEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();


        System.exit(0);
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP)
        {
            //torch on
            customCameraParam.setFlashMode("torch");
            customCamera.setParameters(customCameraParam);
        }
        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN)
        {
            //torch off
            customCameraParam.setFlashMode("off");
            customCamera.setParameters(customCameraParam);
        }
        return false;
    }


    private boolean LogOut() {
        UserLocalStore tmp= MainActivity.userLocalStore;
        tmp.clearUserData();
        tmp.setUserLoggedIn(false);
        if(tmp.getUserLoggedIn()) return false;
        return true;

    }
    }

