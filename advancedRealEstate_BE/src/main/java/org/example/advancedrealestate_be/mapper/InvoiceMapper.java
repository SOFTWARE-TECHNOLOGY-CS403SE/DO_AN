package org.example.advancedrealestate_be.mapper;


import org.example.advancedrealestate_be.dto.request.InvoiceRequest;
import org.example.advancedrealestate_be.dto.response.InvoiceResponse;
import org.example.advancedrealestate_be.entity.Invoices;
import org.example.advancedrealestate_be.repository.ContractReposetory;
import org.example.advancedrealestate_be.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InvoiceMapper {

    @Autowired
    private ContractReposetory contractReposetory;

    public Invoices toEntity(InvoiceRequest request) {
        Invoices invoices = new Invoices();
        invoices.setInvoice_date(LocalDate.now()); // Set current date automatically
        invoices.setAmount(request.getAmount());
        invoices.setExpire_date_payment(request.getExpire_date_payment());

        // Find and set the associated contract
        contractReposetory.findById(request.getContractId()).ifPresent(invoices::setContracts);
        return invoices;
    }

    public InvoiceResponse toResponse(Invoices entity) {
        return InvoiceResponse.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .expire_date_payment(entity.getExpire_date_payment())
                .invoice_date(entity.getInvoice_date())
                .build();
    }
}
