output "vaultUri" {
  value = "${module.pro-persistence-service-vault.key_vault_uri}"
}

output "vaultName" {
  value = "${module.pro-persistence-service-vault.key_vault_name}"
}

output "microserviceName" {
  value = "${local.app_full_name}"
}