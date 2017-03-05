package com.windhoek.hackathon.schoolfinder;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.windhoek.hackathon.schoolfinder.Model.SchoolObject;


public class SchoolProfilePopup implements View.OnClickListener {
    private static final String TAG = "SchoolProfile";
    private Dialog dialog;
    private MainActivity mainActivity;
    private ImageView submissionImage;
    private ImageButton popupButtonPositive;
    private TextView schoolPhone;
    private TextView schoolAddress;
    private TextView schoolName;
    private String name;
    private LinearLayout exitLinear;
    private SchoolObject schoolObject;

    public SchoolProfilePopup(String name, MainActivity mainActivity) {
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
        this.schoolAddress = (TextView) this.dialog.findViewById(R.id.submissionSubmitterName);
        this.schoolPhone = (TextView) this.dialog.findViewById(R.id.submissionDate);
        this.popupButtonPositive = (ImageButton) this.dialog.findViewById(R.id.popup_button_positive);
        this.exitLinear = (LinearLayout) this.dialog.findViewById(R.id.exitLinear);
        exitLinear.setOnClickListener(this);
        //this.loadingSpinny = (ProgressBar) this.dialog.findViewById(R.id.progressBarSubmissionPopup);
        //this.mainView = this.dialog.findViewById(R.id.popupMainContent);
        this.schoolName = (TextView) this.dialog.findViewById(R.id.submissionTitle);
        findSchoolObject();

        this.schoolName.setText(schoolObject.getName());
        this.schoolName.setTypeface(null, Typeface.BOLD);

        this.schoolAddress.setText(schoolObject.getAddress());
        this.schoolPhone.setText(schoolObject.getPhoneNumber());
        this.schoolPhone.setTextColor(Color.parseColor("#0000FF"));
        this.schoolPhone.setTypeface(null, Typeface.BOLD);
        this.schoolPhone.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        this.schoolPhone.setOnClickListener(this);

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
            case R.id.submissionDate:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + schoolObject.getPhoneNumber()));
                mainActivity.startActivity(intent);
                break;
        }
    }

}