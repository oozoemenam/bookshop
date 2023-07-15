package com.bookshop.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.Instant;

@Data
public class ItemServerConnection {
    @URL
    private String resourceURL;

    @NotNull
    @URL(protocol = "http", host = "www.bookshop.com")
    private String itemURL;

    @URL(protocol = "ftp", port = 21)
    private String ftpServerURL;

    private Instant lastConnectionDate;
}
