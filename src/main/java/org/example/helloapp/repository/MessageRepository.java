package org.example.helloapp.repository;

import org.example.helloapp.models.Message;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId, Sort sort);



    @Query(value = """
SELECT * FROM messages m
WHERE (m.sender_id = :userId OR m.receiver_id = :userId)
AND m.id IN (
    SELECT MAX(id)
    FROM messages
    WHERE sender_id = :userId OR receiver_id = :userId
    GROUP BY 
        LEAST(sender_id, receiver_id),
        GREATEST(sender_id, receiver_id)
)
ORDER BY m.id DESC
""", nativeQuery = true)
    List<Message> findChatList(Long userId);
}
