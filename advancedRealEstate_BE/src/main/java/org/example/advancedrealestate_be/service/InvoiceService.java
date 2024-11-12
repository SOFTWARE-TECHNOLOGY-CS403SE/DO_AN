package org.example.advancedrealestate_be.service;

import org.example.advancedrealestate_be.dto.request.ContractDetailRequest;
import org.example.advancedrealestate_be.dto.request.InvoiceRequest;
import org.example.advancedrealestate_be.dto.response.ContractDetailResponse;
import org.example.advancedrealestate_be.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse createInvoice(InvoiceRequest request);
    InvoiceResponse getInvoiceById(String id);
    List<InvoiceResponse> getAllInvoice();
    InvoiceResponse updateInvoice(String id, InvoiceRequest request);
    void deleteInvoice(String id);
}
