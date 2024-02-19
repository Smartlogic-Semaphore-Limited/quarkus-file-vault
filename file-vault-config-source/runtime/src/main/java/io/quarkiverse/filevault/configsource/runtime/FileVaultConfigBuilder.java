package io.quarkiverse.filevault.configsource.runtime;

import io.quarkus.runtime.configuration.ConfigBuilder;
import io.smallrye.config.SmallRyeConfigBuilder;

public class FileVaultConfigBuilder implements ConfigBuilder {

    public static FileVaultConfigSourceRecorder recorder;
    public static FileVaultBootstrapConfig fileVaultBootstrapConfig;

    @Override
    public SmallRyeConfigBuilder configBuilder(SmallRyeConfigBuilder builder) {
        return builder.withSources(new FileVaultConfigSourceFactory());
    }
}
