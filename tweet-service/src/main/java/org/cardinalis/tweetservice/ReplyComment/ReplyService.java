package org.cardinalis.tweetservice.ReplyComment;

import java.util.Map;

public interface ReplyService {

    Reply saveReply(Reply reply);

    Reply editReply(Reply reply);
    Reply deleteReplyById(Long id);

    Reply getReplyById(Long id);

    Map<String, Object> getRepliesOfComment(Long commentId, String sort, int pageNo, int pageSize);

}
