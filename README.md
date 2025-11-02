# 🧑‍🍳 Kitch | Kitchen Inventory & Recipe Manager

**Kitch** is a personal Java project I built to organize my kitchen inventory, shopping list, and recipes in one place.  
It automatically saves everything as you make changes and keeps your data connected between windows.

---

## 🌟 Overview

I originally built this app using **Eclipse WindowBuilder** to design the interface, then improved and modernized it in **VS Code** with custom UI and auto-save features.  
It’s written in **Java 17 (Swing)** and stores all data in simple **CSV and text files**, so you can easily view or edit them outside the app.

---

## 🧩 What It Can Do

### 🗃️ Inventory & Shopping
- Add, edit, and delete items with automatic saving  
- Separate tables for your pantry and shopping list  
- “Move to Shopping” automatically detects items with 0 quantity  
- Search instantly as you type  
- Data stored in `/data/inventory.csv` and `/data/shopping.csv`

### 🍲 Recipe Builder
- Build and save your own recipes  
- Two-pane view: Ingredients and Steps  
- Switch between multiple recipes easily  
- Connect ingredients directly with your inventory list  
- Auto-saves in `/data/ingredients` and `/data/recipes`

### 🎨 Interface & Design
- Built with **Java Swing** (customized with WindowBuilder)  
- Clean **dark mode** color palette:  
  `#011936` Oxford Blue · `#465362` Charcoal · `#82A3A1` Cambridge Blue · `#9FC490` Olivine · `#C0DFA1` Tea Green  
- Large rounded buttons with hover effects  
- Responsive layout that scales with your window  
- No “Save” button, everything updates live

---

## ⚙️ Tech Details

| Category | Tools |
|-----------|-------|
| Language | Java SE 17 |
| GUI | Swing + WindowBuilder (Eclipse) |
| Editor | VS Code & Eclipse |
| Storage | CSV / TXT (auto-save) |
| Theme | Custom dark mode |

---

## 🧠 How It Works

Each part of the app handles one job:
- The **logic** (like saving or checking quantities) runs behind the scenes.  
- The **interface** (built with Swing) displays everything clearly.  
- Data is **auto-saved** to CSV and TXT files after every change.  
- Everything updates live, no manual file management needed.

---

## 🚀 Future Ideas
- 🔔 Expiry reminders and notifications  
- 📱 A web or mobile version
- 🧾 Export grocery lists 
- 🧠 Smart recipe suggestions

---

## 👨‍💻 Developer

**Miguel Joaquin Beltran**  
📍 UC Davis - Computer Science Major (Class of 2027)  
💼 [LinkedIn](https://www.linkedin.com/in/miguel-beltran-184741294/) | [GitHub](https://github.com/traz3n)

---

## 🪶 License

This project is released under the [MIT License](./LICENSE).

---
