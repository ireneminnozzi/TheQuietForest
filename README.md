#  The Quiet Forest

> *Un'avventura RPG in cui il giocatore esplora un boosco e deve sconfiggere i mostri*


---

##  Descrizione

**The Quiet Forest** è un gioco RPG sviluppato in Java con JavaFX, in cui la protagonista — una giovane strega — deve esplorare un bosco misterioso e sconfiggere quattro mostri che ne minacciano l'equilibrio. Il gioco combina esplorazione, combattimenti a turni, gestione delle risorse (salute, mana, pozioni) e un sistema di salvataggio automatico.

Il progetto è stato sviluppato come elaborato finale per il corso di **Metodi di Programmazione per i Giochi** presso l'Università La Sapienza di Roma, con l'obiettivo di dimostrare la padronanza di:
- Programmazione orientata agli oggetti
- Pattern di design (Factory, Singleton, DTO, Navigator)
- Architettura MVC (Model-View-Controller)
- Principi SOLID e Clean Code
- Persistenza dei dati con JSON

---

##  Come Giocare

### Obiettivo
Sconfiggi tutti e 4 i mostri del bosco per riportare la pace:

| # | Mostro | Location |
|---|--------|----------|
| 1 |  Goblin | Cascate |
| 2 |  Troll | Palude |
| 3 |  Spirito | Cimitero |
| 4 |  Drago | Bosco |

### Controlli

**Menu Iniziale:**
- **Nuova Partita** → Inizia una nuova avventura
- **Carica Partita** → Riprendi un salvataggio esistente
- **Esci** → Chiudi il gioco

**Mappa del Bosco:**
- Clicca su una location per esplorarla
- La **Capanna** è una zona sicura dove riposare (recupera salute e mana)
- I mostri sconfitti vengono segnati con una ✓ sulla mappa

**Combattimento:**
-  **Attacco Base** → Colpo fisico (nessun costo)
-  **Incantesimo** → Danno magico (costa mana)
-  **Pozione** → Cura o bonus (consuma l'oggetto)
-  **Fuggi** → 50% di probabilità di successo

### Salvataggio
Il gioco si **salva automaticamente** dopo ogni combattimento vinto e dopo il riposo nella capanna. I progressi vengono memorizzati in un file JSON (`savegame.json`) nella root del progetto.

---

### Prerequisiti
- Java 25 (LTS)
- Gradle

### Istruzioni

```bash
git clone https://github.com/ireneminnozzi/PROGETTO-MDP.git
cd PROGETTO-MDP
```

### Build del progetto
```bash
./gradlew build        # Linux/Mac
.\gradlew build        # Windows PowerShell
```

### Esecuzione
```bash
./gradlew run          # Linux/Mac
.\gradlew run          # Windows PowerShell

```
