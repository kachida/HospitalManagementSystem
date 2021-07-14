#! /bin/sh

set -e

echo
echo "**********Logging into vault******"

vault login token="00000000-0000-0000-0000-000000000000"

echo
echo "***** checking if the /secret path is already enabled"
if vault secrets list | grep secret; then
      echo "****** already enabled"
else
      echo "****** to be enabled"
	  echo "****** enabling the /secret path"
	  vault secrets enable -path=/secret kv
fi

echo
echo  "****** Adding secrets"
vault kv put /secret/hospitalmanagementsystem/test dbusername=root dbpassword=root dbjdbcurl=jdbc:mysql://mysqldb:3306/usersvc api_username=panda api_password=Bamboo1234 secret_key=rhino userServiceAccessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTYyNzMwNjI2MiwiaWF0IjoxNjI1ODM1MDMzfQ.pr83ACgZFvuInoPd2JsifT35OONivP5B8TxfuLUCbio patientServiceAccessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTYyNTgzODc1NiwiaWF0IjoxNjI1ODM1MTU2fQ.-jiDHtVBRqGvq9ZO4ApD2EzLaQvBjumGB1zRae7ighY