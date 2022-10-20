package uz.pdp.appspringpcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AttachmentDto {

    @NotNull(message = "name bo'sh bo'lmasligi kerak")
    private String name;

    @NotNull(message = "name bo'sh bo'lmasligi kerak")
    private long size;

    @NotNull(message = "name bo'sh bo'lmasligi kerak")
    private String contentType;
}
