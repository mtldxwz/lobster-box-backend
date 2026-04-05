package com.lobsterbox.repository;

import com.lobsterbox.entity.Costume;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CostumeRepository extends JpaRepository<Costume, Long> {
    List<Costume> findByRarity(Costume.Rarity rarity);
    List<Costume> findByIsLimitedFalse();
}
