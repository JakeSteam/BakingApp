package uk.co.jakelee.baking.list;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.jakelee.baking.recipe.RecipeActivity;
import uk.co.jakelee.baking.structure.Recipe;
import uk.co.jakelee.baking.structure.Step;
import uk.co.jakelee.baking.R;

public class RecipeListAdapter
        extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    private final List<Recipe> recipes;

    public RecipeListAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        Context context = holder.itemView.getContext();
        holder.recipeName.setText(recipe.getName());
        holder.recipeServings.setText(String.format(context.getString(R.string.recipe_servings), recipe.getServings()));
        holder.recipeSteps.setText(String.format(context.getString(R.string.recipe_steps), recipe.getSteps().size()));
        holder.recipeIngredients.setText(String.format(context.getString(R.string.recipe_ingredients), recipe.getIngredients().size()));
        String imagePath = getImagePath(recipe);
        if (imagePath != null) {
            Picasso.get()
                    .load(imagePath)
                    .into(holder.recipeImage);
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, RecipeActivity.class);
                intent.putExtra(RecipeActivity.RECIPE_EXTRA, recipe);
                context.startActivity(intent);
            }
        });
    }

    private String getImagePath(Recipe recipe) {
        if (!recipe.getImage().isEmpty()) {
            return recipe.getImage();
        }
        for (Step step : recipe.getSteps()) {
            if (!step.getThumbnailURL().isEmpty()) {
                return step.getThumbnailURL();
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final CardView card;
        final ImageView recipeImage;
        final TextView recipeName;
        final TextView recipeServings;
        final TextView recipeSteps;
        final TextView recipeIngredients;

        ViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.card_view);
            recipeImage = view.findViewById(R.id.recipe_image);
            recipeName = view.findViewById(R.id.recipe_name);
            recipeServings = view.findViewById(R.id.recipe_servings);
            recipeSteps = view.findViewById(R.id.recipe_steps);
            recipeIngredients = view.findViewById(R.id.recipe_ingredients);
        }
    }
}