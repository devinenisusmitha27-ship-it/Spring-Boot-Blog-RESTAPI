package com.example.blog.dto;

import java.time.Instant;
import java.util.List;

public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Instant createdAt;
    private Long authorId;
    private String authorUsername;
    private List<Long> commentIds;

    // constructors, getters, setters
    public PostDto() {}
    // getters & setters below

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getAuthorUsername() { return authorUsername; }
    public void setAuthorUsername(String authorUsername) { this.authorUsername = authorUsername; }
    public List<Long> getCommentIds() { return commentIds; }
    public void setCommentIds(List<Long> commentIds) { this.commentIds = commentIds; }
}
