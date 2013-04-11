/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jkan997.edgesvg.entity;

import org.json.JSONObject;

/**
 *
 * @author jakaniew
 */
public class TimelineEvent {

    public final static String COMPONENT_SHOW = "componentShow";
    public final static String COMPONENT_HIDE = "componentHide";
    private String type;
    private String componentId;
    private String time;

    public TimelineEvent(JSONObject json) {
        this.type = json.getString("type");
        this.componentId = json.getString("componentId");
        this.time = json.getString("time");
    }

    public String getType() {
        return type;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getTime() {
        return time;
    }

    public String getDisplayStr(boolean inverse) {
        boolean val = type.equals(COMPONENT_SHOW);
        if (inverse) {
            val = !val;
        }
        return (val ? "block" : "none");
    }

    public String getDisplayStr() {
        return getDisplayStr(true);
    }

    public boolean getDisplay() {
        boolean val = type.equals(COMPONENT_SHOW);
        return val;
    }

    @Override
    public String toString() {
        return "TimelineEvent{" + "type=" + type + ", componentId=" + componentId + ", time=" + time + '}';
    }
}
