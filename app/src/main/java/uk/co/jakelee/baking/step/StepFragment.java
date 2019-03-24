package uk.co.jakelee.baking.step;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.fragment.app.Fragment;
import uk.co.jakelee.baking.BuildConfig;
import uk.co.jakelee.baking.R;
import uk.co.jakelee.baking.recipe.RecipeActivity;
import uk.co.jakelee.baking.structure.Step;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link RecipeActivity}
 * in two-pane mode (on tablets) or a {@link StepActivity}
 * on handsets.
 */
public class StepFragment extends Fragment {

    public static final String STEP_EXTRA = "uk.co.jakelee.baking.step";
    private Step step;
    private ExoPlayer exoPlayer;

    public StepFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(STEP_EXTRA)) {
            step = getArguments().getParcelable(STEP_EXTRA);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(step.getShortDescription());
            }
            setHasOptionsMenu(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        if (step != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(step.getDescription());
            if (!step.getVideoURL().equals("")) {
                PlayerView videoPlayer = rootView.findViewById(R.id.videoPlayerView);
                videoPlayer.setVisibility(View.VISIBLE);
                exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity());
                videoPlayer.setPlayer(exoPlayer);
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(
                        rootView.getContext(),
                        Util.getUserAgent(rootView.getContext(), BuildConfig.APPLICATION_ID)
                );
                MediaSource videoSource = new ExtractorMediaSource
                        .Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(step.getVideoURL()));
                exoPlayer.prepare(videoSource);
            }
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null) {
            exoPlayer.release();
        }
    }
}
