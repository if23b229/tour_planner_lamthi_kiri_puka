package tour_planner_lamthi_kiri_puka.service;

import tour_planner_lamthi_kiri_puka.model.Tour;
import tour_planner_lamthi_kiri_puka.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    private static final Logger logger = LogManager.getLogger(TourService.class);

    public List<Tour> findAll() {
        logger.info("Fetching all tours");
        return tourRepository.findAll();
    }

    public Optional<Tour> findById(Long id) {
        logger.info("Fetching tour with ID: " + id);
        return tourRepository.findById(id);
    }

    public Tour save(Tour tour) {
        logger.info("Saving tour: " + tour.getName());
        return tourRepository.save(tour);
    }

    public void deleteById(Long id) {
        logger.info("Deleting tour with ID: " + id);
        tourRepository.deleteById(id);
    }
}
