package uk.co.jakelee.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import uk.co.jakelee.baking.BuildConfig;
import uk.co.jakelee.baking.R;

public class WidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private int widgetId;

    public WidgetViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        // no-op
    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        SharedPreferences prefs = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        CharSequence name = prefs.getString(WidgetProvider.PREF_RECIPE_NAME + widgetId, "name");
        CharSequence ingredients = prefs.getString(WidgetProvider.PREF_RECIPE_INGREDIENTS + widgetId, "ingredients");

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_inside);
        views.setTextViewText(R.id.recipe_name, name);
        views.setTextViewText(R.id.recipe_ingredients, ingredients);

        return(views);
    }

    @Override
    public RemoteViews getLoadingView() {
        return(null);
    }

    @Override
    public int getViewTypeCount() {
        return(1);
    }

    @Override
    public long getItemId(int position) {
        return(position);
    }

    @Override
    public boolean hasStableIds() {
        return(true);
    }

    @Override
    public void onDataSetChanged() {
        // no-op
    }
}
