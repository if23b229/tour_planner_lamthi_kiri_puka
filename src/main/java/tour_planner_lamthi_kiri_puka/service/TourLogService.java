package tour_planner_lamthi_kiri_puka.service;

import tour_planner_lamthi_kiri_puka.model.TourLog;
import tour_planner_lamthi_kiri_puka.repository.TourLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourLogService {
    private final TourLogRepository tourLogRepository;

    @Autowired
    public TourLogService(TourLogRepository tourLogRepository) {
        this.tourLogRepository = tourLogRepository;
    }

    public List<TourLog> findByTourId(Long tourId) {
        return tourLogRepository.findByTourId(tourId);
    }

    public TourLog findById(Long id) {
        Optional<TourLog> log = tourLogRepository.findById(id);
        return log.orElse(null);
    }

    public void deleteById(Long id) {
        tourLogRepository.deleteById(id);
    }

    public TourLog saveLog(TourLog tourLog) {
        return tourLogRepository.save(tourLog);
    }

    public TourLog updateLog(TourLog tourLog) {
        return tourLogRepository.save(tourLog);
    }
}
