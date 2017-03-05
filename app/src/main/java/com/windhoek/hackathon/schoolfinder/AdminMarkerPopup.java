package com.windhoek.hackathon.schoolfinder;
import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;


public class AdminMarkerPopup implements View.OnClickListener {
    private static final String TAG = "SchoolProfile";
    private Cursor queryCursor;
    private String adminMarkerId;
    private Dialog dialog;
    private MainActivity mainActivity;
    private ImageView submissionImage;
    private ImageButton popupButtonPositive;
    private TextView submissionDescription;
    private TextView submissionDate;
    private ProgressBar loadingSpinny;
    private TextView submissionSubmitterName;
    private View mainView;
    private TextView submissionTitle;
    private String name;
    private LinearLayout exitLinear;
    private SchoolObject schoolObject;

    public AdminMarkerPopup(String name, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.name = name;
    }

    public void createPopupTest() {

        this.dialog = new Dialog(mainActivity);
        // Include dialog.xml file
        this.dialog.setContentView(R.layout.school_profile_popup);
        // find views
        this.submissionImage = (ImageView) this.dialog.findViewById(R.id.submissionImageMain);
       // this.submissionDescription = (TextView) this.dialog.findViewById(R.id.reportDescription);
        this.submissionSubmitterName = (TextView) this.dialog.findViewById(R.id.submissionSubmitterName);
        this.submissionDate = (TextView) this.dialog.findViewById(R.id.submissionDate);
        this.popupButtonPositive = (ImageButton) this.dialog.findViewById(R.id.popup_button_positive);
        this.exitLinear = (LinearLayout) this.dialog.findViewById(R.id.exitLinear);
        exitLinear.setOnClickListener(this);
        //this.loadingSpinny = (ProgressBar) this.dialog.findViewById(R.id.progressBarSubmissionPopup);
        //this.mainView = this.dialog.findViewById(R.id.popupMainContent);
        this.submissionTitle = (TextView) this.dialog.findViewById(R.id.submissionTitle);
        findSchoolObject();

        this.submissionTitle.setText(schoolObject.getName());
        this.submissionSubmitterName.setText(schoolObject.getAddress());
        this.submissionDate.setText(schoolObject.getPhoneNumber());

        // set click listeners
        this.popupButtonPositive.setOnClickListener(this);
        this.submissionImage.setOnClickListener(this);
        this.submissionImage.setOnClickListener(this);

        this.dialog.show();
    }

    private void findSchoolObject() {
        for (SchoolObject o : DataHandlerSingleton.getDataHandlerSingleton().getSchoolObjects()) {
            if (o.getName().equals(name)) {
                schoolObject = o;
                Log.e(TAG, "findSchoolObject: FOUND" );
                break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popup_button_positive:
                dialog.dismiss();
                break;
            case R.id.exitLinear:
                dialog.dismiss();
                break;
        }
    }

}