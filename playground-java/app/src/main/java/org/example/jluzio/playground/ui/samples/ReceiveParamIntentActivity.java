package org.example.jluzio.playground.ui.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.ui.MainActivity;

public class ReceiveParamIntentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_param_intent);

        String param = getIntent().getExtras().getString(MainActivity.INTENT_PARAM);
        TextView tvText = findViewById(R.id.tvText);
        tvText.setText(param);
    }
}
