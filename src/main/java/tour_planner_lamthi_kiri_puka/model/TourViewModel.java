package tour_planner_lamthi_kiri_puka.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TourViewModel {
    private final StringProperty name = new SimpleStringProperty(this, "name", "");
    private final StringProperty description = new SimpleStringProperty(this, "description", "");
    private final StringProperty from = new SimpleStringProperty(this, "from", "");
    private final StringProperty to = new SimpleStringProperty(this, "to", "");
    private final StringProperty transportType = new SimpleStringProperty(this, "transportType", ""); // Added property for transport type

    private StringProperty imagePath;
    // Constructors

    public TourViewModel(String name, String description, String from, String to, String transportType, String imagePath) {
        this.name.set(name);
        this.description.set(description);
        this.from.set(from);
        this.to.set(to);
        this.transportType.set(transportType);
        this.imagePath = new SimpleStringProperty(imagePath);
    }

    // Name property
    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    // Description property
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    // From property
    public String getFrom() {
        return from.get();
    }

    public void setFrom(String value) {
        from.set(value);
    }

    public StringProperty fromProperty() {
        return from;
    }

    // To property
    public String getTo() {
        return to.get();
    }

    public void setTo(String value) {
        to.set(value);
    }

    public StringProperty toProperty() {
        return to;
    }

    // TransportType property
    public String getTransportType() {
        return transportType.get();
    }

    public void setTransportType(String value) {
        transportType.set(value);
    }

    public StringProperty transportTypeProperty() {
        return transportType;
    }

    public TourViewModel() {
        // Initialize imagePath along with other properties
        this.imagePath = new SimpleStringProperty();
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


    @Override
    public String toString() {
        return name.get() + ":  " + from.get()+ " - " + to.get();
    }

}
