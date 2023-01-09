package org.cardinalis.tweetservice.ReplyComment;

import lombok.extern.slf4j.Slf4j;
import org.cardinalis.tweetservice.Ultilities.NoContentFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

import static org.cardinalis.tweetservice.Ultilities.Reusable.createPageResponse;

@Slf4j
@Service
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
                .orElseThrow(() -> new NoContentFoundException("no replyComment found"));
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
