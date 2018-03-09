package com.ibm.db;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.db.util.DBCommunicator;
import com.ibm.db.util.JSonDocumentTemplateClass;
import com.ibm.db.util.PatchedCredentialUtils;

/**
 * Servlet implementation class DBSelect
 */
@WebServlet("/DBTask")
public class DBSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private static final String DBName="digitalavatardb";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBSelect() {
        super();
    }
    
	@Override
	public void init(ServletConfig config) throws ServletException {
		try {
			config.getServletContext().setAttribute("db_svc",new DBCommunicator(
					PatchedCredentialUtils.getDBuname(null), 
					PatchedCredentialUtils.getDBpass(null),
					PatchedCredentialUtils.getDBurl(null), DBSelect.DBName));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DBCommunicator dbsvc = (DBCommunicator)request.getServletContext().getAttribute("db_svc");
		Map<String,String[]> parammap = request.getParameterMap();
		Set<String> keys = parammap.keySet();
		String phoneme = null,spoken_status=null,tone_analysis_expression=null,question=null,_id=null;
		JsonObject json_params = null;
		JsonObject response_json = new JsonObject();
		boolean json_error=false;

		for(String key:keys){
			for(String paramval:parammap.get(key)){
				System.out.println(key+"------>"+paramval);			
			}
		}
		response.setContentType("application/json");
		
		String method = null;
		
		try{
			method = request.getParameter("operationtype");
		json_params = new JsonParser().parse(request.getParameter("params_json")).getAsJsonObject();
		}catch(com.google.gson.JsonSyntaxException e){
			response_json.addProperty("status","failure");
			response_json.addProperty("param_desc",e.getMessage());
			json_error=true;
		}catch(NullPointerException e){
			response_json.addProperty("status","failure");
			response_json.addProperty("param_desc","missing params_json or/and operationtype parameters");
			json_error=true;			
		}
		try{
			phoneme = json_params.get("phoneme").getAsString()==null || json_params.get("phoneme").getAsString()=="" ?null:json_params.get("phoneme").getAsString();
		}catch(NullPointerException e){};		
		try{
			spoken_status = json_params.get("spoken_status").getAsString()==null || json_params.get("spoken_status").getAsString()=="" ?null:json_params.get("spoken_status").getAsString();
		}catch(NullPointerException e){};
		try{
			tone_analysis_expression = json_params.get("tone_analysis_expression").getAsString()==null || json_params.get("tone_analysis_expression").getAsString()=="" ?null:json_params.get("tone_analysis_expression").getAsString(); 
		}catch(NullPointerException e){};
		try{
			question = json_params.get("question").getAsString()==null || json_params.get("question").getAsString()=="" ?null:json_params.get("question").getAsString();
		}catch(NullPointerException e){};
		try{
			_id = json_params.get("_id").getAsString()==null || json_params.get("_id").getAsString()=="" ?null:json_params.get("_id").getAsString();
		}catch(NullPointerException e){};


		if(method.equalsIgnoreCase("select") && !json_error){
			if(_id==null){
				response_json.addProperty("status","failure");
				response_json.addProperty("desc","_id not supplied");
			}else{
				JSonDocumentTemplateClass rec = dbsvc.getRecord(_id);
					response_json.addProperty("status","succcess");
					response_json.addProperty("record",new Gson().toJson(rec));
					System.out.println(new Gson().toJson(rec));
			}
		}else
		if(method.equalsIgnoreCase("update") && !json_error){
			if(_id==null){
				response_json.addProperty("status","failure");
				response_json.addProperty("desc","_id not supplied");
			}else{
				try{
				JSonDocumentTemplateClass rec = dbsvc.getRecord(_id);
				if(rec !=null){
					rec.setPhoneme(phoneme==null?rec.getPhoneme():phoneme);
					rec.setQuestion(question==null?rec.getQuestion():question);
					rec.setSpoken_status(spoken_status==null?rec.getSpoken_status():spoken_status);
					rec.setTone_analysis_expression(tone_analysis_expression==null?rec.getTone_analysis_expression():tone_analysis_expression);
					response_json.addProperty("status","success");
					response_json.addProperty("desc","record updated with status code"+dbsvc.updateRecord(rec));
				}else{
					response_json.addProperty("status","error");
					response_json.addProperty("desc","no record exists, check _id");
				}
				}catch(com.cloudant.client.org.lightcouch.NoDocumentException e){
					response_json.addProperty("status","failure");
					response_json.addProperty("desc",e.getMessage());
				}
			}
			
		}else
		if(method.equalsIgnoreCase("delete") && !json_error){
			response_json.addProperty("status","success");
			response_json.addProperty("desc","coming soon");
		}else
		if(method.equalsIgnoreCase("create") && !json_error){
			if(phoneme==null && spoken_status==null && tone_analysis_expression==null && question==null){
				response_json.addProperty("status","error");
				response_json.addProperty("desc","atleast one of params(phoneme,spoken_status,tone_analysis_expression,question) needed");
			}
			else{
			long currentTimeMillis = System.currentTimeMillis();
			JSonDocumentTemplateClass json_bean = new JSonDocumentTemplateClass(
					phoneme!=null?phoneme:"",
					spoken_status!=null?spoken_status:"",
					tone_analysis_expression!=null?tone_analysis_expression:"",
					question!=null?question:"",currentTimeMillis);
			String id = dbsvc.saveRecord(json_bean);
			response_json.addProperty("_id",id);
			response_json.addProperty("time",new Timestamp(currentTimeMillis).toString());
			response_json.addProperty("status","success");
			}
		}else {
			response_json.addProperty("status","failure");
			response_json.addProperty("desc","invalidoperation or jsonerror");
		}
		response.getWriter().print(response_json);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
