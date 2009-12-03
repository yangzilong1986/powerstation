package com.hisun.framework.event;

public abstract interface IServerEventListener extends IServerInitListener, IServerStartListener, IServerStopListener, IServerDestroyListener, IServerPauseListener, IServerResumeListener
{
}