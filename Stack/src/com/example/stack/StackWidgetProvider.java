package com.example.stack;

	import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

	public class StackWidgetProvider extends AppWidgetProvider {
	    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
	    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";

	    @Override
	    public void onDeleted(Context context, int[] appWidgetIds) {
	        super.onDeleted(context, appWidgetIds);
	    }

	    @Override
	    public void onDisabled(Context context) {
	        super.onDisabled(context);
	    }

	    @Override
	    public void onEnabled(Context context) {
	        super.onEnabled(context);
	    }

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
	        if (intent.getAction().equals(TOAST_ACTION)) {
	            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
	                    AppWidgetManager.INVALID_APPWIDGET_ID);
	            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
	            //onNavigate(context,appWidgetId, viewIndex);
	            Toast.makeText(context.getApplicationContext(), "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
	        }
	        super.onReceive(context, intent);
	    }
	    
	    protected void onNavigate(Context context, Integer widgetId, Integer id) {  
	        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	        RemoteViews root = new RemoteViews(context.getPackageName(), R.layout.stackwidgetinfo);
	            root.showPrevious(R.id.stack_view);
	        appWidgetManager.updateAppWidget(widgetId, root);
	    }
	   
/*
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        Bundle extras = intent.getExtras();
	        Integer id = (Integer) (extras == null ? null : extras.get(TRIGGER));
	        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action) && id != null) {
	            int widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
	            onNavigate(context, widgetId, id); 
	        } else {
	            super.onReceive(context, intent);
	        }
	    }


	    protected void onNavigate(Context context, Integer widgetId, Integer id) {  
	        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
	        RemoteViews root = new RemoteViews(context.getPackageName(), R.layout.stackwidgetinfo);
	        if (id == R.id.left) {
	            root.showPrevious(R.id.scroll);
	        } else {
	            root.showNext(R.id.scroll);            
	        }
	        appWidgetManager.updateAppWidget(widgetId, root);
	    }
	    
*/
	    
	    @SuppressWarnings("deprecation")
		@Override
	    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
	        // update each of the widgets with the remote adapter
	    	Toast.makeText(context.getApplicationContext(), "Touched view " , Toast.LENGTH_SHORT).show();
	    	Log.e("Tag", "ENTER ONUPDATE");
	        for (int i = 0; i < appWidgetIds.length; ++i) {
	        	Toast.makeText(context, "Touched view " , Toast.LENGTH_SHORT).show();

	            // Here we setup the intent which points to the StackViewService which will
	            // provide the views for this collection.
	            Intent intent = new Intent(context, StackWidgetService.class);
	            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
	            // When intents are compared, the extras are ignored, so we need to embed the extras
	            // into the data so that the extras will not be ignored.
	            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
	            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
	            rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_view, intent);

	            // The empty view is displayed when the collection has no items. It should be a sibling
	            // of the collection view.
	            rv.setEmptyView(R.id.stack_view, R.id.empty_view);
	            

	            // Here we setup the a pending intent template. Individuals items of a collection
	            // cannot setup their own pending intents, instead, the collection as a whole can
	            // setup a pending intent template, and the individual items can set a fillInIntent
	            // to create unique before on an item to item basis.
	            Intent toastIntent = new Intent(context, StackWidgetProvider.class);
	            toastIntent.setAction(StackWidgetProvider.TOAST_ACTION);
	            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
	            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
	            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
	                    PendingIntent.FLAG_UPDATE_CURRENT);
	            rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent);

	            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
	        }
	        super.onUpdate(context, appWidgetManager, appWidgetIds);
	    }
	}