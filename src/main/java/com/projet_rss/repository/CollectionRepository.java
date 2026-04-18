package com.projet_rss.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet_rss.domain.Collection;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, UUID> {
}
