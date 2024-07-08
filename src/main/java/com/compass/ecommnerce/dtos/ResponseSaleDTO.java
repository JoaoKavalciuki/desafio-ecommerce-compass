package com.compass.ecommnerce.dtos;

import java.time.Instant;
import java.util.List;

public record ResponseSaleDTO(Instant date, List<ResponseProductDTO> productDTO) {
}
