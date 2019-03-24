package uk.co.jakelee.baking.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import uk.co.jakelee.baking.BuildConfig;
import uk.co.jakelee.baking.R;
import uk.co.jakelee.baking.structure.Ingredient;
import uk.co.jakelee.baking.structure.Recipe;
import uk.co.jakelee.baking.util.RecipeFetcher;

public class WidgetConfigureActivity extends Activity {
    private List<String> names;
    private List<List<Ingredient>> ingredients;
    int widgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    Spinner spinner;

    public WidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_config);

        spinner = findViewById(R.id.recipe_picker);

        findViewById(R.id.save).setOnClickListener(mOnClickListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
        RecipeFetcher.populateWidgetRecipes(this);
    }

    public void populatePicker(List<Recipe> recipeList) {
        names = new ArrayList<>();
        ingredients = new ArrayList<>();
        for (Recipe recipe: recipeList) {
            names.add(recipe.getName());
            ingredients.add(recipe.getIngredients());
        }
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spinner.setAdapter(dataAdapter);
            }
        });
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = WidgetConfigureActivity.this;
            String recipeName = names.get(spinner.getSelectedItemPosition());
            StringBuilder sb = new StringBuilder();
            for (Ingredient ingredient : ingredients.get(spinner.getSelectedItemPosition())) {
                sb.append(ingredient.toString());
            }

            context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
                    .edit()
                    .putString(WidgetProvider.PREF_RECIPE_NAME + widgetId, recipeName)
                    .putString(WidgetProvider.PREF_RECIPE_INGREDIENTS + widgetId, sb.toString())
                    .apply();

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            WidgetProvider.updateAppWidget(context, appWidgetManager, widgetId);

            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };
}