package br.com.borracharia.mapper.worker;

import br.com.borracharia.dto.worker.WorkOrderReq;
import br.com.borracharia.dto.worker.WorkOrderRes;
import br.com.borracharia.entity.WorkOrder;

public class WorkOrderMapper {

    public static WorkOrder toEntity(WorkOrderReq dto) {
        return WorkOrder.builder()
                .customerId(dto.getCustomerId())
                .plate(dto.getPlate())
                .serviceDate(dto.getServiceDate())
                .items(dto.getItems())
                .paymentMethod(dto.getPaymentMethod())
                .status(dto.getStatus())
                .total(dto.calculateTotal())
                .build();
    }

    public static WorkOrderRes toDto(WorkOrder entity) {
        return WorkOrderRes.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .plate(entity.getPlate())
                .serviceDate(entity.getServiceDate())
                .items(entity.getItems())
                .paymentMethod(entity.getPaymentMethod())
                .status(entity.getStatus())
                .total(entity.getTotal())
                .createdBy(entity.getCreatedBy())
                .build();
    }
}
