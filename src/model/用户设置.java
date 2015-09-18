package model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class 用户设置 implements Serializable{
	private static final long serialVersionUID = -6836853122602481782L;
	private Map<String, Object> setting = new LinkedHashMap<String, Object>();

	public final Map<String, Object> getSetting() {
		return setting;
	}

	public final void setSetting(Map<String, Object> setting) {
		this.setting = setting;
	}
	
}
