// shared/dto/response/common/ApiResponse.java
package com.policlinico.smartsalud.shared.dto.response.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object data;
}