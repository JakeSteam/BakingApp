package uk.co.jakelee.baking.recipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import uk.co.jakelee.baking.R;
import uk.co.jakelee.baking.step.StepFragment;
import uk.co.jakelee.baking.step.StepActivity;
import uk.co.jakelee.baking.structure.Recipe;
import uk.co.jakelee.baking.structure.Step;

public class StepListAdapter
        extends RecyclerView.Adapter<StepListAdapter.ViewHolder> {

    private final RecipeActivity mParentActivity;
    private final List<Step> steps;
    private final boolean mTwoPane;
    private final boolean isFirstStepIntro;

    StepListAdapter(RecipeActivity parent,
                    Recipe recipe,
                    boolean twoPane) {
        steps = recipe.getSteps();
        mParentActivity = parent;
        mTwoPane = twoPane;
        isFirstStepIntro = steps.get(0).getDescription().equals("Recipe Introduction");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_step, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Step step = steps.get(position);
        String imagePath = step.getThumbnailURL();
        if (!imagePath.isEmpty()) {
            Picasso.get()
                    .load(imagePath)
                    .into(holder.stepImage);
        }
        if (!isFirstStepIntro) {
            holder.stepNumber.setText(String.valueOf(position + 1));
            holder.stepDesc.setText(step.getDescription());
        } else if (position > 0) {
            holder.stepNumber.setText(String.valueOf(position));
            holder.stepDesc.setText(step.getDescription());
        }
        holder.stepTitle.setText(step.getShortDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putParcelable(StepFragment.STEP_EXTRA, step);
                    StepFragment fragment = new StepFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, StepActivity.class);
                    intent.putExtra(StepFragment.STEP_EXTRA, step);
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView stepImage;
        final TextView stepNumber;
        final TextView stepTitle;
        final TextView stepDesc;

        ViewHolder(View view) {
            super(view);
            stepImage = view.findViewById(R.id.step_image);
            stepNumber = view.findViewById(R.id.step_number);
            stepTitle = view.findViewById(R.id.step_title);
            stepDesc = view.findViewById(R.id.step_desc);
        }
    }
}