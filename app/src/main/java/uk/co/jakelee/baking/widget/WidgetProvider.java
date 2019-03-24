package uk.co.jakelee.baking.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import uk.co.jakelee.baking.BuildConfig;
import uk.co.jakelee.baking.R;


public class WidgetProvider extends AppWidgetProvider {

    public static final String PREF_RECIPE_NAME = "recipe_name_";
    public static final String PREF_RECIPE_INGREDIENTS = "recipe_ingredients_";

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) { }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        SharedPreferences prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        CharSequence name = prefs.getString(WidgetProvider.PREF_RECIPE_NAME + widgetId, "name");
        CharSequence ingredients = prefs.getString(WidgetProvider.PREF_RECIPE_INGREDIENTS + widgetId, "ingredients");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.recipe_name, name);
        views.setTextViewText(R.id.recipe_ingredients, ingredients);

        appWidgetManager.updateAppWidget(widgetId, views);
    }
}
