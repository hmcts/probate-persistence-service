provider "vault" {
  //  # It is strongly recommended to configure this provider through the
  //  # environment variables described above, so that each user can have
  //  # separate credentials set in the environment.
  //  #
  //  # This will default to using $VAULT_ADDR
  //  # But can be set explicitly
  address = "https://vault.reform.hmcts.net:6200"
}


// locals {
//   aseName = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
//   //java_proxy_variables: "-Dhttp.proxyHost=${var.proxy_host} -Dhttp.proxyPort=${var.proxy_port} -Dhttps.proxyHost=${var.proxy_host} -Dhttps.proxyPort=${var.proxy_port}"
//   app_full_name = "${var.product}-${var.microservice}"


//   local_env = "${(var.env == "preview" || var.env == "spreview") ? (var.env == "preview" ) ? "aat" : "saat" : var.env}"
//   local_ase = "${(var.env == "preview" || var.env == "spreview") ? (var.env == "preview" ) ? "core-compute-aat" : "core-compute-saat" : local.aseName}"


//   //probate_frontend_hostname = "probate-frontend-aat.service.core-compute-aat.internal"
//   previewVaultName = "${var.raw_product}-aat"
//   nonPreviewVaultName = "${var.raw_product}-${var.env}"
//   vaultName = "${(var.env == "preview" || var.env == "spreview") ? local.previewVaultName : local.nonPreviewVaultName}"

//   oldVaultName = "${(var.env == "preview" || var.env == "spreview") ? "pro-persist-ser-aat" : "pro-persist-ser-${var.env}" }"
//   local.vaultName = "${(var.env == "aat") ? local.oldVaultName : local.nonPreviewVaultName}"
// }

// data "azurerm_key_vault" "probate_key_vault" {
//   name = "${local.vaultName}"
//   resource_group_name = "${local.vaultName}"
// }

// data "azurerm_key_vault_secret" "spring_application_json_persistence_service" {
//   name = "spring-application-json-persistence-service"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

// data "azurerm_key_vault_secret" "probate_postgresql_user" {
//   name = "probate-postgresql-user"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

// data "azurerm_key_vault_secret" "probate_postgresql_database" {
//   name = "probate-postgresql-database"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }


// module "probate-persistence-service" {
//   source = "git@github.com:hmcts/moj-module-webapp.git?ref=master"
//   product = "${local.app_full_name}"
//   location = "${var.location}"
//   env = "${var.env}"
//   ilbIp = "${var.ilbIp}"
//   is_frontend  = false
//   subscription = "${var.subscription}"
//   asp_name     = "${var.asp_name}"
//   capacity     = "${var.capacity}"
//   appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"
//   common_tags  = "${var.common_tags}"
//   asp_rg       = "${var.asp_rg}"

//   app_settings = {
//      // Logging vars
//     REFORM_TEAM = "${var.product}"
//     REFORM_SERVICE_NAME = "${var.microservice}"
//     REFORM_ENVIRONMENT = "${var.env}"
  

//     DEPLOYMENT_ENV= "${var.deployment_env}"

//     PROBATE_POSTGRESQL_USER = "${module.probate-persistence-db.user_name}"
//     PROBATE_POSTGRESQL_PASSWORD = "${module.probate-persistence-db.postgresql_password}"
//     PROBATE_POSTGRESQL_DATABASE = "${module.probate-persistence-db.postgresql_database}?ssl=true&amp;sslfactory=org.postgresql.ssl.NonValidatingFactory"
//     SPRING_APPLICATION_JSON = "${data.azurerm_key_vault_secret.spring_application_json_persistence_service.value}"
//     PROBATE_POSTGRESQL_HOSTNAME =  "${module.probate-persistence-db.host_name}"
//     PROBATE_POSTGRESQL_PORT = "${module.probate-persistence-db.postgresql_listen_port}"
//     PROBATE_PERSISTENCE_SHOW_SQL = "${var.probate_postgresql_show_sql}"
//     LIQUIBASE_AT_STARTUP = "${var.liquibase_at_startup}"
//     java_app_name = "${var.microservice}"
//     LOG_LEVEL = "${var.log_level}"
//     //ROOT_APPENDER = "JSON_CONSOLE" //Remove json logging

