# 🧑‍🍳 Kitch — Smart Kitchen Inventory & Recipe Manager

**Kitch** is a modern Java Swing desktop app for managing your **kitchen inventory**, **shopping list**, and **recipes** in one place.
It automatically saves all changes, keeps your ingredient data consistent across windows, and provides a clean, minimal dark-mode interface.

---

## 🌟 Overview

This project was originally built in **Eclipse WindowBuilder**, then refactored and modernized in **VS Code** for better maintainability.  
It follows a simple **MVC (Model-View-Controller)** architecture to separate logic, data, and UI components.

---

## 🧩 Key Features

### 🗃️ Inventory & Shopping
- Manage pantry items and shopping lists in separate tables
- Auto-save on every change (no “Save” button needed)
- “Move 0-Qty → Shopping” action to handle out-of-stock items
- Fast live search and CSV-based data storage under `/data`

### 🍲 Recipe Builder
- Two-pane interface for **Ingredients** and **Steps**
- Supports multiple recipes (switchable with one click)
- Auto-linked to inventory and saved as CSV / TXT files
- Keyboard shortcuts for quick switching and navigation

### 🎨 Modern UI Design
- Built with **Java Swing** and **WindowBuilder (Eclipse)**
- Dark-mode color palette:
  `#011936` Oxford Blue `#465362` Charcoal `#82A3A1` Cambridge Blue `#9FC490` Olivine `#C0DFA1` Tea Green
- Rounded, responsive buttons and proportional tables
- Subtle hover effects and auto-resizing layouts for full-screen use

---

## ⚙️ Tech Stack

| Category | Tools / Technologies |
|-----------|----------------------|
| Language | Java SE 17 |
| GUI Framework | Swing + Eclipse WindowBuilder |
| IDEs | Eclipse & VS Code |
| Architecture | MVC (Model / View / Controller) |
| Data Storage | CSV / TXT (local) |
| Theme | Custom dark-mode UI |

---

## 🧠 Design Principles

- **Layered Architecture:** Models store data, Controllers handle logic, and the UI renders views.
- **Auto-Save Everywhere:** Every change persists immediately for a seamless UX.
- **Data Transparency:** All CSVs and recipe files are readable and editable by hand.
- **Scalable Foundation:** Future-ready for migration to JavaFX or SQLite backend.

---

## 🚀 Future Enhancements

- 🔔 **Expiry reminders & notifications**
- 📱 **Web or mobile version**
- 🧾 **Export grocery lists**
- 🧠 **Smart suggestions**

---

## 👨‍💻 Developer

**Miguel Joaquin Beltran**  
📍 UC Davis — Computer Science Major  
💼 [LinkedIn](https://www.linkedin.com/in/miguel-beltran-184741294/) | [GitHub](https://github.com/traz3n)

---

## 🪶 License

This project is released under the [MIT License](LICENSE).

---
