package uk.co.jakelee.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import uk.co.jakelee.baking.BuildConfig;
import uk.co.jakelee.baking.R;


public class WidgetProvider extends AppWidgetProvider {

    public static final String PREF_RECIPE_NAME = "recipe_name_";
    public static final String PREF_RECIPE_INGREDIENTS = "recipe_ingredients_";

    static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_inside);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId); // AppWidgetManager.EXTRA_APPWIDGET_ID
        views.setRemoteAdapter(appWidgetId, R.id.listview, intent);

        /*SharedPreferences prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        CharSequence name = prefs.getString(WidgetProvider.PREF_RECIPE_NAME + appWidgetId, "name");
        CharSequence ingredients = prefs.getString(WidgetProvider.PREF_RECIPE_INGREDIENTS + appWidgetId, "ingredients");

        views.setTextViewText(R.id.recipe_name, name);
        views.setTextViewText(R.id.recipe_ingredients, ingredients);*/

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);


        }

    }
}
