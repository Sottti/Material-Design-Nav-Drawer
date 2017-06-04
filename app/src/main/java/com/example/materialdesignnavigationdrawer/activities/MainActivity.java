package com.example.materialdesignnavigationdrawer.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.example.materialdesignnavdrawer.R;
import com.example.materialdesignnavigationdrawer.fragments.ImageFragment;

public class MainActivity extends AppCompatActivity {

  private final static String sKEY_ACTIONBAR_TITLE = "actionBarTitle";
  private static final int sDELAY_MILLIS = 250;

  private Toolbar mToolbar;
  private Context mContext;
  private DrawerLayout mDrawerLayout;
  private NavigationView mNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    init(savedInstanceState);
  }

  private void init(@Nullable final Bundle savedInstanceState) {
    bindResources();
    setUpToolbar();
    setUpDrawer();
    restoreState(savedInstanceState);
  }

  private void bindResources() {
    mContext = this;
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
    mNavigationView = (NavigationView) findViewById(R.id.activity_main_navigation_view);
  }

  private void setUpToolbar() {
    setSupportActionBar(mToolbar);

    final ActionBar actionBar = getSupportActionBar();

    if (actionBar != null) {
      actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  private void setUpDrawer() {
    mNavigationView
        .getHeaderView(0)
        .findViewById(R.id.navigation_drawer_header_clickable)
        .setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            startActivityWithDelay(AccountActivity.class);
          }
        });

    mNavigationView.setNavigationItemSelectedListener
        (
            new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(final MenuItem item) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

                switch (item.getItemId()) {
                  case R.id.navigation_view_item_home:
                    item.setChecked(true);
                    setToolbarTitle(R.string.toolbar_title_home);
                    showImageFragment(ImageFragment.sIMAGE_NEO);
                    break;

                  case R.id.navigation_view_item_explore:
                    setToolbarTitle(R.string.toolbar_title_explore);
                    showImageFragment(ImageFragment.sIMAGE_MORPHEUS);
                    item.setChecked(true);
                    break;

                  case R.id.navigation_view_item_help:
                    startActivityWithDelay(HelpAndFeedbackActivity.class);
                    break;

                  case R.id.navigation_view_item_about:
                    startActivityWithDelay(AboutActivity.class);
                    break;
                }

                return true;
              }
            }
        );
  }

  private void restoreState(final @Nullable Bundle savedInstanceState) {
    // This allow us to know if the activity was recreated
    // after orientation change and restore the Toolbar title
    if (savedInstanceState == null) {
      showDefaultFragment();
    } else {
      setToolbarTitle((String) savedInstanceState.getCharSequence(sKEY_ACTIONBAR_TITLE));
    }
  }

  private void showDefaultFragment() {
    setToolbarTitle(R.string.toolbar_title_home);
    showImageFragment(ImageFragment.sIMAGE_NEO);
  }

  private void showImageFragment(final int imageCode) {
    final Bundle bundle = getImageFragmentBundle(imageCode);
    replaceFragmentWithDelay(bundle);
  }

  /**
   * We start the transaction with delay to avoid junk while closing the drawer
   */
  private void replaceFragmentWithDelay(@NonNull final Bundle bundle) {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_activity_content_frame, ImageFragment.newInstance(bundle))
            .commit();
      }
    }, sDELAY_MILLIS);
  }

  @NonNull
  private Bundle getImageFragmentBundle(final int imageCode) {
    final Bundle bundle = new Bundle();
    bundle.putInt(ImageFragment.sARGUMENT_IMAGE_CODE, imageCode);
    return bundle;
  }

  private void setToolbarTitle(@StringRes final String title) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(title);
    }
  }

  private String getToolbarTitle() {
    if (getSupportActionBar() != null) {
      return (String) getSupportActionBar().getTitle();
    }

    return getString(R.string.app_name);
  }

  private void setToolbarTitle(@StringRes final int string) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(string);
    }
  }

  /**
   * We start this activities with delay to avoid junk while closing the drawer
   */
  private void startActivityWithDelay(@NonNull final Class activity) {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(mContext, activity));
      }
    }, sDELAY_MILLIS);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mDrawerLayout.openDrawer(GravityCompat.START);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    outState.putCharSequence(sKEY_ACTIONBAR_TITLE, getToolbarTitle());
    super.onSaveInstanceState(outState);
  }
}
