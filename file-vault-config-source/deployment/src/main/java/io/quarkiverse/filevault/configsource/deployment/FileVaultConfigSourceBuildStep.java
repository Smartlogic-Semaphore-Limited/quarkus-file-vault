package io.quarkiverse.filevault.configsource.deployment;

import io.quarkiverse.filevault.configsource.runtime.FileVaultBootstrapConfig;
import io.quarkiverse.filevault.configsource.runtime.FileVaultConfigBuilder;
import io.quarkiverse.filevault.configsource.runtime.FileVaultConfigSourceRecorder;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.ExecutionTime;
import io.quarkus.deployment.annotations.Record;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.RunTimeConfigBuilderBuildItem;

public class FileVaultConfigSourceBuildStep {

    public FeatureBuildItem featureBuildItem() {
        return new FeatureBuildItem("file-vault-config-source");
    }

    @Record(ExecutionTime.RUNTIME_INIT)
    @BuildStep
    RunTimeConfigBuilderBuildItem init(FileVaultConfigSourceRecorder recorder,
            FileVaultBootstrapConfig fileVaultBootstrapConfig) {
        return new RunTimeConfigBuilderBuildItem(FileVaultConfigBuilder.class.getName());
    }
}
