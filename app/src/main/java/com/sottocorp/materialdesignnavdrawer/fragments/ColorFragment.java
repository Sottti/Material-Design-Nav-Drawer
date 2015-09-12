package com.sottocorp.materialdesignnavdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sottocorp.materialdesignnavdrawer.R;

/**
 * Fragment showing a solid background color
 */
public class ColorFragment extends Fragment
{
    public static final String sARGUMENT_IMAGE_CODE = "image";

    public static ColorFragment newInstance(Bundle bundle)
    {
        final ColorFragment colorFragment = new ColorFragment();

        if (bundle != null)
        {
            colorFragment.setArguments(bundle);
        }

        return colorFragment;
    }

    @Override
    public View onCreateView
    (
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    )
    {
        // The last two arguments ensure LayoutParams are inflated properly
        final View view = inflater.inflate(R.layout.colored_fragment, container, false);
        initialise(view);

        return view;
    }

    /**
     * Creates, binds and sets up the resources
     *
     * @param view is the view to get the bindings, context...
     */
    private void initialise(@NonNull final View view)
    {
        switch (getArguments().getInt(sARGUMENT_IMAGE_CODE))
        {
            case 0:
                view.findViewById(R.id.neo).setVisibility(View.VISIBLE);
                view.findViewById(R.id.morpheo).setVisibility(View.GONE);
                break;

            case 1:
                view.findViewById(R.id.neo).setVisibility(View.GONE);
                view.findViewById(R.id.morpheo).setVisibility(View.VISIBLE);
                break;

        }
    }
}
