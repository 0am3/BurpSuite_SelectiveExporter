# 🛡️ BurpSuite Selective Exporter

**Optimize Web Security Context for the AI Era.**

[![Burp Suite](https://img.shields.io/badge/Burp%20Suite-Extension-orange)](https://portswigger.net/burp)
[![Java](https://img.shields.io/badge/Language-Java%2017-blue)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

Selective Exporter is a modern Burp Suite extension designed to bridge the gap between heavy web application traffic and AI-driven security analysis. It allows security researchers and pentesters to selectively extract, filter, and format HTTP interactions for high-quality context feeding into LLMs (Large Language Models).

---

## 🚀 Why Selective Exporter?

Modern AI models are powerful but prone to **hallucinations** and **context window exhaustion** when fed with raw, noisy data. 

In a typical pentest, raw HTTP history is filled with redundant headers (`User-Agent`, `Cookies`), noise (`Tracking pixels`, `Analytics`), and repetitive payloads. Feeding this "raw bloat" to an AI leads to:
1. **Hallucinations**: The model gets lost in irrelevant details.
2. **Data Bloat**: Unnecessary token consumption and higher costs.
3. **Loss of Focus**: The AI misses the critical security logic hidden in the noise.

**Selective Exporter** gives you surgical control over what the AI sees.

---

## ✨ Key Features

- **🎯 Selective Extraction**: Export only what matters. Choose specific headers, body parameters, or JSON branches.
- **📄 AI-Friendly Formats**: 
    - **Markdown (Optimized)**: Structured tables and blocks that LLMs parse perfectly.
    - **Cleaned JSON**: Minified and stripped of noise for programmatic analysis.
    - **Prompt Templates**: Wrap your data directly into a "System Message" or "Context" block.
- **🖱️ Seamless Workflow**: 
    - Context menu integration: Right-click any request in History and send it to the exporter.
    - Dedicated review tab for batch processing.
- **⚡ High Performance**: Built on the modern **Montoya API** for maximum stability and speed.

---

## 🛠️ Installation

1. **Build the extension**:
   ```powershell
   ./gradlew shadowJar
   ```
2. **Load into Burp**:
   - Go to `Extensions` -> `Installed`.
   - Click `Add`.
   - Select `Java` and choose the JAR from `build/libs/SelectiveExporter.jar`.

---

## 📖 How to Use

1. **Select**: Browse your Proxy History or Repeater.
2. **Send**: Right-click a request -> `Send to Selective Exporter`.
3. **Configure**: Go to the `Selective Exporter` tab. Choose your headers, body fields, and target format.
4. **Export**: Copy the generated context and feed it directly to your AI (ChatGPT, Claude, etc.).

---

## 🗺️ Roadmap

- [x] Initial project structure & Montoya API setup.
- [x] Basic UI and Context Menu integration.
- [ ] Advanced JSON Path filtering.
- [ ] Header whitelisting/blacklisting profiles.
- [ ] Custom Markdown templates for different AI agents.

---

## 🤝 Contributing

Contributions are welcome! If you have ideas for better AI context templates or filtering logic, feel free to open an issue or PR.

---

*Developed with ❤️ for the security community.*
