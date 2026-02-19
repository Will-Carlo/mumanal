package com.mumanal.modules.finance.web.controller;

import com.mumanal.modules.finance.domain.dto.request.CreateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateAffiliateRequest;
import com.mumanal.modules.finance.domain.dto.response.AffiliateResponse;
import com.mumanal.modules.finance.domain.service.AffiliateService;
import com.mumanal.modules.security.domain.constant.AppPermissions;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/affiliates")
public class AffiliateController {

    private final AffiliateService affiliateService;

    public AffiliateController(AffiliateService affiliateService) {
        this.affiliateService = affiliateService;
    }

    @GetMapping
    @PreAuthorize(AppPermissions.FIN_AFFILIATE_READ)
    public ResponseEntity<List<AffiliateResponse>> getAll() {
        return ResponseEntity.ok(affiliateService.getAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize(AppPermissions.FIN_AFFILIATE_READ)
    public ResponseEntity<AffiliateResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(affiliateService.getById(id));
    }

    @PostMapping
    @PreAuthorize(AppPermissions.FIN_AFFILIATE_MANAGE)
    public ResponseEntity<AffiliateResponse> create(@RequestBody @Valid CreateAffiliateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(affiliateService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize(AppPermissions.FIN_AFFILIATE_MANAGE)
    public ResponseEntity<AffiliateResponse> update(@PathVariable Integer id,
                                                    @RequestBody @Valid UpdateAffiliateRequest request) {
        return ResponseEntity.ok(affiliateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AppPermissions.FIN_AFFILIATE_MANAGE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        affiliateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}