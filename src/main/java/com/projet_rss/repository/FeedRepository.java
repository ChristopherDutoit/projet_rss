package com.projet_rss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet_rss.domain.Feed;

import java.util.UUID;

@Repository
public interface FeedRepository extends JpaRepository<Feed, UUID> {

}