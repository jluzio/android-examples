package org.example.jluzio.playground;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        boolean isStartActivity = true;
        if (isStartActivity) {
            Class<?> targetViewClass = getActivityClassByNavigationItem(id);
            if (targetViewClass != null) {
                startActivity(new Intent(this, targetViewClass));
            }
        } else {
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup container = findViewById(getLayoutByNavigationItem(id));
            inflater.inflate(R.layout.activity_main, container);

//            FrameLayout frameLayout = findViewById(R.id.content_main);

//            FragmentManager fragmentManager = getFragmentManager();
//            Fragment targetView = (Fragment) findViewById(getLayoutByNavigationItem(id));
//            fragmentManager.beginTransaction().replace(R.id.content_main, targetView).commit();

            //drawer.openDrawer(findViewById(id));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Object getActivityByNavigationItem(int id) {
        try {
            Class<?> activityClass = getActivityClassByNavigationItem(id);
            return activityClass != null ? activityClass.newInstance() : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class<?> getActivityClassByNavigationItem(int id) {
        Class<?> activityClass = null;
        if (id == R.id.nav_sample) {
            activityClass = SampleActivity.class;
        } else if (id == R.id.nav_sample_by_screen_size) {
            activityClass = SampleByScreenSizeActivity.class;
        } else if (id == R.id.nav_challenge20171127x01) {
            activityClass = Challenge20171127x01Activity.class;
        } else if (id == R.id.nav_challenge20171128x01) {
            activityClass = Challenge20171128x01Activity.class;
        }
        return activityClass;
    }

    public Integer getLayoutByNavigationItem(int id) {
        Integer layout = null;
        if (id == R.id.nav_sample) {
            layout = R.layout.activity_sample;
        } else if (id == R.id.nav_sample_by_screen_size) {
            layout = R.layout.activity_sample_by_screen_size;
        } else if (id == R.id.nav_challenge20171127x01) {
            layout = R.layout.activity_challenge20171127x01;
        } else if (id == R.id.nav_challenge20171128x01) {
            layout = R.layout.activity_challenge20171128x01;
        }
        return layout;
    }

}