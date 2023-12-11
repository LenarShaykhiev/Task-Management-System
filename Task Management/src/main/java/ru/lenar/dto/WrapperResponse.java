package ru.lenar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WrapperResponse {
    private Object response;
    private Long time;
}
