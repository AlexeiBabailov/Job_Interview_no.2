import java.io.Serializable;

public class PointOfInterest implements Serializable {

	private String name;
	private Double X_coordinate;
	private Double Y_coordinate;

	//constructor - point of interest
	public PointOfInterest(String name, Double X_coordinate,Double Y_coordinate) {
		this.name = name;
		this.X_coordinate = X_coordinate;
		this.Y_coordinate = Y_coordinate;
	}
	
	public PointOfInterest() {
		this.name = null;
		this.X_coordinate = null;
		this.Y_coordinate = null;
	}

	public String getName() {
		return name;
	}

	//currently not in use - for forwarder development such as updating
	public void setName(String name) {
		this.name = name;
	}

	public Double getX_coordinate() {
		return X_coordinate;
	}

	//currently not in use - for forwarder development such as updating
	public void setX_coordinate(Double x_coordinate) {
		X_coordinate = x_coordinate;
	}

	public Double getY_coordinate() {
		return Y_coordinate;
	}

	//currently not in use - for forwarder development such as updating
	public void setY_coordinate(Double y_coordinate) {
		Y_coordinate = y_coordinate;
	}
}
