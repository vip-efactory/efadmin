package vip.efactory.modules.quartz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.efactory.modules.monitor.service.VisitsService;

@Component
public class VisitsTask {

    @Autowired
    private VisitsService visitsService;

    public void run() {
        visitsService.save();
    }
}
