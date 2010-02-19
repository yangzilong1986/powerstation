package com.hzjbbis.fk.sockclient.async.simulator;

import com.hzjbbis.fk.message.IMessage;
import com.hzjbbis.fk.sockclient.async.JAsyncSocket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class SimulatorManager {
    private static ArrayList<IRtuSimulator> simulators = new ArrayList();
    public static int rtua = 92010001;
    public static int heartInterval = 5;
    public static int taskInterval = 15;
    private static Timer timerHeart = null;
    private static Timer timerTask = null;
    public static int totalSend = 0;
    public static int totalRecv = 0;
    private static LinkedList<IMessage> sendMsg = new LinkedList();
    private static LinkedList<IMessage> recvMsg = new LinkedList();
    private static Object lockRecv = new Object();
    private static Object lockSend = new Object();

    public static void startHeart() {
        stopHeart();
        timerHeart = new Timer("simul.heart");
        timerHeart.schedule(new TimerTask() {
            public void run() {
                synchronized (SimulatorManager.simulators) {
                    for (IRtuSimulator simu : SimulatorManager.simulators)
                        simu.sendHeart();
                }
            }
        }, 0L, heartInterval * 1000);
    }

    public static void stopHeart() {
        if (timerHeart != null) {
            timerHeart.cancel();
            timerHeart = null;
        }
    }

    public static boolean isHeartRunning() {
        return (timerHeart == null);
    }

    public static void startTask() {
        stopTask();
        timerTask = new Timer("simul.task");
        timerTask.schedule(new TimerTask() {
            public void run() {
                synchronized (SimulatorManager.simulators) {
                    for (IRtuSimulator simu : SimulatorManager.simulators)
                        simu.sendTask();
                }
            }
        }, 0L, taskInterval * 1000);
    }

    public static void stopTask() {
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    public static boolean isTaskRunning() {
        return (timerTask == null);
    }

    public static void onChannelConnected(JAsyncSocket channel) {
        IRtuSimulator simulator = (IRtuSimulator) channel.attachment();
        if (simulator.getRtua() == 0) {
            synchronized (simulators) {
                simulator.setRtua(rtua++);
                simulators.add(simulator);
            }
        }
        simulator.onConnect(channel);
    }

    public static void onChannelClosed(JAsyncSocket channel) {
        IRtuSimulator simulator = (IRtuSimulator) channel.attachment();
        simulator.onClose(channel);
    }

    public static void onChannelReceive(JAsyncSocket channel, IMessage message) {
        IRtuSimulator simulator = (IRtuSimulator) channel.attachment();
        simulator.onReceive(channel, message);
        synchronized (lockRecv) {
            totalRecv += 1;
            if (recvMsg.size() > 5000) recvMsg.removeFirst();
            recvMsg.add(message);
        }
    }

    public static void onChannelSend(JAsyncSocket channel, IMessage message) {
        IRtuSimulator simulator = (IRtuSimulator) channel.attachment();
        simulator.onSend(channel, message);
        synchronized (lockSend) {
            totalSend += 1;
            if (sendMsg.size() > 5000) sendMsg.removeFirst();
            sendMsg.add(message);
        }
    }

    public static ArrayList<IRtuSimulator> getSimulators() {
        synchronized (simulators) {
            return simulators;
        }
    }

    public static IMessage[] getSendMsg() {
        synchronized (lockSend) {
            return ((IMessage[]) sendMsg.toArray(new IMessage[0]));
        }
    }

    public static IMessage[] getRecvMsg() {
        synchronized (lockRecv) {
            return ((IMessage[]) recvMsg.toArray(new IMessage[0]));
        }
    }
}