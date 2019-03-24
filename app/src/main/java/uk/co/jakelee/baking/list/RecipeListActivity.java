package uk.co.jakelee.baking.list;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;
import uk.co.jakelee.baking.R;
import uk.co.jakelee.baking.util.RecipeFetcher;

public class RecipeListActivity extends AppCompatActivity {
    private float TWO_PANE_MIN_WIDTH = 800f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        boolean isTwoPane = getWidthInDp() > TWO_PANE_MIN_WIDTH;
        RecipeFetcher.populateRecipes(
                this,
                (RecyclerView)findViewById(R.id.recipe_list),
                (ContentLoadingProgressBar) findViewById(R.id.recipe_list_loading),
                isTwoPane);
    }

    private float getWidthInDp() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return displayMetrics.widthPixels / displayMetrics.density;
    }
}
