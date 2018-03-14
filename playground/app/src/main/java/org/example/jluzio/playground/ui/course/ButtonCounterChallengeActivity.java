package org.example.jluzio.playground.ui.course;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.example.jluzio.playground.R;

public class ButtonCounterChallengeActivity extends AppCompatActivity {
    interface SavedStateKey {
        public static final String TEXT_VIEW = "ButtonCounterChallengeActivity.textView";
    }
    private EditText userInput;
    private Button submitButton;
    private TextView textView;
    private static final String TAG = "ButtonCounterChallengeA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_counter_challenge);

        userInput = findViewById(R.id.inputText);
        submitButton = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        userInput.setText("");
        textView.setText("");
//        if (savedInstanceState != null) {
//            textView.setText(savedInstanceState.getString(SavedStateKey.TEXT_VIEW, ""));
//        }
        textView.setMovementMethod(new ScrollingMovementMethod());

        submitButton.setOnClickListener(view -> {
            textView.append(String.format("%s%n", userInput.getText()));
            userInput.setText("");
        });

        Log.d(TAG, "onCreate: out");
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: in");
        super.onStart();
        Log.d(TAG, "onStart: out");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: in");
        super.onStop();
        Log.d(TAG, "onStop: out");
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: in");
        super.onDestroy();
        Log.d(TAG, "onDestroy: out");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: in");
        super.onPause();
        Log.d(TAG, "onPause: out");
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: in");
        super.onResume();
        Log.d(TAG, "onResume: out");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: in");
        super.onRestoreInstanceState(savedInstanceState);
        String savedValue = savedInstanceState.getString(SavedStateKey.TEXT_VIEW, "");
        textView.setText(savedValue);
        Log.d(TAG, "onRestoreInstanceState: out");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: in");
        super.onSaveInstanceState(outState);
        outState.putString(SavedStateKey.TEXT_VIEW, textView.getText().toString());
        Log.d(TAG, "onSaveInstanceState: out");
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: in");
        super.onRestart();
        Log.d(TAG, "onRestart: out");
    }
}
