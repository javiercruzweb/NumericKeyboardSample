package mx.javiercruzweb.numerickeyboardsample.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import mx.javiercruzweb.numerickeyboardsample.R;

/**
 * Created by javiercruz on 14/09/17.
 */

public class NumericKeyboadDialogFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG = NumericKeyboadDialogFragment.class.getSimpleName();

    public interface CurrentNumberChangeListener extends Serializable {
        void onCurrentNumberChanged(int currentNumber);
    }

    private int currentNumber;
    private View rootView;
    private CurrentNumberChangeListener currentNumberChangeListener;

    //views
    private EditText inputNumber;
    private TextView t9_key_1;
    private TextView t9_key_2;
    private TextView t9_key_3;
    private TextView t9_key_4;
    private TextView t9_key_5;
    private TextView t9_key_6;
    private TextView t9_key_7;
    private TextView t9_key_8;
    private TextView t9_key_9;
    private TextView t9_key_0;
    private TextView t9_key_clear;
    private TextView t9_key_backspace;
    private TextView t9_key_acccept;

    public NumericKeyboadDialogFragment newInstance
            (int currentNumber, CurrentNumberChangeListener currentNumberChangeListener) {
        Bundle args = new Bundle();
        args.putInt("CurrentNumber", currentNumber);
        args.putSerializable("CurrentNumberChangeListener", currentNumberChangeListener);

        NumericKeyboadDialogFragment numericKeyboadDialogFragment = new NumericKeyboadDialogFragment();
        numericKeyboadDialogFragment.setArguments(args);
        return numericKeyboadDialogFragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentNumber = getArguments().getInt("CurrentNumber");
        currentNumberChangeListener = (CurrentNumberChangeListener)
                getArguments().getSerializable("CurrentNumberChangeListener");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.numeric_keyboard_view, container, false);
        setUI();
        return rootView;
    }

    private void setUI() {

        inputNumber = (EditText) rootView.findViewById(R.id.inputNumber);
        inputNumber.setText(String.valueOf(currentNumber));
        t9_key_1 = (TextView) rootView.findViewById(R.id.t9_key_1);
        t9_key_1.setOnClickListener(this);
        t9_key_2 = (TextView) rootView.findViewById(R.id.t9_key_2);
        t9_key_2.setOnClickListener(this);
        t9_key_3 = (TextView) rootView.findViewById(R.id.t9_key_3);
        t9_key_3.setOnClickListener(this);
        t9_key_4 = (TextView) rootView.findViewById(R.id.t9_key_4);
        t9_key_4.setOnClickListener(this);
        t9_key_5 = (TextView) rootView.findViewById(R.id.t9_key_5);
        t9_key_5.setOnClickListener(this);
        t9_key_6 = (TextView) rootView.findViewById(R.id.t9_key_6);
        t9_key_6.setOnClickListener(this);
        t9_key_7 = (TextView) rootView.findViewById(R.id.t9_key_7);
        t9_key_7.setOnClickListener(this);
        t9_key_8 = (TextView) rootView.findViewById(R.id.t9_key_8);
        t9_key_8.setOnClickListener(this);
        t9_key_9 = (TextView) rootView.findViewById(R.id.t9_key_9);
        t9_key_9.setOnClickListener(this);
        t9_key_0 = (TextView) rootView.findViewById(R.id.t9_key_0);
        t9_key_0.setOnClickListener(this);
        t9_key_clear = (TextView) rootView.findViewById(R.id.t9_key_clear);
        t9_key_clear.setOnClickListener(this);
        t9_key_backspace = (TextView) rootView.findViewById(R.id.t9_key_backspace);
        t9_key_backspace.setOnClickListener(this);
        t9_key_acccept = (TextView) rootView.findViewById(R.id.t9_key_accept);
        t9_key_acccept.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick!" + v.getTag());

        // handle number button click
        if (v.getTag() != null && "number_button".equals(v.getTag())) {
            inputNumber.append(((TextView) v).getText());
            return;
        }
        switch (v.getId()) {

            case R.id.t9_key_clear: { // handle clear button
                inputNumber.setText(null);
            }
            break;

            case R.id.t9_key_backspace: { // handle backspace button
                // delete one character
                Editable editable = inputNumber.getText();
                int charCount = editable.length();
                if (charCount > 0) {
                    editable.delete(charCount - 1, charCount);
                }
            }
            break;

            case R.id.t9_key_accept: { // handle accept button
                // send the current number
                if (inputNumber.getText().toString().isEmpty()
                        || Integer.parseInt(inputNumber.getText().toString()) < 1){
                    showSendZeroNumber();
                    return;
                }

                currentNumberChangeListener.onCurrentNumberChanged(
                        Integer.parseInt(inputNumber.getText().toString()));
            }
            break;

        }
    }

    private void showSendZeroNumber(){
        new AlertDialog.Builder(getContext())
                .setMessage("Send Zero number?")
                .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentNumberChangeListener.onCurrentNumberChanged(0);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
