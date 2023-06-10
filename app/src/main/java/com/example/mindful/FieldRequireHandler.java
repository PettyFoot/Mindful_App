package com.example.mindful;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class FieldRequireHandler {

    ArrayList<View> requireDataViews;

    ArrayList<View> invalidViews;

    ArrayList<View> validViews;

    private String TAG = "FieldRequireHandler";

    public FieldRequireHandler(ArrayList<View> requireDataViews) {
        this.requireDataViews = requireDataViews;
        invalidViews = new ArrayList<View>();
        validViews = new ArrayList<View>();
    }

    public boolean ValidateFields(ArrayList<View> fieldsToValidate){
        boolean bIsValid = true;

        //check if argument passed, otherwise use existing
        if(fieldsToValidate != null){

            requireDataViews = fieldsToValidate;
        }

        //currently there is only textviews to handle
        for(View view: requireDataViews){

            TextView textView = (TextView) view;
            if(textView != null){

                if(!validData(textView)){

                    bIsValid = false;
                    invalidViews.add(textView);
                }else{

                    validViews.add(textView);
                }
            }
        }

        return bIsValid;
    }

    private boolean validData(TextView textView) {

        boolean bIsValid = true;

        //check for empty field, auto flag for invalid
        String text = textView.getText().toString();
        if(TextUtils.isEmpty(text)){
           return bIsValid = false;
        }

        //Is textView edit text?
        EditText editText = (EditText) textView;
        if(editText != null){
            //textView is EditText
            int inputType = textView.getInputType();

            switch (inputType){
                case InputType.TYPE_CLASS_TEXT:
                    // Handle input type: Text
                    break;
                case InputType.TYPE_CLASS_NUMBER:
                    // Handle input type: Number
                    break;
                case InputType.TYPE_CLASS_DATETIME:
                    // Handle input type: Date and Time
                    break;
                case InputType.TYPE_CLASS_PHONE:
                    // Handle input type: Phone Number
                    break;
                case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
                    // Handle input type: Email Address
                    break;
                case InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD:
                    // Handle input type: Password
                    break;
                // Add more cases for other input types as needed
                default:
                    // Handle other input types or unsupported input types
                    bIsValid = false;
                    break;

            }
        }else{

            //textView is not editText

        }

        return bIsValid;
    }

    public ArrayList<View> getInvalidViews() {
        return invalidViews;
    }

    public ArrayList<View> getValidViews() {
        return validViews;
    }
}
