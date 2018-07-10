package org.example.jluzio.playground.ui.samples;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.example.jluzio.playground.R;

public class SampleFragmentActivity extends AppCompatActivity implements SampleBodyFragment.OnBodyFragmentInteractionListener, SampleHeaderFragment.OnHeaderFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fragment);

        SampleHeaderFragment sampleHeaderFragment = SampleHeaderFragment.newInstance("nice title");
        SampleBodyFragment sampleBodyFragment = SampleBodyFragment.newInstance("nice body");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentHeader, sampleHeaderFragment)
                .replace(R.id.fragmentBody, sampleBodyFragment)
                .commit();

/*
        Fragment fragmentHeader = getSupportFragmentManager().findFragmentById(R.id.fragmentHeader);
        Fragment fragmentBody = getSupportFragmentManager().findFragmentById(R.id.fragmentBody);

        fragmentHeader.setArguments(getParamBundle("nice title"));
        fragmentBody.setArguments(getParamBundle("nice body"));
*/
    }

    private Bundle getParamBundle(String textValue) {
        Bundle bundle = new Bundle();
        bundle.putString("text", textValue);
        return bundle;
    }

    @Override
    public void onBodyFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHeaderFragmentInteraction(Uri uri) {

    }
}
