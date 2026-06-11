package com.portfolio.portfolio.repository;

import com.portfolio.portfolio.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Благодарение на JpaRepository имаме всичко необходимо!
}