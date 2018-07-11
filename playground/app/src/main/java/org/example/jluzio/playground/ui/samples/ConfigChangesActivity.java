package org.example.jluzio.playground.ui.samples;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.example.jluzio.playground.R;

public class ConfigChangesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_changes);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        String orientationText = newConfig.orientation == Configuration.ORIENTATION_PORTRAIT ? "portrait" : "landscape";
        Toast.makeText(this, String.format("Switched to %s", orientationText), Toast.LENGTH_SHORT).show();
    }
}
