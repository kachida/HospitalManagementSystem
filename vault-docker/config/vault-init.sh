VAULT_DEV_TOKEN=00000000-0000-0000-0000-000000000000
export VAULT_ADDR=http://127.0.0.1:8200
export VAULT_TOKEN=${VAULT_DEV_TOKEN}
export VAULT_DEV_ROOT_TOKEN_ID=${VAULT_DEV_TOKEN}

echo "--Vault server initiatialization-"

vault server -dev -dev-root-token-id ${VAULT_DEV_TOKEN}

echo "--Vault server initiatied successfully-"

vault login ${VAULT_DEV_TOKEN}

echo "--Vault Login is successful-"

vault secrets enable -version=1 kv

echo "--Vault Secrets enabled-"

vault kv put secret/hospitalmanagementsystem/test dbusername=root dbpassword=root dbjdbcurl=jdbc:mysql://localhost:3306/usersvc api_username=panda api_password=Bamboo1234$ secret_key=rhino userServiceAccessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTYyNzMwNjI2MiwiaWF0IjoxNjI1ODM1MDMzfQ.pr83ACgZFvuInoPd2JsifT35OONivP5B8TxfuLUCbio patientServiceAccessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5kYSIsImV4cCI6MTYyNTgzODc1NiwiaWF0IjoxNjI1ODM1MTU2fQ.-jiDHtVBRqGvq9ZO4ApD2EzLaQvBjumGB1zRae7ighY
echo "----------------------------"
echo "--Vault KV Pairs are stored-"
echo "----------------------------"