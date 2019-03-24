package uk.co.jakelee.baking.util;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.jakelee.baking.R;
import uk.co.jakelee.baking.list.RecipeListAdapter;
import uk.co.jakelee.baking.structure.Recipe;
import uk.co.jakelee.baking.widget.WidgetConfigureActivity;

public class RecipeFetcher {
    public static void populateAppRecipes(final Activity activity, final RecyclerView recyclerView,
                                          final ContentLoadingProgressBar spinner, final boolean isTwoPane) {
        Request request = new Request.Builder()
                .url(activity.getString(R.string.recipe_url))
                .build();
        new OkHttpHelper().httpClient(recyclerView.getContext()).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                displayError(activity, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    displayError(activity, response.message());
                    return;
                }
                Type recipeListType = new TypeToken<List<Recipe>>() {}.getType();
                final List<Recipe> recipes = new Gson().fromJson(response.body().string(), recipeListType);
                final GridLayoutManager glm = new GridLayoutManager(
                        recyclerView.getContext(),
                        isTwoPane ? 4 : 2,
                        RecyclerView.VERTICAL,
                        false);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setLayoutManager(glm);
                        recyclerView.setAdapter(new RecipeListAdapter(recipes));
                        spinner.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    public static void populateWidgetRecipes(final WidgetConfigureActivity activity) {
        Request request = new Request.Builder()
                .url(activity.getString(R.string.recipe_url))
                .build();
        new OkHttpHelper().httpClient(activity).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                displayError(activity, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    displayError(activity, response.message());
                    return;
                }
                Type recipeListType = new TypeToken<List<Recipe>>() {}.getType();
                final List<Recipe> recipes = new Gson().fromJson(response.body().string(), recipeListType);
                activity.populatePicker(recipes);
            }
        });
    }

    private static void displayError(final Activity activity, final String error) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