//   }
// }

data "vault_generic_secret" "probate_postgresql_user" {
  path = "secret/${var.vault_section}/probate/probate_postgresql_user"
}

data "vault_generic_secret" "probate_postgresql_password" {
  path = "secret/${var.vault_section}/probate/probate_postgresql_password"
}

data "vault_generic_secret" "probate_postgresql_database" {
  path = "secret/${var.vault_section}/probate/probate_postgresql_database"
}

data "vault_generic_secret" "spring_application_json_persistence_service" {
  path = "secret/${var.vault_section}/probate/spring_application_json_persistence_service"
}

data "vault_generic_secret" "probate_postgresql_hostname" {
  path = "secret/${var.vault_section}/probate/probate_postgresql_hostname"
}

data "vault_generic_secret" "probate_postgresql_port" {
  path = "secret/${var.vault_section}/probate/probate_postgresql_port"
}

locals {
  aseName = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
  //java_proxy_variables: "-Dhttp.proxyHost=${var.proxy_host} -Dhttp.proxyPort=${var.proxy_port} -Dhttps.proxyHost=${var.proxy_host} -Dhttps.proxyPort=${var.proxy_port}"
  app_full_name = "${var.product}-${var.microservice}"

  //probate_frontend_hostname = "probate-frontend-aat.service.core-compute-aat.internal"
  previewVaultName = "pro-persist-ser"
  nonPreviewVaultName = "pro-persist-ser-${var.env}"
  vaultName = "${(var.env == "preview" || var.env == "spreview") ? local.previewVaultName : local.nonPreviewVaultName}"

  nonPreviewVaultUri = "${module.probate-persistence-service-vault.key_vault_uri}"
  previewVaultUri = "https://pro-persist-ser-aat.vault.azure.net/"
  vaultUri = "${(var.env == "preview" || var.env == "spreview") ? local.previewVaultUri : local.nonPreviewVaultUri}"

}

module "probate-persistence-service" {
  source = "git@github.com:hmcts/moj-module-webapp.git?ref=master"
  product = "${local.app_full_name}"
  location = "${var.location}"
  env = "${var.env}"
  ilbIp = "${var.ilbIp}"
  is_frontend  = false
  subscription = "${var.subscription}"
  asp_name     = "${var.product}-${var.env}-asp"
  capacity     = "${var.capacity}"
  appinsights_instrumentation_key = "${var.appinsights_instrumentation_key}"
  common_tags  = "${var.common_tags}"

  app_settings = {
     // Logging vars
    REFORM_TEAM = "${var.product}"
    REFORM_SERVICE_NAME = "${var.microservice}"
    REFORM_ENVIRONMENT = "${var.env}"
  

    DEPLOYMENT_ENV= "${var.deployment_env}"

    PROBATE_POSTGRESQL_USER = "${module.probate-persistence-db.user_name}"
    PROBATE_POSTGRESQL_PASSWORD = "${module.probate-persistence-db.postgresql_password}"
    PROBATE_POSTGRESQL_DATABASE = "${module.probate-persistence-db.postgresql_database}?ssl=true&amp;sslfactory=org.postgresql.ssl.NonValidatingFactory"
    SPRING_APPLICATION_JSON = "${data.vault_generic_secret.spring_application_json_persistence_service.data["value"]}"
    PROBATE_POSTGRESQL_HOSTNAME =  "${module.probate-persistence-db.host_name}"
    PROBATE_POSTGRESQL_PORT = "${module.probate-persistence-db.postgresql_listen_port}"
    PROBATE_PERSISTENCE_SHOW_SQL = "${var.probate_postgresql_show_sql}"
    LIQUIBASE_AT_STARTUP = "${var.liquibase_at_startup}"
    java_app_name = "${var.microservice}"
    LOG_LEVEL = "${var.log_level}"
    //ROOT_APPENDER = "JSON_CONSOLE" //Remove json logging

  }
}

