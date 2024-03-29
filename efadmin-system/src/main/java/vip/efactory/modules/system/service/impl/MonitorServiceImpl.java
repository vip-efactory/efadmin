/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package vip.efactory.modules.system.service.impl;

import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.FormatUtil;
import oshi.util.Util;
import vip.efactory.common.i18n.enums.ErrorCodeUtil;
import vip.efactory.modules.system.service.MonitorService;
import vip.efactory.utils.FileUtil;
import vip.efactory.utils.StringUtils;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Zheng Jie
 * @author dbdu
 * @date 2020-05-02
 */
@Service
public class MonitorServiceImpl implements MonitorService {

    private final DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public Map<String, Object> getServers() {
        Map<String, Object> resultMap = new LinkedHashMap<>(8);
        try {
            SystemInfo si = new SystemInfo();
            OperatingSystem os = si.getOperatingSystem();
            HardwareAbstractionLayer hal = si.getHardware();
            // 系统信息
            resultMap.put("sys", getSystemInfo(os));
            // cpu 信息
            resultMap.put("cpu", getCpuInfo(hal.getProcessor()));
            // 内存信息
            resultMap.put("memory", getMemoryInfo(hal.getMemory()));
            // 交换区信息
            resultMap.put("swap", getSwapInfo(hal.getMemory()));
            // 磁盘
            resultMap.put("disk", getDiskInfo(os));
            resultMap.put("time", DateUtil.format(new Date(), "HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取磁盘信息
     *
     * @return /
     */
    private Map<String, Object> getDiskInfo(OperatingSystem os) {
        Map<String, Object> diskInfo = new LinkedHashMap<>();
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores(true);
        long allTotal = 0L;
        long allUsed = 0L;
        long allAvailable = 0L;
        for (OSFileStore fs : fsArray) {
            allTotal = allTotal + fs.getTotalSpace();
            allUsed = allUsed + (fs.getTotalSpace() - fs.getUsableSpace());
            allAvailable = allAvailable + fs.getUsableSpace();

//            diskInfo.put("total", fs.getTotalSpace() > 0 ? FileUtil.getSize(fs.getTotalSpace()) : "?");
//            long used = fs.getTotalSpace() - fs.getUsableSpace();
//            diskInfo.put("available", FileUtil.getSize(fs.getUsableSpace()));
//            diskInfo.put("used", FileUtil.getSize(used));
//            diskInfo.put("usageRate", df.format(used/(double)fs.getTotalSpace() * 100));
        }

        diskInfo.put("total", FileUtil.getSize(allTotal));
        diskInfo.put("available", FileUtil.getSize(allAvailable));
        diskInfo.put("used", FileUtil.getSize(allUsed));
        if (allTotal <= 0){
            diskInfo.put("usageRate", 0);
        } else {
            diskInfo.put("usageRate", df.format(allUsed / (double) allTotal * 100));
        }
        diskInfo.put("disks", fsArray);
        return diskInfo;
    }

    /**
     * 获取交换区信息
     *
     * @param memory /
     * @return /
     */
    private Map<String, Object> getSwapInfo(GlobalMemory memory) {
        Map<String, Object> swapInfo = new LinkedHashMap<>();
        swapInfo.put("total", FormatUtil.formatBytes(memory.getVirtualMemory().getSwapTotal()));
        swapInfo.put("used", FormatUtil.formatBytes(memory.getVirtualMemory().getSwapUsed()));
        swapInfo.put("available", FormatUtil.formatBytes(memory.getVirtualMemory().getSwapTotal() - memory.getVirtualMemory().getSwapUsed()));
        if (memory.getVirtualMemory().getSwapTotal() <= 0){
            swapInfo.put("usageRate", 0);
        } else {
            swapInfo.put("usageRate", df.format(memory.getVirtualMemory().getSwapUsed() / (double) memory.getVirtualMemory().getSwapTotal() * 100));
        }
        return swapInfo;
    }

    /**
     * 获取内存信息
     *
     * @param memory /
     * @return /
     */
    private Map<String, Object> getMemoryInfo(GlobalMemory memory) {
        Map<String, Object> memoryInfo = new LinkedHashMap<>();
        memoryInfo.put("total", FormatUtil.formatBytes(memory.getTotal()));
        memoryInfo.put("available", FormatUtil.formatBytes(memory.getAvailable()));
        memoryInfo.put("used", FormatUtil.formatBytes(memory.getTotal() - memory.getAvailable()));
        memoryInfo.put("usageRate", df.format((memory.getTotal() - memory.getAvailable()) / (double) memory.getTotal() * 100));
        return memoryInfo;
    }

    /**
     * 获取Cpu相关信息
     *
     * @param processor /
     * @return /
     */
    private Map<String, Object> getCpuInfo(CentralProcessor processor) {
        Map<String, Object> cpuInfo = new LinkedHashMap<>();
        cpuInfo.put("name", processor.getProcessorIdentifier().getName());
        cpuInfo.put("package", processor.getPhysicalPackageCount() + " " + ErrorCodeUtil.getMessage("Server.package"));
        cpuInfo.put("core", processor.getPhysicalProcessorCount() + " " + ErrorCodeUtil.getMessage("Server.core"));
        cpuInfo.put("coreNumber", processor.getPhysicalProcessorCount());
        cpuInfo.put("logic", processor.getLogicalProcessorCount() + " " + ErrorCodeUtil.getMessage("Server.logic"));
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 等待1秒...
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
        cpuInfo.put("used", df.format(100d * user / totalCpu + 100d * sys / totalCpu));
        cpuInfo.put("idle", df.format(100d * idle / totalCpu));
        return cpuInfo;
    }

    /**
     * 获取系统相关信息,系统、运行天数、系统IP
     *
     * @param os /
     * @return /
     */
    private Map<String, Object> getSystemInfo(OperatingSystem os) {
        Map<String, Object> systemInfo = new LinkedHashMap<>();
        // jvm 运行时间
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        Date date = new Date(time);
        // 计算项目运行时间
        String formatBetween = DateUtil.formatBetween(date, new Date(), BetweenFormatter.Level.HOUR);
        // 如果当前的语言环境不是中文简体，则显示为英文
        if (!LocaleContextHolder.getLocale().equals(Locale.SIMPLIFIED_CHINESE)) {
            formatBetween = formatBetween.replace("天", "Days");
            formatBetween = formatBetween.replace("小时", "Hours");
            //formatBetween = formatBetween.replace("分", "Minutes");
            //formatBetween = formatBetween.replace("秒", "Seconds");
            //formatBetween = formatBetween.replace("毫秒", "MillSeconds");
        }
        // 系统信息
        systemInfo.put("os", os.toString());
        systemInfo.put("day", formatBetween);
        systemInfo.put("ip", StringUtils.getLocalIp());
        return systemInfo;
    }
}
