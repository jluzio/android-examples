package org.example.jluzio.playground;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ButtonCounterChallengeActivity extends AppCompatActivity {
    interface SavedStateKey {
        public static final String TEXT_VIEW = "ButtonCounterChallengeActivity.textView";
    }
    private EditText userInput;
    private Button submitButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }


}
