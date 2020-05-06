package com.gmail.katsuya_iida.speechdemo;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.speech_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_recognizerintent:
                navigateToFragment(new RecognizerIntentFragment(), R.string.title_recognizerintent);
                return true;
            case R.id.nav_speechrecognizer:
                navigateToFragment(new SpeechRecognizerFragment(), R.string.title_speechrecognizer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToFragment(Fragment fragment, int stringId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(stringId);
    }
}
