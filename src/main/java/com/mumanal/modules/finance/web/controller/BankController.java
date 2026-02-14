package com.mumanal.modules.finance.web.controller;

import com.mumanal.modules.finance.domain.dto.request.CreateBankRequest;
import com.mumanal.modules.finance.domain.dto.request.UpdateBankRequest;
import com.mumanal.modules.finance.domain.dto.response.BankResponse;
import com.mumanal.modules.finance.domain.service.BankService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance/banks")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity<List<BankResponse>> getAll() {
        return ResponseEntity.ok(bankService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(bankService.getById(id));
    }

    @PostMapping
    public ResponseEntity<BankResponse> create(@RequestBody @Valid CreateBankRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bankService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> update(@PathVariable Integer id,
                                               @RequestBody @Valid UpdateBankRequest request) {
        return ResponseEntity.ok(bankService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        bankService.delete(id);
        return ResponseEntity.noContent().build();
    }
}