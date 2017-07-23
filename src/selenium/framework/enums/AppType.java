package selenium.framework.enums;

public enum AppType {
	Accolade("Accolade"),
	SystemLevelErrors("SystemLevelErrors");
	
	private final String value;

	AppType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AppType fromValue(String v) {
        for (AppType c: AppType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
