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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("postedDate DESC, note ASC")
    // @JoinColumn(name = "comment_id")
    // @OrderColumn(name = "publication_index")
    private List<Comment> comments;
}
