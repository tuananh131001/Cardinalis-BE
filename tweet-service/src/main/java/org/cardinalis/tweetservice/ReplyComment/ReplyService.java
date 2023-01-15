package org.cardinalis.tweetservice.ReplyComment;

import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;

@EnableKafka
public interface ReplyService {

    @KafkaListener(topics = "saveReply", groupId = "group_id")
    Reply saveReply(Reply reply);

    Reply editReply(Reply reply);
    Reply deleteReplyById(Long id);

    Reply getReplyById(Long id);

    Map<String, Object> getRepliesOfComment(Long commentId, String sort, int pageNo, int pageSize);

}
