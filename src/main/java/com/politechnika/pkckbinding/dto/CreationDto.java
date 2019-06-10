package com.politechnika.pkckbinding.dto;

import java.io.Serializable;
import java.util.List;

import com.politechnika.pkckbinding.dto.rockets.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreationDto implements Serializable {
    protected List<String> currencyTypes;
    protected List<String> massTypes;
    protected List<String> forceUnitTypes;
    protected List<String> launchpadRef;
    protected List<String> customerRef;
    protected List<String> payloadRef;
    protected List<String> rocketRef;
    protected List<String> launchRef;
    protected List<String> locationRef;
}
