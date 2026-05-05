package com.gso.jug.repository;

import com.gso.jug.model.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PollOptionRepository extends JpaRepository<PollOption, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE PollOption p SET p.voteCount = p.voteCount + 1 WHERE p.id = :id")
    int incrementVoteCount(@Param("id") Long id);
}
