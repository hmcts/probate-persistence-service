output "vaultUri" {
  value = "${module.probate-persistence-service-vault.key_vault_uri}"
}

output "vaultName" {
  value = "${module.probate-persistence-service-vault.key_vault_name}"
}

output "microserviceName" {
  value = "${local.app_full_name}"
}