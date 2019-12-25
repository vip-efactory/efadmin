package vip.efactory.modules.monitor.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.efactory.ejpa.base.controller.BaseController;
import vip.efactory.modules.monitor.entity.Visits;
import vip.efactory.modules.monitor.service.VisitsService;
import vip.efactory.utils.RequestHolder;

@RestController
@RequestMapping("api")
public class VisitsController extends BaseController<Visits, VisitsService, Long> {

    @PostMapping(value = "/visits")
    public ResponseEntity create() {
        entityService.count(RequestHolder.getHttpServletRequest());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping(value = "/visits")
    public ResponseEntity get() {
        return new ResponseEntity(entityService.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/visits/chartData")
    public ResponseEntity getChartData() {
        return new ResponseEntity(entityService.getChartData(), HttpStatus.OK);
    }
}
