package com.michaelkarchercalling.callersclock;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by mkarcher on 5/13/15.
 */
public class DanceList
{
    private LinkedList<Dance> dances;

    public DanceList()
    {
        dances = new LinkedList<Dance>();
    }

    public int count(String type)
    {
        ListIterator<Dance> danceListIterator = dances.listIterator();
        int n = 0;

        while (danceListIterator.hasNext())
        {
            if (danceListIterator.next().getType().equals(type))
                n++;
        }

        return n;
    }

    public Dance addDance(String type, Date start)
    {
        int n = count(type);
        Dance dance = new Dance(type, n);
        dance.addStart(start);

        dances.addLast(dance);

        return dance;
    }

    @Override
    public String toString()
    {
        ListIterator<Dance> danceListIterator = dances.listIterator();
        Dance d;
        String result = "";

        while (danceListIterator.hasNext())
        {
            d = danceListIterator.next();
            result = result + d.toString() + "\n";
        }

        return result;
    }
}
