package com.michaelkarchercalling.callersclock;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import static android.os.SystemClock.elapsedRealtime;


public class MainActivity extends Activity
{
    private long startTime;
    private long pauseTime;
    private long missedTime;
    private boolean started, running;

    private DanceList danceList;
    private Dance currentDance;
    private String currentType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner typeSpinner = (Spinner) findViewById(R.id.dance_type);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        started = false;
        running = false;

        danceList = new DanceList();
        currentType = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void newDance(View view)
    {
        RelativeLayout stopwatchPane = (RelativeLayout) findViewById(R.id.stopwatch_pane);
        Chronometer stopwatch = (Chronometer) findViewById(R.id.stopwatch);
        //Button startButton = (Button) findViewById(R.id.start_dance);
        //Button endButton = (Button) findViewById(R.id.end_dance);
        //Button pauseButton  = (Button) findViewById(R.id.pause);
        //Button resumeButton  = (Button) findViewById(R.id.resume);
        Spinner dancePicker = (Spinner) findViewById(R.id.dance_type);
        TextView danceLabel = (TextView) findViewById(R.id.dance_label);
        int n;

        endDance(view);

        currentType = dancePicker.getSelectedItem().toString();
        n = danceList.count(currentType) + 1;

        danceLabel.setText(String.format("%s #%d", currentType, n));

        stopwatch.setBase(elapsedRealtime());
        stopwatchPane.setVisibility(View.VISIBLE);
    }

    public void startDance(View view)
    {
        Chronometer stopwatch = (Chronometer) findViewById(R.id.stopwatch);
        Button startButton = (Button) findViewById(R.id.start_dance);
        Button cancelButton = (Button) findViewById(R.id.cancel_dance);
        Button endButton = (Button) findViewById(R.id.end_dance);
        Button pauseButton  = (Button) findViewById(R.id.pause);

        startTime = elapsedRealtime();
        missedTime = 0;

        started = true;
        running = true;

        currentDance = danceList.addDance(currentType, new Date());

        stopwatch.setBase(startTime);
        stopwatch.start();

        startButton.setVisibility(View.GONE);
        cancelButton.setVisibility(View.GONE);
        endButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
    }

    public void endDance(View view)
    {
        RelativeLayout stopwatchPane = (RelativeLayout) findViewById(R.id.stopwatch_pane);
        Chronometer stopwatch = (Chronometer) findViewById(R.id.stopwatch);
        Button startButton = (Button) findViewById(R.id.start_dance);
        Button cancelButton = (Button) findViewById(R.id.cancel_dance);
        Button endButton = (Button) findViewById(R.id.end_dance);
        Button pauseButton  = (Button) findViewById(R.id.pause);
        Button resumeButton  = (Button) findViewById(R.id.resume);

        stopwatch.stop();
        stopwatchPane.setVisibility(View.INVISIBLE);

        if (running)
            currentDance.addStop(new Date());

        currentType = "";

        started = false;
        running = false;

        startButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        endButton.setVisibility(View.GONE);
        pauseButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.GONE);
    }

    public void pause(View view)
    {
        Chronometer stopwatch = (Chronometer) findViewById(R.id.stopwatch);
        Button pauseButton  = (Button) findViewById(R.id.pause);
        Button resumeButton  = (Button) findViewById(R.id.resume);

        stopwatch.stop();
        pauseTime = elapsedRealtime();

        currentDance.addStop(new Date());
        running = false;

        pauseButton.setVisibility(View.GONE);
        resumeButton.setVisibility(View.VISIBLE);
    }

    public void resume(View view)
    {
        Chronometer stopwatch = (Chronometer) findViewById(R.id.stopwatch);
        Button pauseButton  = (Button) findViewById(R.id.pause);
        Button resumeButton  = (Button) findViewById(R.id.resume);

        missedTime += elapsedRealtime() - pauseTime;
        stopwatch.setBase(startTime + missedTime);
        stopwatch.start();

        currentDance.addStart(new Date());
        running = true;

        pauseButton.setVisibility(View.VISIBLE);
        resumeButton.setVisibility(View.GONE);
    }
}
