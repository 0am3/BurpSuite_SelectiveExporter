package com.selectiveexporter.ui;

import burp.api.montoya.MontoyaApi;
import javax.swing.*;
import java.awt.*;

public class MainTab extends JPanel {
    private final MontoyaApi api;

    public MainTab(MontoyaApi api) {
        this.api = api;
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.add(new JLabel("<html><h2>Selective Exporter</h2></html>"));
        headerPanel.add(new JLabel(" - Optimize web traffic for LLM context"));
        add(headerPanel, BorderLayout.NORTH);

        // Main Content (Configuration)
        JPanel configPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        configPanel.add(new JLabel("Export Format:"), gbc);
        
        gbc.gridx = 1;
        String[] formats = {"Markdown (Optimized for AI)", "JSON (Cleaned)", "CSV (Structured)", "Raw Prompt"};
        JComboBox<String> formatCombo = new JComboBox<>(formats);
        configPanel.add(formatCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        configPanel.add(new JLabel("Include:"), gbc);

        gbc.gridx = 1;
        JPanel checks = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checks.add(new JCheckBox("Request Headers", true));
        checks.add(new JCheckBox("Request Body", true));
        checks.add(new JCheckBox("Response Headers", false));
        checks.add(new JCheckBox("Response Body", true));
        configPanel.add(checks, gbc);

        add(new JScrollPane(configPanel), BorderLayout.CENTER);

        // Footer / Actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton exportButton = new JButton("Export Selection");
        actionPanel.add(exportButton);
        add(actionPanel, BorderLayout.SOUTH);
    }
}
