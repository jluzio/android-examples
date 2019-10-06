package org.example.jluzio.playground.ui.samples;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.example.jluzio.playground.R;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;

public class WorkManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);

        Data inputData = new Data.Builder()
                .putString("text", "go go go tasks!!")
                .build();
        WorkRequest workRequest = new OneTimeWorkRequest.Builder(LogMessageWorker.class)
                .setInputData(inputData)
                .build();
        WorkManager.getInstance().enqueue(workRequest);
    }

    public static class LogMessageWorker extends Worker {
        private static final String TAG = "LogMessageWorker";
        public static final String DATA_TEXT = "text";

        @NonNull
        @Override
        public Result doWork() {
            Log.d(TAG, "doWork");
            String textValue = getInputData().getString(DATA_TEXT, "-null-");
            Log.d(TAG, "doWork: " + textValue);
            return Result.SUCCESS;
        }
    }
}
