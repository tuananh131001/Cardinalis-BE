package org.cardinalis.tweetservice.ReplyComment;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.AuthorizationException;
import org.cardinalis.tweetservice.Util.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static org.cardinalis.tweetservice.Util.Reusable.createPageResponse;

@Slf4j
@Service
@EnableKafka
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    ReplyRepository replyRepository;

    @Override
    public Reply saveReply(Reply newReply) {
        try {
            return replyRepository.save(newReply);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Reply editReply(Reply newReply) {
        try {
            Reply oldReply = getReplyById(newReply.getId());
            if (!oldReply.getUsermail().equals(newReply.getUsermail())) throw new AuthorizationException("authorization failed");
            oldReply.setContent(newReply.getContent());
            oldReply.setLastEdit(LocalDateTime.now());
            return replyRepository.save(oldReply);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Reply deleteReplyById(Long id) {
        try {
            Reply find = this.getReplyById(id);
            replyRepository.delete(find);
            return find;
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public Reply getReplyById(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new NoContentFoundException("no reply found"));
    }

    @Override
    public Map<String, Object> getRepliesOfComment(Long commentId, String sort, int pageNo, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sort),"createdAt");
            Page<Reply> result = replyRepository.findByComment_Id(commentId, pageable);
            return createPageResponse(result.getContent(), result.getNumber(), result.hasNext(), result.getTotalPages(), result.getNumberOfElements(), result.getSize());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
