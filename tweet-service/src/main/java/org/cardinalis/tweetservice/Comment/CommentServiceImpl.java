package org.cardinalis.tweetservice.Comment;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.createPageResponse;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment commentTweet) {
        try {
            return commentRepository.save(commentTweet);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Comment deleteCommentByTweet_IdAndUsername(Long tweetId, String username) {
        try {
            Comment find = getCommentByTweet_IdAndUsername(tweetId, username);
            commentRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Comment deleteCommentById(Long id) {
        try {
            Comment find = this.getCommentById(id);
            commentRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Comment getCommentByTweet_IdAndUsername(Long tweetId, String username) {
        Comment find = commentRepository.findByUsernameAndTweet_Id(username, tweetId)
                .orElseThrow(() -> new NoContentFoundException("no comment found"));
        return find;
    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoContentFoundException("no comment found"));
    }

    @Override
    public Map<String, Object> getCommentsByTweet(Long tweetId, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Comment> result = commentRepository.findByTweet_Id(tweetId, pageable);
            return createPageResponse(result.getContent(), result.getNumber(), result.hasNext(), result.getTotalPages(), result.getNumberOfElements(), result.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Map<String, Object> getAll(int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.DESC,"createdAt");
            Page<Comment> result = commentRepository.findAll(pageable);
            return createPageResponse(result.getContent(), result.getNumber(), result.hasNext(), result.getTotalPages(), result.getNumberOfElements(), result.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
