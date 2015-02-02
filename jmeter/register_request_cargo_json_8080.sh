#!/bin/bash
EMAIL="namn%40kommun.se"
PASSWORD="password"
NAME="F%C3%B6RNAMN+EFTERNAMN"
COMMENT="KanskeFelet+%C3%A4r+%C3%A5tg%C3%A4rdat"
REPORT_ID=11
MYHOST="http://localhost:8080"
UPDATE="${MYHOST}/report/update"
LOGIN="${MYHOST}/auth"
EXTRA_PARMS="submit_update=1&may_show_name=1&add_alert=0"
STATUS="fixed"

SERVICE="Halka"
CATEGORY="Halka"
ID="4711"
SUBJECT="SUBJECT"
DETAIL="DETAIL"
LAT=59.3038489
LON=18.1258670

API_KEY=xyz
SERVICE_CODE=2
FIRST_NAME="F%C3%B6RNAMN"
LAST_NAME="EFTERNAMN"
FORMAT="json"
FORMAT="xml"

JURISDICTION_ID="jurisdiction_id"
DEVICE_ID="device_id" 
DESCRIPTION="description" 
ACCOUNT_ID="account_id"
PHONE="1234567890"
MEDIA_URL=http://www.motrice.se


REGISTER="${MYHOST}/restrice/jersey/runtime/open311/v2/requests.json"
DATA="{\"service_code\":$SERVICE_CODE,\"first_name\":\"$FIRST_NAME\"}" 
####echo $DATA
curl  -H "Content-Type: application/x-www-form-urlencoded; charset=utf-8" --data "jurisdiction_id=$JURISDICTION_ID&description=$DESCRIPTION&account_id=$ACCOUNT_ID&phone=$PHONE&media_url=$MEDIA_URL&device_id=$DEVICE_ID&api_key=$API_KEY&service_code=$SERVICE_CODE&first_name=$FIRST_NAME&last_name=$LAST_NAME&email=$EMAIL&lat=$LAT&long=$LON&id=$ID" ${REGISTER}
echo " " 

