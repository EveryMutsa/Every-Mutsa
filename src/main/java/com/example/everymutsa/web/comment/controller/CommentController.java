package com.example.everymutsa.web.comment.controller;

import com.example.everymutsa.web.comment.domain.dto.CommentRequest;
import com.example.everymutsa.web.comment.domain.dto.CommentResponse;
import com.example.everymutsa.web.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    /** post Id로 전체 출력 PAGE*/
    @GetMapping("/posts/{pid}")
    public Page<CommentResponse> getAllCommentByPost(@PathVariable Long pid,
                                               @RequestParam(name = "no", defaultValue = "0") int pageNo,
                                               @RequestParam(name = "size", defaultValue = "20") int pageSize){
        return commentService.readAllByPostId(pid, pageNo, pageSize);
    }

    /** post Id로 전체 출력 PAGE*/
    @GetMapping("/user/{uid}")
    public Page<CommentResponse> getAllCommentByMember(@PathVariable Long uid,
                                               @RequestParam(name = "no", defaultValue = "0") int pageNo,
                                               @RequestParam(name = "size", defaultValue = "20") int pageSize){
        return commentService.readAllByMemberId(uid, pageNo, pageSize);
    }

    /** comment id로 출력 */
    @GetMapping("/{id}/posts/{pid}")
    public CommentResponse getComment(@PathVariable Long id, @PathVariable Long pid){
        return commentService.readOne(id, pid);
    }

    /** comment 저장  : userDetails 필요 */
    @PostMapping("/posts/{pid}")
    public CommentResponse saveComment(
            @PathVariable Long pid,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal User userDetails){
        String email = userDetails.getUsername();
        return commentService.save(pid, request, email);
    }

    /** comment 수정 : userDetails 필요 */
    @PutMapping("{id}/posts/{pid}")
    public CommentResponse updateComment(
            @PathVariable Long id,
            @PathVariable Long pid,
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal User userDetails){
        String email = userDetails.getUsername();
        return commentService.update(id, pid, request, email);
    }

    /** comment 삭제 : userDetails 필요 */
    @DeleteMapping("{id}/posts/{pid}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long id,
            @PathVariable Long pid,
            @AuthenticationPrincipal User userDetails){
        String email = userDetails.getUsername();
        commentService.remove(id, pid, email);

        return ResponseEntity.ok("Deletion successful");
    }
}
