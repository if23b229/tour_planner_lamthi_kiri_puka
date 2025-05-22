package tour_planner_lamthi_kiri_puka.repository;

import tour_planner_lamthi_kiri_puka.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
}
