package com.mycompany.app.helloworld;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static final String GREETING_PREFIX = "Hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView view = (TextView)findViewById(R.id.greeting_message);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String greetingName = preferences.getString("greeting_name", "World").trim();

        view.setText(GREETING_PREFIX + " " + greetingName);

    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView view = (TextView)findViewById(R.id.greeting_message);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String greetingName = preferences.getString("greeting_name", "World").trim();

        view.setText(GREETING_PREFIX + " " + greetingName);

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
        if (id == R.id.action_settings) {

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
