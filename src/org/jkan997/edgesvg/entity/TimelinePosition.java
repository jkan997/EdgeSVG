/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.entity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jakaniew
 */
public class TimelinePosition {

    private List<TimelineEvent> events = new ArrayList<TimelineEvent>();
    private String time;

    public TimelinePosition(JSONObject json) {
        time = json.getString("time");
        JSONArray eventsArr = json.getJSONArray("events");
        for (int i = 0; i < eventsArr.length(); i++) {
            JSONObject teJson = eventsArr.getJSONObject(i);
            TimelineEvent te = new TimelineEvent(teJson);
            events.add(te);
        }
    }

    public List<TimelineEvent> getEvents() {
        return events;
    }

    
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("TimelinePosition{" + "time=" + time + "}\n");
        for (TimelineEvent te : events) {
            res.append("     * ");
            res.append(te.toString());
            res.append("\n");
        }
        return res.toString();
    }
}
