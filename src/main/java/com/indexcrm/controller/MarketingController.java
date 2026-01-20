import com.indexcrm.service.MarketingService;
import com.indexcrm.dto.MarketingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

package com.indexcrm.controller;



@RestController
@RequestMapping("/api/marketing")
public class MarketingController {

    private final MarketingService marketingService;

    @Autowired
    public MarketingController(MarketingService marketingService) {
        this.marketingService = marketingService;
    }

    @GetMapping
    public ResponseEntity<List<MarketingDto>> findAll() {
        return ResponseEntity.ok(marketingService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarketingDto> findById(@PathVariable Long id) {
        return marketingService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MarketingDto> create(@Valid @RequestBody MarketingDto dto) {
        MarketingDto created = marketingService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarketingDto> update(@PathVariable Long id, @Valid @RequestBody MarketingDto dto) {
        return marketingService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (marketingService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}