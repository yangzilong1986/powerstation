package com.hisun.service.imp;


import com.hisun.event.UpdateListener;
import com.hisun.service.UpdateListenerHub;
import edu.emory.mathcs.backport.java.util.concurrent.CopyOnWriteArrayList;

import java.util.List;

public class UpdateListenerHubImpl implements UpdateListenerHub {
    private final List _listeners;

    public UpdateListenerHubImpl() {

        this._listeners = new CopyOnWriteArrayList();
    }

    public void addUpdateListener(UpdateListener listener) {

        this._listeners.add(listener);
    }

    public void fireUpdateEvent() {

        for (int i = 0; i < this._listeners.size(); ++i)

            ((UpdateListener) this._listeners.get(i)).checkForUpdates();
    }
}