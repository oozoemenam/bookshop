package com.bookshop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class News {
    @EmbeddedId
    private NewsId id;

    @Column(nullable = false)
    private String content;

    // @OrderBy("postedDate DESC, note ASC")
    @JoinColumn
    // @OrderColumn(name = "publication_index")
    @OrderColumn
    private List<Comment> comments;
}
