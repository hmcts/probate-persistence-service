provider "vault" {
  //  # It is strongly recommended to configure this provider through the
  //  # environment variables described above, so that each user can have
  //  # separate credentials set in the environment.
  //  #
  //  # This will default to using $VAULT_ADDR
  //  # But can be set explicitly
  address = "https://vault.reform.hmcts.net:6200"
}


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

locals {
  aseName = "${data.terraform_remote_state.core_apps_compute.ase_name[0]}"
  //java_proxy_variables: "-Dhttp.proxyHost=${var.proxy_host} -Dhttp.proxyPort=${var.proxy_port} -Dhttps.proxyHost=${var.proxy_host} -Dhttps.proxyPort=${var.proxy_port}"
}

module "probate-persistence-service" {
  source = "git@github.com:hmcts/moj-module-webapp.git?ref=master"
  product = "${var.product}-${var.microservice}"
  location = "${var.location}"
  env = "${var.env}"
  ilbIp = "${var.ilbIp}"
  is_frontend  = false
  subscription = "${var.subscription}"

  app_settings = {

	  // Logging vars
    REFORM_TEAM = "${var.product}"
    REFORM_SERVICE_NAME = "${var.microservice}"
    REFORM_ENVIRONMENT = "${var.env}"
  

    DEPLOYMENT_ENV= "${var.deployment_env}"

    PROBATE_POSTGRESQL_USER = "${data.vault_generic_secret.probate_postgresql_user.data["value"]}"
    PROBATE_POSTGRESQL_PASSWORD = "${data.vault_generic_secret.probate_postgresql_password.data["value"]}"
    PROBATE_POSTGRESQL_DATABASE = "${data.vault_generic_secret.probate_postgresql_database.data["value"]}"
    SPRING_APPLICATION_JSON = "${data.vault_generic_secret.spring_application_json_persistence_service.data["value"]}"
    PROBATE_POSTGRESQL_HOSTNAME = "${var.probate_postgresql_hostname}"
    PROBATE_POSTGRESQL_PORT = "${var.probate_postgresql_port}"
    PROBATE_PERSISTENCE_SHOW_SQL = "${var.probate_postgresql_show_sql}"
    
    java_app_name = "${var.microservice}"
    LOG_LEVEL = "${var.log_level}"
    ROOT_APPENDER = "JSON_CONSOLE"

  }
}

module "probate-persistence-service-vault" {
  source              = "git@github.com:hmcts/moj-module-key-vault?ref=master"
  name                = "pro-persist-ser-${var.env}"
  product             = "${var.product}"
  env                 = "${var.env}"
  tenant_id           = "${var.tenant_id}"
  object_id           = "${var.jenkins_AAD_objectId}"
  resource_group_name = "${module.probate-persistence-service.resource_group_name}"
  product_group_object_id = "68839600-92da-4862-bb24-1259814d1384"
}