module "probate-persistence-db" {
  source = "git@github.com:hmcts/moj-module-postgres?ref=master"
  product = "${local.app_full_name}-postgres-db"
  location = "${var.location}"
  env = "${var.env}"
  postgresql_user = "${data.azurerm_key_vault_secret.probate_postgresql_user.value}"
  database_name = "${data.azurerm_key_vault_secret.probate_postgresql_database.value}"
  sku_name = "GP_Gen5_2"
  sku_tier = "GeneralPurpose"
  storage_mb = "51200"
  common_tags  = "${var.common_tags}"
}

////////////////////////////////
// Populate Vault with DB info
////////////////////////////////

// resource "azurerm_key_vault_secret" "POSTGRES-USER" {
//   name = "${local.app_full_name}-POSTGRES-USER"
//   value = "${module.probate-persistence-db.user_name}"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

// resource "azurerm_key_vault_secret" "POSTGRES-PASS" {
//   name = "${local.app_full_name}-POSTGRES-PASS"
//   value = "${module.probate-persistence-db.postgresql_password}"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

// resource "azurerm_key_vault_secret" "POSTGRES_HOST" {
//   name = "${local.app_full_name}-POSTGRES-HOST"
//   value = "${module.probate-persistence-db.host_name}"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

// resource "azurerm_key_vault_secret" "POSTGRES_PORT" {
//   name = "${local.app_full_name}-POSTGRES-PORT"
//   value = "${module.probate-persistence-db.postgresql_listen_port}"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

// resource "azurerm_key_vault_secret" "POSTGRES_DATABASE" {
//   name = "${local.app_full_name}-POSTGRES-DATABASE"
//   value = "${module.probate-persistence-db.postgresql_database}"
//   vault_uri = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
// }

###################################################
#
#           Old Vault
#
##################################################

module "probate-persistence-service-vault" {
  source              = "git@github.com:hmcts/moj-module-key-vault?ref=master"
  name                = "${local.oldVaultName}"
  product             = "${var.product}"
  env                 = "${var.env}"
  tenant_id           = "${var.tenant_id}"
  object_id           = "${var.jenkins_AAD_objectId}"
  resource_group_name = "${module.probate-persistence-service.resource_group_name}"
  product_group_object_id = "33ed3c5a-bd38-4083-84e3-2ba17841e31e"
}

resource "azurerm_key_vault_secret" "POSTGRES-USER" {
  name = "${local.app_full_name}-POSTGRES-USER"
  value = "${module.probate-persistence-db.user_name}"
  vault_uri = "${module.probate-persistence-service-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES-PASS" {
  name = "${local.app_full_name}-POSTGRES-PASS"
  value = "${module.probate-persistence-db.postgresql_password}"
  vault_uri = "${module.probate-persistence-service-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES_HOST" {
  name = "${local.app_full_name}-POSTGRES-HOST"
  value = "${module.probate-persistence-db.host_name}"
  vault_uri = "${module.probate-persistence-service-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES_PORT" {
  name = "${local.app_full_name}-POSTGRES-PORT"
  value = "${module.probate-persistence-db.postgresql_listen_port}"
  vault_uri = "${module.probate-persistence-service-vault.key_vault_uri}"
}

resource "azurerm_key_vault_secret" "POSTGRES_DATABASE" {
  name = "${local.app_full_name}-POSTGRES-DATABASE"
  value = "${module.probate-persistence-db.postgresql_database}"
  vault_uri = "${module.probate-persistence-service-vault.key_vault_uri}"
}


