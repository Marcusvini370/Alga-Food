package com.algafood.api.v1.model.input;

import com.algafood.core.validation.FileContentType;
import com.algafood.core.validation.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoInput {

    @Schema(hidden = true)
    @NotNull
    @FileSize(max = "4MB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    private MultipartFile arquivo;

    @Schema(description = "Descrição da foto do produto", required = true)
    @NotBlank
    private String descricao;

}
