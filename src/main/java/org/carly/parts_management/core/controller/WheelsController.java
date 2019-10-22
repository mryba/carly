package org.carly.parts_management.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.carly.parts_management.api.model.WheelsRest;
import org.carly.parts_management.api.model.WheelsSearchCriteriaRest;
import org.carly.parts_management.core.service.WheelsFindService;
import org.carly.parts_management.core.service.WheelsSaveService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/wheels")
@Slf4j
public class WheelsController {

    private final WheelsFindService wheelsFindService;
    private final WheelsSaveService wheelsSaveService;


    public WheelsController(WheelsFindService wheelsFindService,
                            WheelsSaveService wheelsSaveService) {
        this.wheelsFindService = wheelsFindService;
        this.wheelsSaveService = wheelsSaveService;
    }


    @GetMapping("/wheels")
    public List<WheelsRest> findWheels(WheelsSearchCriteriaRest searchCriteria,
                                       Page pageable) {
        return wheelsFindService.findWheels(searchCriteria, pageable);
    }

    @GetMapping("/{id}")
    public WheelsRest findWheelsById(@PathVariable("id") ObjectId id) {
        return wheelsFindService.findPartById(id);
    }

    @GetMapping("/all")
    public Collection<WheelsRest> findAllWheels() {
        return wheelsFindService.findAll();
    }

    @PostMapping("/create-wheels")
    public WheelsRest createWheels(@RequestBody WheelsRest wheels) {
        return wheelsSaveService.createPart(wheels);
    }

    @PutMapping()
    public WheelsRest updateWheels(@RequestBody WheelsRest wheels) {
        return wheelsSaveService.updatePart(wheels);
    }



}
