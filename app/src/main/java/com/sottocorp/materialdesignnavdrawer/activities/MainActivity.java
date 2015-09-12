package com.sottocorp.materialdesignnavdrawer.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.LinearLayout;

import com.sottocorp.materialdesignnavdrawer.R;
import com.sottocorp.materialdesignnavdrawer.customViews.ScrimInsetsFrameLayout;
import com.sottocorp.materialdesignnavdrawer.fragments.ImageFragment;
import com.sottocorp.materialdesignnavdrawer.utils.UtilsDevice;
import com.sottocorp.materialdesignnavdrawer.utils.UtilsMiscellaneous;

/**
 * Main class hosting the navigation drawer
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private final static double sNavigationDrawerAccountSectionAspectRatio = 9d/16d;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mNavDrawerEntriesRootView;
    private FrameLayout mFrameLayout_AccountView, mFrameLayout_Home,
            mFrameLayout_Explore, mFrameLayout_HelpAndFeedback, mFrameLayout_About;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }

    /**
     * Binds, creates and sets up the resources
     */
    private void initialise()
    {
        // Toolbar
        final Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        setUpIcons();

        // Layout resources
        mFrameLayout_AccountView =
                (FrameLayout) findViewById(R.id.navigation_drawer_account_view);

        mNavDrawerEntriesRootView =
                (LinearLayout)findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        mFrameLayout_Home =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_home);

        mFrameLayout_Explore =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_explore);

        mFrameLayout_HelpAndFeedback =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_help_and_feedback);

        mFrameLayout_About =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_about);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);

        ScrimInsetsFrameLayout mScrimInsetsFrameLayout =
                (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        final ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle
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

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        final int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        final int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width =
                Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * sNavigationDrawerAccountSectionAspectRatio);

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        mFrameLayout_Home.setOnClickListener(this);
        mFrameLayout_Explore.setOnClickListener(this);
        mFrameLayout_HelpAndFeedback.setOnClickListener(this);
        mFrameLayout_About.setOnClickListener(this);

        // Set the first item as selected for the first time
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(R.string.toolbar_title_home);
        }

        mFrameLayout_Home.setSelected(true);

        // Create the first fragment to be shown
        final Bundle bundle = new Bundle();
        bundle.putInt(ImageFragment.sARGUMENT_IMAGE_CODE, 0);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_content_frame, ImageFragment.newInstance(bundle))
                .commit();
    }

    @Override
    public void onClick(View view)
    {
        if (view == mFrameLayout_AccountView)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);

            // Start account/signup/signin
            startActivity(new Intent(view.getContext(), AccountActivity.class));
        }
        else
        {
            if (!view.isSelected())
            {
                onRowPressed((FrameLayout) view);

                if (view == mFrameLayout_Home)
                {
                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_home));
                    }

                    view.setSelected(true);

                    final Bundle bundle = new Bundle();
                    bundle.putInt(ImageFragment.sARGUMENT_IMAGE_CODE, 0);

                    // Insert the fragment by replacing any existing fragment
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_activity_content_frame, ImageFragment.newInstance(bundle))
                            .commit();
                }
                else if (view == mFrameLayout_Explore)
                {
                    if (getSupportActionBar() != null)
                    {
                        getSupportActionBar().setTitle(getString(R.string.toolbar_title_explore));
                    }

                    view.setSelected(true);

                    final Bundle bundle = new Bundle();
                    bundle.putInt(ImageFragment.sARGUMENT_IMAGE_CODE, 1);

                    // Insert the fragment by replacing any existing fragment
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_activity_content_frame, ImageFragment.newInstance(bundle))
                            .commit();
                }
                else if (view == mFrameLayout_HelpAndFeedback)
                {
                    // Start help/feedback acitvity
                    startActivity(new Intent(view.getContext(), HelpAndFeedbackActivity.class));
                }
                else if (view == mFrameLayout_About)
                {
                    // Show about activity
                    startActivity(new Intent(view.getContext(), AboutActivity.class));
                }
            }
            else
            {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        }
    }

    /**
     * Set up the rows when any is pressed
     *
     * @param pressedRow is the pressed row in the drawer
     */
    private void onRowPressed(@NonNull final FrameLayout pressedRow)
    {
        if (pressedRow.getTag() != getResources().getString(R.string.tag_nav_drawer_special_entry))
        {
            for (int i = 0; i < mNavDrawerEntriesRootView.getChildCount(); i++)
            {
                final View currentView = mNavDrawerEntriesRootView.getChildAt(i);

                final boolean currentViewIsMainEntry = currentView.getTag() ==
                        getResources().getString(R.string.tag_nav_drawer_main_entry);

                if (currentViewIsMainEntry)
                {
                    currentView.setSelected(currentView == pressedRow);
                }
            }
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * Sets a tint list to the icons
     *
     * Uses DrawableCompat to make it work before SKD 21
     */
    private void setUpIcons()
    {
        // Icons tint list
        final ImageView homeImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_home);
        final Drawable homeDrawable = DrawableCompat.wrap(homeImageView.getDrawable());
        DrawableCompat.setTintList
                (
                        homeDrawable.mutate(),
                        ContextCompat.getColorStateList(this, R.color.nav_drawer_row_icon)
                );

        homeImageView.setImageDrawable(homeDrawable);

        final ImageView exploreImageView =
                (ImageView) findViewById(R.id.navigation_drawer_items_list_icon_explore);
        final Drawable exploreDrawable = DrawableCompat.wrap(exploreImageView.getDrawable());
        DrawableCompat.setTintList
                (
                        exploreDrawable.mutate(),
                        ContextCompat.getColorStateList(this, R.color.nav_drawer_row_icon)
                );

        exploreImageView.setImageDrawable(exploreDrawable);
    }
}
