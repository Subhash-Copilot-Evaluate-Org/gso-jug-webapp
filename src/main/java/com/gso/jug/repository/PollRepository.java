package com.gso.jug.repository;

import com.gso.jug.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Poll findFirstByActiveTrue();
}
