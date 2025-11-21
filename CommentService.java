package com.example.blog.service;

import com.example.blog.dto.CommentDto;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepo;
    private final PostRepository postRepo;

    public CommentService(CommentRepository commentRepo, PostRepository postRepo) {
        this.commentRepo = commentRepo; this.postRepo = postRepo;
    }

    public CommentDto createComment(CommentDto dto) {
        Post post = postRepo.findById(dto.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", dto.getPostId()));
        Comment c = new Comment(dto.getText(), dto.getAuthorName());
        c.setPost(post);
        Comment saved = commentRepo.save(c);
        return toDto(saved);
    }

    public CommentDto getComment(Long id) {
        return commentRepo.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
    }

    public List<CommentDto> listCommentsByPost(Long postId) {
        return commentRepo.findByPostId(postId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public CommentDto updateComment(Long id, CommentDto dto) {
        Comment c = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        c.setText(dto.getText());
        c.setAuthorName(dto.getAuthorName());
        return toDto(commentRepo.save(c));
    }

    public void deleteComment(Long id) { commentRepo.deleteById(id); }

    private CommentDto toDto(Comment c) {
        CommentDto dto = new CommentDto();
        dto.setId(c.getId());
        dto.setText(c.getText());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setPostId(c.getPost() != null ? c.getPost().getId() : null);
        dto.setAuthorName(c.getAuthorName());
        return dto;
    }
}
