package org.example.advancedrealestate_be.mapper;

import javax.annotation.processing.Generated;
import org.example.advancedrealestate_be.dto.request.ContractRequest;
import org.example.advancedrealestate_be.dto.response.ContractResponse;
import org.example.advancedrealestate_be.entity.Building;
import org.example.advancedrealestate_be.entity.Contracts;
import org.example.advancedrealestate_be.entity.Customers;
import org.example.advancedrealestate_be.entity.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class ContractMapperImpl implements ContractMapper {

    @Override
    public ContractResponse toResponse(Contracts contract) {
        if ( contract == null ) {
            return null;
        }

        ContractResponse.ContractResponseBuilder contractResponse = ContractResponse.builder();

        contractResponse.customerId( contractCustomerId( contract ) );
        contractResponse.buildingId( contractBuildingId( contract ) );
        contractResponse.userId( contractUserId( contract ) );
        contractResponse.id( contract.getId() );

        return contractResponse.build();
    }

    @Override
    public Contracts toEntity(ContractRequest request) {
        if ( request == null ) {
            return null;
        }

        Contracts.ContractsBuilder contracts = Contracts.builder();

        return contracts.build();
    }

    private String contractCustomerId(Contracts contracts) {
        if ( contracts == null ) {
            return null;
        }
        Customers customer = contracts.getCustomer();
        if ( customer == null ) {
            return null;
        }
        String id = customer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String contractBuildingId(Contracts contracts) {
        if ( contracts == null ) {
            return null;
        }
        Building building = contracts.getBuilding();
        if ( building == null ) {
            return null;
        }
        String id = building.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String contractUserId(Contracts contracts) {
        if ( contracts == null ) {
            return null;
        }
        User user = contracts.getUser();
        if ( user == null ) {
            return null;
        }
        String id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
