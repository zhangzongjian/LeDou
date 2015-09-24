package model;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

public class 乐斗项目 {
	protected Document mainDoc;

	public 乐斗项目(Document mainURL) {
		this.mainDoc = mainURL;
	}

	protected Map<String, Object> message = new LinkedHashMap<String, Object>();

	public Map<String, Object> getMessage() {
		return message;
	}
}
