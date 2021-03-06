package org.carly.api.endpoint;

import org.bson.types.ObjectId;
import org.carly.api.rest.criteria.TiresSearchCriteriaRequest;
import org.carly.api.rest.request.TiresRequest;
import org.carly.api.rest.response.TiresResponse;
import org.carly.core.partsmanagement.service.TiresFindService;
import org.carly.core.partsmanagement.service.TiresSaveService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api//tires")
public class TiresController {

    private final TiresFindService tiresFindService;
    private final TiresSaveService tiresSaveService;

    public TiresController(TiresFindService tiresFindService,
                           TiresSaveService tiresSaveService) {
        this.tiresFindService = tiresFindService;
        this.tiresSaveService = tiresSaveService;
    }

    @GetMapping()
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'CARLY_FACTORY', 'CARLY_COMPANY', 'CARLY_CUSTOMER')")
    public Page<TiresResponse> findTires(TiresSearchCriteriaRequest searchCriteria,
                                         Pageable pageable) {
        return tiresFindService.findTires(searchCriteria, pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'CARLY_FACTORY', 'CARLY_COMPANY', 'CARLY_CUSTOMER')")
    public ResponseEntity<TiresResponse> findTiresById(@PathVariable("id") String id) {
        return tiresFindService.findPartById(new ObjectId(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'CARLY_FACTORY', 'CARLY_COMPANY', 'CARLY_CUSTOMER')")
    public Collection<TiresResponse> findAllTires() {
        return tiresFindService.findAll();
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'CARLY_FACTORY')")
    public ResponseEntity<TiresResponse> createTires(@RequestBody TiresRequest tires) {
        return tiresSaveService.createPart(tires);
    }

    @PutMapping()
    @PreAuthorize("hasAnyAuthority('ADMINISTRATOR', 'CARLY_FACTORY')")
    public ResponseEntity<TiresResponse> updateTires(@RequestBody TiresRequest tires) {
        return tiresSaveService.updatePart(tires);
    }

}
