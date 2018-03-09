package com.ibm.db.util;

import java.sql.Timestamp;

/**
 *****************************************************************************
 * Copyright (c) 2017 IBM Corporation and other Contributors.

 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Arpit Rastogi - Initial Contribution
 *****************************************************************************
 */


public class JSonDocumentTemplateClass {
	private String ID=null, phoneme=null, spoken_status=null, tone_analysis_expression=null, question=null;
	private long TimeStamp=0L;
	
	public long getTimeStamp() {
		return TimeStamp;
	}
	
	public void setTimeStamp(long timeStamp) {
		TimeStamp = timeStamp;
	}

	public String getID() {
		return _id;
	}

	public void setID(String iD) {
		ID = _id;
	}

	public String getPhoneme() {
		return phoneme;
	}

	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
	}

	public String getSpoken_status() {
		return spoken_status;
	}

	public void setSpoken_status(String spoken_status) {
		this.spoken_status = spoken_status;
	}

	public String getTone_analysis_expression() {
		return tone_analysis_expression;
	}

	public void setTone_analysis_expression(String tone_analysis_expression) {
		this.tone_analysis_expression = tone_analysis_expression;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String get_rev() {
		return _rev;
	}

	public void set_rev(String _rev) {
		this._rev = _rev;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	private String _rev = null;
	private String _id = null;

	public JSonDocumentTemplateClass(String phoneme, String spoken_status, String tone_analysis_expression,
			String question, long timeStamp) {
		super();
		this.phoneme = phoneme;
		this.spoken_status = spoken_status;
		this.tone_analysis_expression = tone_analysis_expression;
		this.question = question;
		this.TimeStamp = timeStamp;
	}

}
