package com.hisun.service;

import com.hisun.event.UpdateListener;

public abstract interface UpdateListenerHub {
    public abstract void addUpdateListener(UpdateListener paramUpdateListener);

    public abstract void fireUpdateEvent();
}