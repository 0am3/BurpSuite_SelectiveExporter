package com.selectiveexporter;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import com.selectiveexporter.ui.MainTab;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        // Register Context Menu
        api.userInterface().registerContextMenuItemsProvider(new ContextMenuItemsProvider() {
            @Override
            public List<Component> provideMenuItems(ContextMenuEvent event) {
                List<Component> items = new ArrayList<>();
                JMenuItem exportItem = new JMenuItem("Send to Selective Exporter");
                
                exportItem.addActionListener(e -> {
                    mainTab.setSelectedItems(event.selectedRequestResponses());
                });
                
                items.add(exportItem);
                return items;
            }
        });

        api.logging().logToOutput("Selective Exporter initialized successfully.");
        api.logging().logToOutput("Ready to export selective context for AI.");
    }
}
