package com.example.materialdesignnavigationdrawer.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.percent.PercentRelativeLayout;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    public static final int sDELAY_MILLIS = 300;

    private static final int sIMAGE_NEO = 0;
    private static final int sIMAGE_MORPHEUS = 1;

    private Toolbar mToolbar;
    private Context mContext;
    private ImageView mHomeIcon;
    private ImageView mExploreIcon;
    private DrawerLayout mDrawerLayout;
    private PercentRelativeLayout mAccountRow;
    private ScrimInsetsFrameLayout mScrimInsetsFrameLayout;
    private FrameLayout mHomeRow, mExploreRow, mHelpAndFeedbackRow, mAboutRow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        bindResources();
        setUpToolbar();
        setUpIcons();
        setUpDrawer();
        setUpDefaultPosition();
    }

    private void bindResources()
    {
        mContext = this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAccountRow = (PercentRelativeLayout) findViewById
                (R.id.navigation_drawer_header);
        mHomeRow = (FrameLayout) findViewById
                (R.id.navigation_drawer_items_list_linearLayout_home);
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

    private void setUpToolbar()
    {
        setSupportActionBar(mToolbar);
    }

    private void setUpIcons()
    {
        setColorStateList(mHomeIcon);
        setColorStateList(mExploreIcon);
    }

    private void setColorStateList(@NonNull final ImageView icon)
    {
        final Drawable homeDrawable = DrawableCompat.wrap(icon.getDrawable());
        DrawableCompat.setTintList
                (
                        homeDrawable.mutate(),
                        ContextCompat.getColorStateList(this, R.color.nav_drawer_icon)
                );

        icon.setImageDrawable(homeDrawable);
    }

    private void setUpDrawer()
    {
        final ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                )
        {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        final int probableMinDrawerWidth = UtilsDevice.getScreenWidthInPx(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);

        final int maxDrawerWidth = getResources()
                .getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width =
                Math.min(probableMinDrawerWidth, maxDrawerWidth);

        mAccountRow.setOnClickListener(this);
        mHomeRow.setOnClickListener(this);
        mExploreRow.setOnClickListener(this);
        mHelpAndFeedbackRow.setOnClickListener(this);
        mAboutRow.setOnClickListener(this);
    }

    private void setUpDefaultPosition()
    {
        setToolbarTitle(R.string.toolbar_title_home);
        mHomeRow.setSelected(true);
        showImageFragment(sIMAGE_NEO);
    }

    @Override
    public void onClick(View view)
    {
        mDrawerLayout.closeDrawer(GravityCompat.START);

        if (view == mAccountRow)
        {
            startActivityWithDelay(AccountActivity.class);
        }
        else
        {
            if (!view.isSelected())
            {
                if (view == mHomeRow)
                {
                    deselectRows();
                    view.setSelected(true);
                    setToolbarTitle(R.string.toolbar_title_home);
                    showImageFragment(sIMAGE_NEO);
                }
                else if (view == mExploreRow)
                {
                    deselectRows();
                    view.setSelected(true);
                    setToolbarTitle(R.string.toolbar_title_explore);
                    showImageFragment(sIMAGE_MORPHEUS);
                }
                else if (view == mHelpAndFeedbackRow)
                {
                    startActivityWithDelay(HelpAndFeedbackActivity.class);
                }
                else if (view == mAboutRow)
                {
                    startActivityWithDelay(AboutActivity.class);
                }
            }
        }
    }

    private void showImageFragment(final int imageCode)
    {
        final Bundle bundle = getImageFragmentBundle(imageCode);
        replaceFragmentWithDelay(bundle);
    }

    /**
     * We start the transaction with delay to avoid junk while closing the drawer
     */
    private void replaceFragmentWithDelay(@NonNull final Bundle bundle)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_activity_content_frame, ImageFragment.newInstance(bundle))
                        .commit();
            }
        }, sDELAY_MILLIS);
    }

    @NonNull
    private Bundle getImageFragmentBundle(final int imageCode)
    {
        final Bundle bundle = new Bundle();
        bundle.putInt(ImageFragment.sARGUMENT_IMAGE_CODE, imageCode);
        return bundle;
    }

    private void setToolbarTitle(@StringRes final int string)
    {
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(string);
        }
    }

    /**
     * We start this activities with delay to avoid junk while closing the drawer
     */
    private void startActivityWithDelay(@NonNull final Class activity)
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                startActivity(new Intent(mContext, activity));
            }
        }, sDELAY_MILLIS);
    }

    private void deselectRows()
    {
        mHomeRow.setSelected(false);
        mExploreRow.setSelected(false);
    }
}
