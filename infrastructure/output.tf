output "vaultUri" {
  value = "${data.azurerm_key_vault.probate_key_vault.vault_uri}"
}

output "vaultName" {
  value = "${local.vaultName}"
}

output "microserviceName" {
  value = "${local.app_full_name}"
}