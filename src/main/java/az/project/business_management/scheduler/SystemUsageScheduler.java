package az.project.business_management.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

@Component
public class SystemUsageScheduler {

    @Scheduled(fixedRate = 5000) // Run every 5 seconds
    public void logSystemUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();

        // Get CPU load (System and Process)
        double systemCpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getSystemCpuLoad() * 100;
        double processCpuLoad = ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuLoad() * 100;

        // Get memory usage
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;

        System.out.printf("System CPU Load: %.2f%%, Process CPU Load: %.2f%%, Used Memory: %d MB, Total Memory: %d MB%n",
                systemCpuLoad, processCpuLoad, usedMemory / (1024 * 1024), totalMemory / (1024 * 1024));
    }
}