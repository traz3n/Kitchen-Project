package kitchgui;

import kitchgui.ui.GUIDesign;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public final class Main {
    // ===== New Palette =====
    public static final Color OXFORD    = new Color(0x011936); // app background (very dark blue)
    public static final Color CHARCOAL  = new Color(0x465362); // cards & table background
    public static final Color CAMBRIDGE = new Color(0x82A3A1); // header strips
    public static final Color OLIVINE   = new Color(0x9FC490); // primary accent
    public static final Color TEAGREEN  = new Color(0xC0DFA1); // secondary accent

    // Text on dark backgrounds
    public static final Color TEXT_PRIMARY = new Color(0xF7FFF9);
    public static final Color TEXT_MUTED   = new Color(0xD8E7E2);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Nimbus L&F
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ignore) {}

            // Dark Nimbus tweaks with your palette
            UIManager.put("control",                OXFORD);
            UIManager.put("nimbusLightBackground",  CHARCOAL);
            UIManager.put("info",                   CHARCOAL);
            UIManager.put("text",                   TEXT_PRIMARY);
            UIManager.put("nimbusBase",             CAMBRIDGE.darker());
            UIManager.put("nimbusBlueGrey",         CAMBRIDGE);
            UIManager.put("nimbusFocus",            TEAGREEN);

            UIManager.put("Table.background",       CHARCOAL);
            UIManager.put("Table.foreground",       TEXT_PRIMARY);
            UIManager.put("Table.gridColor",        new Color(0x3A4653));
            UIManager.put("Table.showHorizontalLines", Boolean.FALSE);
            UIManager.put("Table.showVerticalLines",   Boolean.FALSE);

            new GUIDesign(Path.of("data")).setVisible(true);
        });
    }
}
