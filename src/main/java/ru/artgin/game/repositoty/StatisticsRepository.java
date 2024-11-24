package ru.artgin.game.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.artgin.game.models.Statistic;

import java.util.List;


/**
 *It is the repository interface for work with statistic data.
 */
public interface StatisticsRepository extends JpaRepository<Statistic, Long> {
    Statistic findTopByOrderByIdDesc();
    List<Statistic> findByUserId(Long id);
    List<Statistic> findAll();
    boolean existsBy();
}
