package com.compass.ecommnerce.dtos;

import java.time.Instant;
import java.util.List;

public record RelatoryDTO(Instant issuedAt, Double relatoryTotalSale, Integer relatoryQuantitySold, List<ResponseSaleDTO> sales) {
}
