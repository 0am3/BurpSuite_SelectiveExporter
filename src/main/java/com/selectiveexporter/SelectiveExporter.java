package com.selectiveexporter;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.selectiveexporter.ui.MainTab;

public class SelectiveExporter implements BurpExtension {
    private MontoyaApi api;

    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;

        // Set extension name
        api.extension().setName("Selective Exporter");

        // Create and register the UI tab
        MainTab mainTab = new MainTab(api);
        api.userInterface().registerSuiteTab("Selective Exporter", mainTab);

        api.logging().logToOutput("Selective Exporter initialized successfully.");
        api.logging().logToOutput("Ready to export selective context for AI.");
    }
}
