package mx.javiercruzweb.numerickeyboardsample.activities;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mx.javiercruzweb.numerickeyboardsample.R;
import mx.javiercruzweb.numerickeyboardsample.fragments.NumericKeyboadDialogFragment;

public class MainActivity extends AppCompatActivity {

    private static final String NUMERIC_KEYBOARD_DIALOG_FRAGMENT_TAG =
            "numeric_keyboard_dialog_fragment";
    private TextView textViewCurrentNumber;
    private Button buttonLaunchNumericKeyboard;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        setUI();
    }

    private void setUI(){
        textViewCurrentNumber = (TextView) findViewById(R.id.textViewCurrentNumber);
        buttonLaunchNumericKeyboard = (Button) findViewById(R.id.buttonLaunchNumericKeyboard);
        buttonLaunchNumericKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonLaunchNumericKeyboard();
            }
        });
    }

    private void onClickButtonLaunchNumericKeyboard(){
        showNumericKeyboardDialogFragment();
    }

    private void showNumericKeyboardDialogFragment(){

        NumericKeyboadDialogFragment numericKeyboadDialogFragment = (NumericKeyboadDialogFragment)
                getSupportFragmentManager().findFragmentByTag(NUMERIC_KEYBOARD_DIALOG_FRAGMENT_TAG);

        if (numericKeyboadDialogFragment == null) {
            FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
            numericKeyboadDialogFragment = new NumericKeyboadDialogFragment()
                    .newInstance(getCurrentNumber(),
                            new NumericKeyboadDialogFragment.CurrentNumberChangeListener() {
                                @Override
                                public void onCurrentNumberChanged(int currentNumber) {
                                    hideNumericKeyboardDialogFragment();
                                    Toast.makeText(mContext, "Current number changed! "
                                                    + currentNumber, Toast.LENGTH_SHORT).show();
                                    updateCurrentNumberView(currentNumber);
                                }
                    });
            numericKeyboadDialogFragment.setCancelable(false);
            numericKeyboadDialogFragment.show(fm, NUMERIC_KEYBOARD_DIALOG_FRAGMENT_TAG);
        }
    }

    private void hideNumericKeyboardDialogFragment(){
        Fragment numericKeyboadDialogFragment =
                getSupportFragmentManager().findFragmentByTag(NUMERIC_KEYBOARD_DIALOG_FRAGMENT_TAG);
        if (numericKeyboadDialogFragment != null) {
            DialogFragment df = (DialogFragment) numericKeyboadDialogFragment;
            df.dismiss();
        }
    }

    private int getCurrentNumber(){
        int currentNumber = 0;
        if (!textViewCurrentNumber.getText().toString().isEmpty()){
            currentNumber = Integer.parseInt(textViewCurrentNumber.getText().toString());
        }

        return currentNumber;
    }

    private void updateCurrentNumberView(int currentNumberUpdated){
        textViewCurrentNumber.setText(String.valueOf(currentNumberUpdated));
    }
}
