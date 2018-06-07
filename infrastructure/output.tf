output "vaultUri" {
  value = "${module.probate-persistence-service-vault.key_vault_uri}"
}

output "vaultName" {
  value = "${module.probate-persistence-service-vault.key_vault_name}"
}
