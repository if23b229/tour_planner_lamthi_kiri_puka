package tour_planner_lamthi_kiri_puka.repository;

import tour_planner_lamthi_kiri_puka.model.TourLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourLogRepository extends JpaRepository<TourLog, Long> {
    List<TourLog> findByTourId(Long tourId);
}
