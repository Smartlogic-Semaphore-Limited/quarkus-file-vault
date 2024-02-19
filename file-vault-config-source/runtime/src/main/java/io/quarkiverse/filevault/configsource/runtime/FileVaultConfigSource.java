package io.quarkiverse.filevault.configsource.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.quarkiverse.filevault.util.KeyStoreUtil;
import io.quarkiverse.filevault.util.KeyStoreUtil.KeyStoreEntry;

public class FileVaultConfigSource implements ConfigSource {
  private Map<String, KeyStoreEntry> storeProperties = new HashMap<>();

  public FileVaultConfigSource(FileVaultBootstrapConfig config) {
    storeProperties = KeyStoreUtil.readKeyStore(config.keystorePath.orElse(null),
        config.keystoreSecret.orElse(null), config.encryptionKey.orElse(null));
  }

  public FileVaultConfigSource(Map<String, KeyStoreEntry> properties) {
    storeProperties = properties;
  }

  @Override
  public String getName() {
    return "file-vault-config-source";
  }

  @Override
  public int getOrdinal() {
    return 270;
  }

  @Override
  public Set<String> getPropertyNames() {
    return Set.copyOf(storeProperties.keySet());
  }

  @Override
  public String getValue(String propertyName) {
    return storeProperties.containsKey(propertyName) ? storeProperties.get(propertyName).getValue()
        : null;
  }
}
