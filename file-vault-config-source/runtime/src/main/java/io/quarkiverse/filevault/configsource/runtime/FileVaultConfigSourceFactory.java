package io.quarkiverse.filevault.configsource.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.spi.ConfigSource;

import io.quarkiverse.filevault.util.KeyStoreUtil;
import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import io.smallrye.config.SmallRyeConfig;
import io.smallrye.config.SmallRyeConfigBuilder;

public class FileVaultConfigSourceFactory implements ConfigSourceFactory {

  public static final String KEYSTORE_PATH = "quarkus.file.vault-config-source.keystore-path";
  public static final String KEYSTORE_SECRET = "quarkus.file.vault-config-source.keystore-secret";
  public static final String KEYSTORE_KEY = "quarkus.file.vault-config-source.encryption-key";

  @Override
  public Iterable<ConfigSource> getConfigSources(ConfigSourceContext context) {
    SmallRyeConfig config = new SmallRyeConfigBuilder()
        .withSources(new ConfigSourceContext.ConfigSourceContextConfigSource(context))
        .withMappingIgnore("quarkus.**").build();

    String keyStorePath =
        config.isPropertyPresent(KEYSTORE_PATH) ? config.getValue(KEYSTORE_PATH, String.class)
            : null;
    String keyStoreSecret =
        config.isPropertyPresent(KEYSTORE_SECRET) ? config.getValue(KEYSTORE_SECRET, String.class)
            : null;
    String keyStoreKey =
        config.isPropertyPresent(KEYSTORE_KEY) ? config.getValue(KEYSTORE_KEY, String.class) : null;
    if (keyStorePath != null) {
      Map<String, KeyStoreUtil.KeyStoreEntry> stringKeyStoreEntryMap =
          KeyStoreUtil.readKeyStore(keyStorePath, keyStoreSecret, keyStoreKey);
      return List.of(new FileVaultConfigSource(stringKeyStoreEntryMap));
    } else {
      return new ArrayList<>();
    }
  }
}
