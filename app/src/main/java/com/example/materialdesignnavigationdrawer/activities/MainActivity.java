package com.example.materialdesignnavigationdrawer.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.example.materialdesignnavdrawer.R;
import com.example.materialdesignnavigationdrawer.customViews.ScrimInsetsFrameLayout;
import com.example.materialdesignnavigationdrawer.fragments.ImageFragment;
import com.example.materialdesignnavigationdrawer.utils.UtilsDevice;
import com.example.materialdesignnavigationdrawer.utils.UtilsMiscellaneous;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private final static String sKEY_SAVED_POSITION = "savedPosition";
  private final static int sPOSITION_HOME = 0;
  private final static int sPOSITION_EXPLORE = 1;
  private static final int sDELAY_MILLIS = 250;

  private Toolbar mToolbar;
  private Context mContext;
  private ImageView mHomeIcon;
  private ImageView mExploreIcon;
  private FrameLayout mAccountRow;
  private DrawerLayout mDrawerLayout;
  private int mCurrentPosition = sPOSITION_HOME;
  private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
  private FrameLayout mHomeRow, mExploreRow, mHelpAndFeedbackRow, mAboutRow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    init(savedInstanceState);
  }

  private void init(@NonNull final Bundle savedInstanceState) {
    bindResources();
    setUpToolbar();
    setUpIcons();
    setUpDrawer();
    restoreState(savedInstanceState);
  }

  private void bindResources() {
    mContext = this;
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mAccountRow = (FrameLayout) findViewById(R.id.navigation_drawer_header);
    mHomeRow = (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_home);
    mExploreRow = (FrameLayout) findViewById
        (R.id.navigation_drawer_items_list_linearLayout_explore);
    mHelpAndFeedbackRow = (FrameLayout) findViewById
        (R.id.navigation_drawer_items_list_linearLayout_help_and_feedback);
    mAboutRow = (FrameLayout) findViewById
        (R.id.navigation_drawer_items_list_linearLayout_about);
    mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
    mHomeIcon = (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_home);
    mExploreIcon = (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_explore);
    mScrimInsetsFrameLayout = (ScrimInsetsFrameLayout)
        findViewById(R.id.main_activity_navigation_drawer_rootLayout);
  }

  private void setUpToolbar() {
    setSupportActionBar(mToolbar);
  }

  private void setUpIcons() {
    setColorStateList(mHomeIcon);
    setColorStateList(mExploreIcon);
  }

  private void setColorStateList(@NonNull final ImageView icon) {
    final Drawable homeDrawable = DrawableCompat.wrap(icon.getDrawable());
    DrawableCompat.setTintList
        (
            homeDrawable.mutate(),
            ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
        );

    icon.setImageDrawable(homeDrawable);
  }

  private void setUpDrawer() {
    setUpDrawerAffordance();
    setUpDrawerMaxWidth();
    setUpDrawerClickListeners();
  }

  private void setUpDrawerAffordance() {
    final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle
        (
            this,
            mDrawerLayout,
            mToolbar,
            R.string.navigation_drawer_opened,
            R.string.navigation_drawer_closed
        ) {
      @Override
      public void onDrawerSlide(View drawerView, float slideOffset) {
        // Disables the burger/arrow animation by default
        super.onDrawerSlide(drawerView, 0);
      }
    };

    mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();
  }

  private void setUpDrawerClickListeners() {
    mAccountRow.setOnClickListener(this);
    mHomeRow.setOnClickListener(this);
    mExploreRow.setOnClickListener(this);
    mHelpAndFeedbackRow.setOnClickListener(this);
    mAboutRow.setOnClickListener(this);
  }

  private void setUpDrawerMaxWidth() {
    final int probableMinDrawerWidth = UtilsDevice.getScreenWidthInPx(this) -
        UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);

    final int maxDrawerWidth = getResources()
        .getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

    mScrimInsetsFrameLayout.getLayoutParams().width =
        Math.min(probableMinDrawerWidth, maxDrawerWidth);
  }

  @Override
  public void onClick(View view) {
    mDrawerLayout.closeDrawer(GravityCompat.START);

    if (view == mAccountRow) {
      startActivityWithDelay(AccountActivity.class);
    } else {
      if (!view.isSelected()) {
        if (view == mHomeRow) {
          selectHomeFragment();
        } else if (view == mExploreRow) {
          selectExploreFragment();
        } else if (view == mHelpAndFeedbackRow) {
          startActivityWithDelay(HelpAndFeedbackActivity.class);
        } else if (view == mAboutRow) {
          startActivityWithDelay(AboutActivity.class);
        }
      }
    }
  }

  private void restoreState(final @Nullable Bundle savedInstanceState) {
    // This allow us to know if the activity was recreated
    // after orientation change and restore the Toolbar title
    if (savedInstanceState != null) {
      switch (savedInstanceState.getInt(sKEY_SAVED_POSITION, sPOSITION_HOME)) {
        case sPOSITION_HOME:
          selectHomeFragment();
          break;

        default:
          selectExploreFragment();
          break;

      }
    } else {
      selectHomeFragment();
    }
  }

  private void selectHomeFragment() {
    mCurrentPosition = sPOSITION_HOME;
    deselectRows();
    mHomeRow.setSelected(true);
    setToolbarTitle(R.string.toolbar_title_home);
    showImageFragment(ImageFragment.sIMAGE_NEO);
  }

  private void selectExploreFragment() {
    mCurrentPosition = sPOSITION_EXPLORE;
    deselectRows();
    mExploreRow.setSelected(true);
    setToolbarTitle(R.string.toolbar_title_explore);
    showImageFragment(ImageFragment.sIMAGE_MORPHEUS);
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

  private void deselectRows() {
    mHomeRow.setSelected(false);
    mExploreRow.setSelected(false);
  }

  @Override
  protected void onSaveInstanceState(final Bundle outState) {
    outState.putInt(sKEY_SAVED_POSITION, mCurrentPosition);
    super.onSaveInstanceState(outState);
  }
}
