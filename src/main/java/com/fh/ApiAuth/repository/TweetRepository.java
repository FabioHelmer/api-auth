package com.fh.ApiAuth.repository;

import com.fh.ApiAuth.entities.Tweet;
import com.fh.ApiAuth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
