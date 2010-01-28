package com.hisun.service;


import com.hisun.event.UpdateListener;
import com.hisun.service.imp.UpdateListenerHubImpl;
import com.hisun.util.HiICSProperty;
import com.hisun.util.HiThreadPool;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.ScheduledExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;
import org.apache.hivemind.Registry;
import org.apache.hivemind.impl.RegistryBuilder;

public class HiInfrastructure {
    private static Registry registry;
    private static UpdateListenerHub hub;
    private static ScheduledExecutorService executor;
    private static int file_check_interval = 60000;

    public static Registry getRegistry() {

        if (registry == null) registry = RegistryBuilder.constructDefaultRegistry();

        return registry;
    }

    public static Object getService(Class serviceInterface) {

        return getRegistry().getService(serviceInterface);
    }

    public static HiThreadPool getThreadPoolService(String id) {

        IThreadPoolFactory factory = (IThreadPoolFactory) getRegistry().getService(IThreadPoolFactory.class);


        HiThreadPool pool = factory.getThreadPool(id);

        pool.setMaximumPoolSize(10);

        return pool;
    }

    public static void addUpdateListener(UpdateListener listener) {

        hub.addUpdateListener(listener);
    }

    static {

        hub = new UpdateListenerHubImpl();

        if (HiICSProperty.isDevEnv()) {

            executor = Executors.newSingleThreadScheduledExecutor();

            Runnable checker = new Runnable() {
                public void run() {

                    HiInfrastructure.hub.fireUpdateEvent();
                }
            };

            executor.scheduleWithFixedDelay(checker, 30000L, file_check_interval, TimeUnit.MILLISECONDS);
        }
    }
}