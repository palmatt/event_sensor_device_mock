package learn.service.messenger.topic;

public enum Topic {
	TEMPERATURE("Temperature"),
	HUMIDITY("Humidity"),
	PH_EC("PH and EC"),
	WATER_LEVEL("Water level"),
	LED("Led"),
	FAN("Fan"),
	CAMERA("Camera"),
	PUMP("Pump"),
	WINDOW_BLIND("Window Blind");

	private String name;

	private Topic(String name) {
		this.name = name;
	}

	public String value() {
		return name;
	}
}
