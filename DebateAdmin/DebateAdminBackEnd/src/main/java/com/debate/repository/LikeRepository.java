package com.debate.repository;

import com.debate.entity.Debate;
import com.debate.entity.Like;
import com.debate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 좋아요(Like) 리포지토리.
 */
@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    /** 토론과 사용자 조합으로 좋아요 존재 여부 조회 */
    Optional<Like> findByDebateAndUser(Debate debate, User user);

    /** 토론별 좋아요 개수 */
    long countByDebate(Debate debate);
}

