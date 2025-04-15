package com.quafresh.web.aquafreshweb.dto.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PictureAdimDTO {
    private Integer id;
    @JsonProperty("url") // ánh xạ từ frontend
    private String urlImage;
    private ProductAdminDTO idProduct;
}
