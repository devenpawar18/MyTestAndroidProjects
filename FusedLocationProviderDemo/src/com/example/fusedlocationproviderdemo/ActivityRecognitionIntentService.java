package com.example.fusedlocationproviderdemo;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class ActivityRecognitionIntentService extends IntentService
{
	public ActivityRecognitionIntentService()
	{
		super("sdf sdf");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		// If the incoming intent contains an update
		if (ActivityRecognitionResult.hasResult(intent))
		{
			// Get the update
			ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
			// Get the most probable activity
			DetectedActivity mostProbableActivity = result.getMostProbableActivity();
			/*
			 * Get the probability that this activity is the the user's actual activity
			 */
			int confidence = mostProbableActivity.getConfidence();
			/*
			 * Get an integer describing the type of activity
			 */
			int activityType = mostProbableActivity.getType();
			String activityName = getNameFromType(activityType);
			/*
			 * At this point, you have retrieved all the information for the current update. You can
			 * display this information to the user in a notification, or send it to an Activity or
			 * Service in a broadcast Intent.
			 */
			Intent broadcastIntent = new Intent(ActivityRecognitionActivity.FILTER_STRING);
			broadcastIntent.putExtra("key", activityName);
			sendBroadcast(broadcastIntent);
		}
		else
		{
			/*
			 * This implementation ignores intents that don't contain an activity update. If you
			 * wish, you can report them as errors.
			 */
			Intent broadcastIntent = new Intent(ActivityRecognitionActivity.FILTER_STRING);
			broadcastIntent.putExtra("key", "Activity Error");
			sendBroadcast(broadcastIntent);
		}
	}

	/**
	 * Map detected activity types to strings
	 * 
	 * @param activityType
	 *            The detected activity type
	 * @return A user-readable name for the type
	 */
	private String getNameFromType(int activityType)
	{
		switch (activityType)
		{
			case DetectedActivity.IN_VEHICLE:
				return "In Vehicle";
			case DetectedActivity.ON_BICYCLE:
				return "On Bicycle";
			case DetectedActivity.ON_FOOT:
				return "Walking";
			case DetectedActivity.STILL:
				return "Still";
			case DetectedActivity.UNKNOWN:
				return "unknown";
			case DetectedActivity.TILTING:
				return "Changing Activity/Tilting";
		}
		return "unknown";
	}
}
