package com.fun.ghostWriterModel;

import java.io.Serializable;

import io.vavr.collection.List;

public class InputGhostStringsModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1672932916981187518L;
	String[] ghostPromptinputStrings;

	public String[] getGhostPromptinputStrings() {
		return ghostPromptinputStrings;
	}

	public void setGhostPromptinputStrings(String[] ghostPromptinputStrings) {
		this.ghostPromptinputStrings = ghostPromptinputStrings;
	}
}
