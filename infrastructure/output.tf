output "vaultUri" {
  value = "${module.probate-persistence-service-vault.key_vault_uri}"
}

output "vaultName" {
  value = "${local.vaultName}"
}

output "microserviceName" {
  value = "${local.app_full_name}"
}