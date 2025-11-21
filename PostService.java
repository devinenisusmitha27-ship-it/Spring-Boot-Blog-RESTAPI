package com.example.blog.service;

import com.example.blog.dto.PostDto;
import com.example.blog.exception.ResourceNotFoundException;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public PostService(PostRepository postRepo, UserRepository userRepo) {
        this.postRepo = postRepo; this.userRepo = userRepo;
    }

    public PostDto createPost(PostDto dto) {
        User author = userRepo.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getAuthorId()));
        Post post = new Post(dto.getTitle(), dto.getContent());
        post.setAuthor(author);
        Post saved = postRepo.save(post);
        return toDto(saved);
    }

    public PostDto getPost(Long id) {
        return postRepo.findById(id).map(this::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
    }

    public List<PostDto> listPosts() {
        return postRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<PostDto> listPostsByAuthor(Long authorId) {
        return postRepo.findByAuthorId(authorId).stream().map(this::toDto).collect(Collectors.toList());
    }

    public PostDto updatePost(Long id, PostDto dto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return toDto(postRepo.save(post));
    }

    public void deletePost(Long id) { postRepo.deleteById(id); }

    private PostDto toDto(Post p) {
        PostDto dto = new PostDto();
        dto.setId(p.getId());
        dto.setTitle(p.getTitle());
        dto.setContent(p.getContent());
        dto.setCreatedAt(p.getCreatedAt());
        if (p.getAuthor() != null) {
            dto.setAuthorId(p.getAuthor().getId());
            dto.setAuthorUsername(p.getAuthor().getUsername());
        }
        dto.setCommentIds(p.getComments().stream().map(c -> c.getId()).collect(Collectors.toList()));
        return dto;
    }
}
