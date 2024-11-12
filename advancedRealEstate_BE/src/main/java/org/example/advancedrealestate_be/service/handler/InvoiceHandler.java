package org.example.advancedrealestate_be.service.handler;

import org.example.advancedrealestate_be.dto.request.InvoiceRequest;
import org.example.advancedrealestate_be.dto.response.InvoiceResponse;
import org.example.advancedrealestate_be.entity.Invoices;
import org.example.advancedrealestate_be.mapper.InvoiceMapper;
import org.example.advancedrealestate_be.repository.InvoiceRepository;
import org.example.advancedrealestate_be.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceHandler implements InvoiceService {
   @Autowired
   private InvoiceRepository invoiceRepository;

   @Autowired
   private InvoiceMapper invoiceMapper;


    @Override
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        Invoices invoices=invoiceMapper.toEntity(request);
        invoices= invoiceRepository.save(invoices);
        return invoiceMapper.toResponse(invoices);
    }

    @Override
    public InvoiceResponse getInvoiceById(String id) {
        Invoices invoices=invoiceRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Invoice not found !"));
                return invoiceMapper.toResponse(invoices);
    }

    @Override
    public List<InvoiceResponse> getAllInvoice() {
        return invoiceRepository.findAll()
                .stream()
                .map(invoiceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceResponse updateInvoice(String id, InvoiceRequest request) {
       Invoices invoices=invoiceRepository.findById(id)
               .orElseThrow(()->new RuntimeException("Invoice not found !"));
       invoices.setAmount(request.getAmount());
       invoices.setExpire_date_payment(request.getExpire_date_payment());
       invoices.setInvoice_date(LocalDate.now());
       invoiceRepository.save(invoices);
       return invoiceMapper.toResponse(invoices);

    }

    @Override
    public void deleteInvoice(String id) {
        invoiceRepository.deleteById(id);
    }


}
