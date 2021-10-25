package com.jhonocampo.codigotonretocenacliente.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NonNull;

@Data
public class DistribuiteDto {
    @NonNull
    private MultipartFile file;

    

    public DistribuiteDto() {
        super();
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
    
}
