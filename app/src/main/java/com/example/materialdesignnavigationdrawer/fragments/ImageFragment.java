package com.example.materialdesignnavigationdrawer.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.materialdesignnavdrawer.R;

public class ImageFragment extends Fragment {

  public static final int sIMAGE_NEO = 0;
  public static final int sIMAGE_MORPHEUS = 1;
  public static final String sARGUMENT_IMAGE_CODE = "image";

  public static ImageFragment newInstance(@NonNull final Bundle bundle) {
    final ImageFragment imageFragment = new ImageFragment();
    imageFragment.setArguments(bundle);
    return imageFragment;
  }

  @Override
  public View onCreateView
      (
          LayoutInflater inflater,
          @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState
      ) {
    final View view = inflater.inflate(R.layout.image_fragment, container, false);
    init(view);

    return view;
  }

  private void init(@NonNull final View view) {
    final ImageView imageView = (ImageView) view.findViewById(R.id.image);

    switch (getArguments().getInt(sARGUMENT_IMAGE_CODE)) {
      case 0:
        imageView.setImageDrawable
            (ContextCompat.getDrawable(getActivity(), R.drawable.neo));
        break;

      case 1:
        imageView.setImageDrawable
            (ContextCompat.getDrawable(getActivity(), R.drawable.morpheus));
        break;

    }
  }
}
