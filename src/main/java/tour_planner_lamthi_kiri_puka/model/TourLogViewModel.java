package tour_planner_lamthi_kiri_puka.model;

public class TourLogViewModel {
    private Long id;
    private String logDate;
    private String comment;
    private String difficulty;
    private String totalDistance;
    private String totalTime;
    private String rating;
    private String logDetails;

    public TourLogViewModel() {}

    public TourLogViewModel(TourLog tourLog) {
        this.id = tourLog.getId();
        this.logDate = tourLog.getLogDate();
        this.comment = tourLog.getComment();
        this.difficulty = tourLog.getDifficulty();
        this.totalDistance = tourLog.getTotalDistance();
        this.totalTime = tourLog.getTotalTime();
        this.rating = tourLog.getRating();
        this.logDetails = tourLog.getLogDetails();
    }

    public static TourLogViewModel fromTourLog(TourLog tourLog) {
        TourLogViewModel viewModel = new TourLogViewModel();
        viewModel.setId(tourLog.getId());
        viewModel.setLogDate(tourLog.getLogDate());
        viewModel.setComment(tourLog.getComment());
        viewModel.setDifficulty(tourLog.getDifficulty());
        viewModel.setTotalDistance(tourLog.getTotalDistance());
        viewModel.setTotalTime(tourLog.getTotalTime());
        viewModel.setRating(tourLog.getRating());
        viewModel.setLogDetails(tourLog.getLogDetails());
        return viewModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogDate() {
        return logDate;
    }

    public void setLogDate(String logDate) {
        this.logDate = logDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getLogDetails() {
        return logDetails;
    }

    public void setLogDetails(String logDetails) {
        this.logDetails = logDetails;
    }
}
