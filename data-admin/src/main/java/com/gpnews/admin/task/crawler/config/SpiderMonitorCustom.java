package com.gpnews.admin.task.crawler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.SpiderListener;
import us.codecraft.webmagic.monitor.SpiderStatusMXBean;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author HuangChongHeng
 * @date 2020/3/30
 */
public class SpiderMonitorCustom {
    private static SpiderMonitorCustom INSTANCE = new SpiderMonitorCustom();

    private AtomicBoolean started = new AtomicBoolean(false);

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MBeanServer mbeanServer;

    private String jmxServerName;

    private Map<String,SpiderStatusCustom> spiderStatuses = new HashMap<String,SpiderStatusCustom>();

    protected SpiderMonitorCustom() {
        jmxServerName = "WebMagic";
        mbeanServer = ManagementFactory.getPlatformMBeanServer();
    }
    public Map<String,SpiderStatusCustom> getSpiderStatuses()
    {
        return spiderStatuses;
    }

    /**
     * Register spider for monitor.
     *
     * @param spiders spiders
     * @return this
     */
    public synchronized SpiderMonitorCustom register(Spider... spiders) throws JMException {
        for (Spider spider : spiders) {
            MyMonitorSpiderListener monitorSpiderListener = new MyMonitorSpiderListener();
            if (spider.getSpiderListeners() == null) {
                List<SpiderListener> spiderListeners = new ArrayList<SpiderListener>();
                spiderListeners.add(monitorSpiderListener);
                spider.setSpiderListeners(spiderListeners);
            } else {
                spider.getSpiderListeners().add(monitorSpiderListener);
            }
            SpiderStatusCustom spiderStatusMBean = getSpiderStatusMBean(spider, monitorSpiderListener);
            registerMBean(spiderStatusMBean);
            spiderStatuses.put(spider.getUUID(),spiderStatusMBean);
        }
        return this;
    }

    protected SpiderStatusCustom getSpiderStatusMBean(Spider spider, MyMonitorSpiderListener monitorSpiderListener) {
        return new SpiderStatusCustom(spider, monitorSpiderListener);
    }

    public static SpiderMonitorCustom instance() {
        return INSTANCE;
    }

    public class MyMonitorSpiderListener implements SpiderListener {

        private final AtomicInteger successCount = new AtomicInteger(0);

        private final AtomicInteger errorCount = new AtomicInteger(0);

        private List<String> errorUrls = Collections.synchronizedList(new ArrayList<String>());

        @Override
        public void onSuccess(Request request) {
            successCount.incrementAndGet();
        }

        @Override
        public void onError(Request request) {
            errorUrls.add(request.getUrl());
            errorCount.incrementAndGet();
        }

        public AtomicInteger getSuccessCount() {
            return successCount;
        }

        public AtomicInteger getErrorCount() {
            return errorCount;
        }

        public List<String> getErrorUrls() {
            return errorUrls;
        }
    }

    protected void registerMBean(SpiderStatusMXBean spiderStatus) throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException {
        ObjectName objName = new ObjectName(jmxServerName + ":name=" + spiderStatus.getName());
        if(mbeanServer.isRegistered(objName)==false)
        {
            mbeanServer.registerMBean(spiderStatus, objName);
        }
    }
}
