package io.quarkiverse.filevault.configsource.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.quarkiverse.filevault.util.KeyStoreUtil;
import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;

public class FileVaultConfigSourceFactory implements ConfigSourceFactory {

  public static final String KEYSTORE_PATH = "quarkus.file.vault-config-source.keystore-path";
  public static final String KEYSTORE_SECRET = "quarkus.file.vault-config-source.keystore-secret";
  public static final String KEYSTORE_KEY = "quarkus.file.vault-config-source.encryption-key";

  @Override
  public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
    String configLocation = System.getProperty("quarkus.keystore.config");
    if (configLocation == null) {
      configLocation = System.getProperty("quarkus.config.locations");
    }
    if (configLocation == null || !new File(configLocation).exists()) {
      return new ArrayList<>();
    }
    Properties properties = new Properties();
    try {
      InputStream stream = new FileInputStream(configLocation);
      properties.load(stream);
      String keyStorePath = properties.getProperty(KEYSTORE_PATH);
      String keyStoreSecret = properties.getProperty(KEYSTORE_SECRET);
      String keyStoreKey = properties.getProperty(KEYSTORE_KEY);
      if (keyStorePath != null) {
        Map<String, KeyStoreUtil.KeyStoreEntry> stringKeyStoreEntryMap =
            KeyStoreUtil.readKeyStore(keyStorePath, keyStoreSecret, keyStoreKey);
        return List.of(new FileVaultConfigSource(stringKeyStoreEntryMap));
      } else {
        return new ArrayList<>();
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
