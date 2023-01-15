package org.cardinalis.tweetservice.Comment;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.cardinalis.tweetservice.Util.Reusable.createPageResponse;

@Slf4j
@Service
@EnableKafka
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public Comment saveComment(Comment newComment) {
        try {
            return commentRepository.save(newComment);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Comment editComment(Comment newComment) {
        try {
            Comment oldComment = getCommentById(newComment.getId());
            oldComment.setContent(newComment.getContent());
            oldComment.setLastEdit(LocalDateTime.now());
            return commentRepository.save(oldComment);
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
    public Comment deleteComment(Comment comment) {
        try {
            commentRepository.delete(comment);
            return comment;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoContentFoundException("no comment found"));
    }

    @Override
    public Map<String, Object> getCommentsOfTweet(Long tweetId, String sort, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sort),"createdAt");
            Page<Comment> result = commentRepository.findByTweet_Id(tweetId, pageable);

            return createPageResponse(getResultList(result), result.getNumber(), result.hasNext(), result.getTotalPages(), result.getNumberOfElements(), result.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<?> getResultList(Page<Comment> page) {
        List<Comment> comments = page.getContent();
        List<CommentDTO> commentDTOS = comments
                .stream()
                .map(comment -> new CommentDTO(comment))
                .collect(Collectors.toList());
        return commentDTOS;
    }
}
