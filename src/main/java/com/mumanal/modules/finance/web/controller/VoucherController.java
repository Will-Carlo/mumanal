package com.mumanal.modules.finance.web.controller;

import com.mumanal.modules.finance.domain.dto.request.CreateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateVoucherRequest;
import com.mumanal.modules.finance.domain.dto.response.VoucherResponse;
import com.mumanal.modules.finance.domain.service.VoucherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getAll() {
        return ResponseEntity.ok(voucherService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(voucherService.getById(id));
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> create(@RequestBody @Valid CreateVoucherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voucherService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoucherResponse> update(@PathVariable Integer id,
                                                  @RequestBody @Valid UpdateVoucherRequest request) {
        return ResponseEntity.ok(voucherService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        voucherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}