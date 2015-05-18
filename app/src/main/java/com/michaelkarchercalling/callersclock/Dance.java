package com.michaelkarchercalling.callersclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

/**
 * Created by mkarcher on 5/13/15.
 */
public class Dance
{
    private String type;
    private int number;
    private LinkedList<Date> starts, stops;

    public Dance(String startType, int startNumber)
    {
        type = startType;
        number = startNumber;
        starts = new LinkedList<Date>();
        stops = new LinkedList<Date>();
    }

    public void addStart(Date start)
    {
        starts.addLast(start);
    }

    public void addStop(Date stop)
    {
        stops.addLast(stop);
    }

    public String getType()
    {
        return type;
    }

    public int getNumber()
    {
        return number;
    }

    public LinkedList<Date> getStarts()
    {
        return starts;
    }

    public LinkedList<Date> getStops()
    {
        return stops;
    }

    @Override
    public String toString()
    {
        String result = String.format("%s #%d", type, number);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm", Locale.US);

        ListIterator<Date> startIter = starts.listIterator();
        ListIterator<Date> stopIter = stops.listIterator();

        while (startIter.hasNext() && stopIter.hasNext())
        {
            result = result + String.format(" (%s, %s)", df.format(startIter.next()), df.format(stopIter.next()));
        }

        return result;
    }
}
