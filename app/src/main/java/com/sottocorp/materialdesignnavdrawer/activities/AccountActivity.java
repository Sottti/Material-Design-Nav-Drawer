package com.sottocorp.materialdesignnavdrawer.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sottocorp.materialdesignnavdrawer.R;

/**
 * Represents other activity different from the main activity
 */
public class AccountActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_activity);

        initialise();
    }

    /**
     * Creates, binds and sets up the resources
     */
    private void initialise()
    {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.toolbar_title_account);
        }
    }
}
