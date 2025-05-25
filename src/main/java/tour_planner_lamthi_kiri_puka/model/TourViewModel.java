package tour_planner_lamthi_kiri_puka.model;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourViewModel {
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty description = new SimpleStringProperty(this, "description", "");
    private final StringProperty origin = new SimpleStringProperty(this, "origin", "");
    private final StringProperty destination = new SimpleStringProperty(this, "destination", "");
    private final StringProperty transportType = new SimpleStringProperty(this, "transportType", "");
    private final StringProperty imagePath = new SimpleStringProperty(this, "imagePath", "");
    private Long id;

     public TourViewModel() { }

    public TourViewModel(String name, String description, String origin, String destination, String transportType, String imagePath) {
        this.name.set(name);
        this.description.set(description);
        this.origin.set(origin);
        this.destination.set(destination);
        this.transportType.set(transportType);
        this.imagePath.set(imagePath);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getOrigin() {
        return origin.get();
    }

    public void setOrigin(String value) {
        origin.set(value);
    }

    public StringProperty originProperty() {
        return origin;
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String value) {
        destination.set(value);
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public String getTransportType() {
        return transportType.get();
    }

    public void setTransportType(String value) {
        transportType.set(value);
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public String getImagePath() {
        return imagePath.get();
    }

    public void setImagePath(String imagePath) {
        this.imagePath.set(imagePath);
    }

    public StringProperty imagePathProperty() {
        return imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name.get() + ": " + origin.get() + " - " + destination.get();
    }

    public Tour toTour() {
        return new Tour(name.get(), description.get(), origin.get(), destination.get(), transportType.get(), imagePath.get());
    }
}