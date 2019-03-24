package uk.co.jakelee.baking.recipe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.jakelee.baking.R;
import uk.co.jakelee.baking.structure.Ingredient;
import uk.co.jakelee.baking.structure.Recipe;

public class RecipeActivity extends AppCompatActivity {

    public final static String RECIPE_EXTRA = "uk.co.jakelee.baking.recipe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_recipe);
        if (!getIntent().hasExtra(RECIPE_EXTRA)) {
            finish();
        }
        Recipe recipe = getIntent().getParcelableExtra(RECIPE_EXTRA);

        // Set title
        ((Toolbar)findViewById(R.id.toolbar)).setTitle(recipe.getName());

        // Set ingredients
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient : recipe.getIngredients()) {
            sb.append(ingredient.toString());
        }
        ((TextView)findViewById(R.id.recipe_steps)).setText(sb.toString());

        // Set steps
        RecyclerView recyclerView = findViewById(R.id.item_list);
        boolean isTwoPane = findViewById(R.id.item_detail_container) != null;
        recyclerView.setAdapter(new StepListAdapter(this, recipe, isTwoPane));
    }
}
