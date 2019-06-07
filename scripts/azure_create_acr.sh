#!/bin/sh
RESOURCE_GROUP=orcctestpg
LOCATION=westeurope
ACR_NAME=orcctestsze
EVENTHUB_NAMESPACE=orcctestnspg
COSMOSDB_ACCOUNT_NAME=orccdbpg
echo "az group create"
az group create -l $LOCATION -n $RESOURCE_GROUP
echo "az eventhubs namespace create"
az eventhubs namespace create --resource-group $RESOURCE_GROUP --name $EVENTHUB_NAMESPACE --location $LOCATION
echo "az cosmosdb create"
az cosmosdb create --name $COSMOSDB_ACCOUNT_NAME --resource-group $RESOURCE_GROUP --kind MongoDB
echo "az acr create"
az acr create --name $ACR_NAME --resource-group $RESOURCE_GROUP --sku Standard --admin-enabled true
echo "az acr login"
az acr login --name $ACR_NAME
echo "az acr credential"
az acr credential show --name $ACR_NAME
echo "az eventhubs namespace authorization-rule keys list"
az eventhubs namespace authorization-rule keys list --name RootManageSharedAccessKey --namespace-name $EVENTHUB_NAMESPACE --resource-group $RESOURCE_GROUP
echo "az cosmosdb list-connection-strings"
az cosmosdb list-connection-strings --name $COSMOSDB_ACCOUNT_NAME --resource-group $RESOURCE_GROUP
