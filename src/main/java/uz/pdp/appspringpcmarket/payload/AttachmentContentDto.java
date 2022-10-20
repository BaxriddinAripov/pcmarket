package uz.pdp.appspringpcmarket.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AttachmentContentDto {

    @NotNull(message = "bytes bo'sh bo'masligi kerak")
    private byte[] bytes;

    @NotNull(message = "attachmentId bo'sh bo'masligi kerak")
    private Integer attachmentId;
}
