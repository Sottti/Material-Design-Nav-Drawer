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
import android.widget.Toast;

import com.sottocorp.materialdesignnavdrawer.R;
import com.sottocorp.materialdesignnavdrawer.customViews.ScrimInsetsFrameLayout;
import com.sottocorp.materialdesignnavdrawer.fragments.ColorFragment;
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

        // Layout resources
        final FrameLayout mFrameLayout_AccountView =
                (FrameLayout) findViewById(R.id.navigation_drawer_account_view);

        mNavDrawerEntriesRootView =
                (LinearLayout)findViewById(R.id.navigation_drawer_linearLayout_entries_root_view);

        final FrameLayout frameLayout_Home =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_home);

        final FrameLayout frameLayout_Explore =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_explore);

        final FrameLayout frameLayout_HelpAndFeedback =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_help_and_feedback);

        final FrameLayout frameLayout_About =
                (FrameLayout) findViewById(R.id.navigation_drawer_items_list_linearLayout_about);

        // Navigation Drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_activity_DrawerLayout);
        mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.primaryDark));
        ScrimInsetsFrameLayout mScrimInsetsFrameLayout =
                (ScrimInsetsFrameLayout) findViewById(R.id.main_activity_navigation_drawer_rootLayout);

        final ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        mDrawerLayout,
                        mToolbar,
                        R.string.navigation_drawer_opened,
                        R.string.navigation_drawer_closed
                ) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                // Disables the burger/arrow animation by default
                super.onDrawerSlide(drawerView, 0);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        mActionBarDrawerToggle.syncState();

        // Navigation Drawer layout width
        final int possibleMinDrawerWidth = UtilsDevice.getScreenWidth(this) -
                UtilsMiscellaneous.getThemeAttributeDimensionSize(this, android.R.attr.actionBarSize);
        final int maxDrawerWidth = getResources().getDimensionPixelSize(R.dimen.navigation_drawer_max_width);

        mScrimInsetsFrameLayout.getLayoutParams().width = Math.min(possibleMinDrawerWidth, maxDrawerWidth);

        // Account section height
        mFrameLayout_AccountView.getLayoutParams().height = (int) (mScrimInsetsFrameLayout.getLayoutParams().width
                * sNavigationDrawerAccountSectionAspectRatio);

        // Nav Drawer item click listener
        mFrameLayout_AccountView.setOnClickListener(this);
        frameLayout_Home.setOnClickListener(this);
        frameLayout_Explore.setOnClickListener(this);
        frameLayout_HelpAndFeedback.setOnClickListener(this);
        frameLayout_About.setOnClickListener(this);

        // Set the first item as selected for the first time
        getSupportActionBar().setTitle(R.string.toolbar_title_home);
        frameLayout_Home.setSelected(true);

        // Create the first fragment to be shown
        final Bundle bundle = new Bundle();
        bundle.putInt(ColorFragment.sARGUMENT_IMAGE_CODE, 0);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                .commit();
    }

    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.navigation_drawer_account_view)
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            Toast.makeText(this, "To Account/SignUp/SignIn", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (!view.isSelected())
            {
                onRowPressed((FrameLayout) view);

                switch (view.getId())
                {
                    case R.id.navigation_drawer_items_list_linearLayout_home:
                    {
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_home));
                        }

                        view.setSelected(true);

                        final Bundle bundle = new Bundle();
                        bundle.putInt(ColorFragment.sARGUMENT_IMAGE_CODE, 0);

                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                                .commit();
                        break;
                    }

                    case R.id.navigation_drawer_items_list_linearLayout_explore:
                    {
                        if (getSupportActionBar() != null)
                        {
                            getSupportActionBar().setTitle(getString(R.string.toolbar_title_explore));
                        }

                        view.setSelected(true);

                        final Bundle bundle = new Bundle();
                        bundle.putInt(ColorFragment.sARGUMENT_IMAGE_CODE, 1);

                        // Insert the fragment by replacing any existing fragment
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_activity_content_frame, ColorFragment.newInstance(bundle))
                                .commit();
                        break;
                    }

                    case R.id.navigation_drawer_items_list_linearLayout_help_and_feedback:
                        // Start intent to send an email
                        startActivity(new Intent(view.getContext(), OtherActivity.class));
                        break;

                    case R.id.navigation_drawer_items_list_linearLayout_about:
                        // Show about activity
                        startActivity(new Intent(view.getContext(), OtherActivity.class));
                        break;

                    default:
                        break;
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
}
