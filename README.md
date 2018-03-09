INSERT:

Sample request:
curl -d 'operationtype=create&params_json={"phoneme":"hello","spoken_status":"answered","tone_analysis_expression":"smile","question":"how are you"}' https://DBQueryInterface.mybluemix.net/DBTask

Sample Respose:
{"_id":"26bc19c2047f4b03a3430f18e9763c58","time":"2018-03-09 11:40:14.355","status":"success"}


UPDATE:

Sample req:
curl -d 'operationtype=update&params_json={"_id":"26bc19c2047f4b03a3430f18e9763c58","spoken_status":"processing"}' https://DBQueryInterface.mybluemix.net/DBTask

Sample Response:
{"status":"success","desc":"record updated with status code201"}


SELECT:

curl -d 'operationtype=select&params_json={"_id":"26bc19c2047f4b03a3430f18e9763c58"}' https://DBQueryInterface.mybluemix.net/DBTask

Sample Response:
{"status":"succcess","record":"{\"phoneme\":\"hello\",\"spoken_status\":\"processing\",\"tone_analysis_expression\":\"smile\",\"question\":\"how are you\",\"TimeStamp\":1520595614355,\"_rev\":\"2-d82525178451e6098981b2d69b101b75\",\"_id\":\"26bc19c2047f4b03a3430f18e9763c58\"}"}


DELETE:
not implemented. Let me know if needed.