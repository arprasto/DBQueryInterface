package com.ibm.db.util;
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


import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;

public class DBCommunicator {
	Logger logger = Logger.getLogger(DBCommunicator.class.getName());
	CloudantClient client = null;
	Database db = null;
	public DBCommunicator(String uname, String pass, String url,String db_name) throws MalformedURLException{
		client = ClientBuilder.account("digitalavatardb")
	            .username(uname)
	            .password(pass)
	            .url(new URL(url))
	            .build();
		
		db = client.database(db_name, true);
	}

	public String saveRecord(JSonDocumentTemplateClass obj){
		logger.log(Level.INFO,"saving to DB");
		String _id = db.save(obj).getId();
		logger.log(Level.INFO,"saved to DB");
		return _id;
	}
	
	public int updateRecord(JSonDocumentTemplateClass obj){
		return db.update(obj).getStatusCode();
	}
	
	public JSonDocumentTemplateClass getRecord(String id){
		logger.log(Level.INFO,"fetching the result");
		return db.find(JSonDocumentTemplateClass.class,id);
	}
	
	public List<JSonDocumentTemplateClass> getRecordList(String _id){
		return db.findByIndex("{  \"selector\": {    \"_id\": {      \"$eq\": "+_id+"    }},  \"fields\": [\"_id\",\"phoneme\",\"spoken_status\",\"tone_analysis_expression\",\"question\",\"TimeStamp\"]}",JSonDocumentTemplateClass.class);
	}
}
