package com.pwr.pwrdatabase.controller;

import com.pwr.pwrdatabase.dto.EmploymentContractDto;
import com.pwr.pwrdatabase.mapper.ContractMapper;
import com.pwr.pwrdatabase.service.ContractService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/contracts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContractController
{
    private final ContractService SERVICE;
    private final ContractMapper MAPPER;

    @GetMapping
    public Set<EmploymentContractDto> getContracts()
    {
        return MAPPER.mapToContractsDto(SERVICE.findAll());
    }

    @GetMapping("/{id}")
    public EmploymentContractDto getContract(@PathVariable("id") final Long ID)
    {
        return MAPPER.mapToContractDto(SERVICE.findOne(ID));
    }

    @DeleteMapping("/{id}")
    public void deleteContract(@PathVariable("id") final Long ID)
    {
        SERVICE.delete(ID);
    }

    @PutMapping
    public EmploymentContractDto updateContract(@Valid @RequestBody final EmploymentContractDto CONTRACT_DTO)
    {
        return MAPPER.mapToContractDto(SERVICE.save(MAPPER.mapToContract(CONTRACT_DTO)));
    }

    @PostMapping
    public void createEmployee(@Valid @RequestBody final EmploymentContractDto CONTRACT_DTO)
    {
        SERVICE.save(MAPPER.mapToContract(CONTRACT_DTO));
    }
}
