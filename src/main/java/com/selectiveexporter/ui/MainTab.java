package com.selectiveexporter.ui;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.HttpRequestResponse;
import com.selectiveexporter.logic.Exporter;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

public class MainTab extends JPanel {
    private final MontoyaApi api;
    private final Exporter exporter;
    private final JTextArea resultArea;
    private final JCheckBox reqHeadersCheck;
    private final JCheckBox reqBodyCheck;
    private final JCheckBox resHeadersCheck;
    private final JCheckBox resBodyCheck;
    private List<HttpRequestResponse> selectedItems = new ArrayList<>();

    public MainTab(MontoyaApi api) {
        this.api = api;
        this.exporter = new Exporter(api);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel titleLabel = new JLabel("Selective Exporter");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        headerPanel.add(titleLabel);
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
        reqHeadersCheck = new JCheckBox("Request Headers", true);
        reqBodyCheck = new JCheckBox("Request Body", true);
        resHeadersCheck = new JCheckBox("Response Headers", false);
        resBodyCheck = new JCheckBox("Response Body", true);

        checks.add(reqHeadersCheck);
        checks.add(reqBodyCheck);
        checks.add(resHeadersCheck);
        checks.add(resBodyCheck);
        configPanel.add(checks, gbc);

        // Add listeners to refresh preview when checkboxes are toggled
        reqHeadersCheck.addActionListener(e -> generateExport());
        reqBodyCheck.addActionListener(e -> generateExport());
        resHeadersCheck.addActionListener(e -> generateExport());
        resBodyCheck.addActionListener(e -> generateExport());

        // Result Preview
        resultArea = new JTextArea(15, 60);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(configPanel), new JScrollPane(resultArea));
        splitPane.setDividerLocation(150);
        add(splitPane, BorderLayout.CENTER);

        // Footer / Actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton copyButton = new JButton("Copy to Clipboard");
        JButton saveButton = new JButton("Export to File");
        JButton exportButton = new JButton("Generate Export");
        
        actionPanel.add(copyButton);
        actionPanel.add(saveButton);
        actionPanel.add(exportButton);
        add(actionPanel, BorderLayout.SOUTH);

        exportButton.addActionListener(e -> generateExport());
        copyButton.addActionListener(e -> {
            StringSelection selection = new StringSelection(resultArea.getText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
            api.logging().logToOutput("Export copied to clipboard.");
        });

        saveButton.addActionListener(e -> saveToFile());
    }

    public void setSelectedItems(List<HttpRequestResponse> items) {
        this.selectedItems = items;
        api.logging().logToOutput("Captured " + items.size() + " items for export.");
        generateExport(); // Auto-generate preview
    }

    private void generateExport() {
        if (selectedItems.isEmpty()) {
            resultArea.setText("No items selected. Right-click requests in Burp and select 'Send to Selective Exporter'.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (HttpRequestResponse item : selectedItems) {
            sb.append(exporter.exportToMarkdown(
                    item.request(), 
                    item.response(),
                    reqHeadersCheck.isSelected(),
                    reqBodyCheck.isSelected(),
                    resHeadersCheck.isSelected(),
                    resBodyCheck.isSelected()
            ));
            sb.append("\n---\n\n");
        }
        resultArea.setText(sb.toString());
        resultArea.setCaretPosition(0);
    }

    private void saveToFile() {
        if (resultArea.getText().isEmpty()) return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Export");
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                writer.write(resultArea.getText());
                api.logging().logToOutput("Export saved to " + file.getAbsolutePath());
                JOptionPane.showMessageDialog(this, "Export saved successfully!");
            } catch (Exception ex) {
                api.logging().logError("Error saving file: " + ex.getMessage());
                JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
