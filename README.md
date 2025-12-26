---

# Moderation Plugin ------> scarica il plugin da spigot: https://www.spigotmc.org/resources/moderation-plugin.131138/ 

**Moderation Plugin** è una soluzione leggera ed efficace per la gestione della moderazione sui server Minecraft (versione 1.21). Permette agli amministratori di gestire sanzioni come Ban, Mute e Blacklist in modo rapido e persistente.

---

## 🚀 Funzionalità

* **Ban & Unban**: Allontana i giocatori dal server con un motivo personalizzato.
* **Mute System**: Silenzia i giocatori nella chat (i dati vengono salvati in modo persistente).
* **Blacklist**: Blocca l'accesso a un giocatore tramite IP o Nome Utente per una protezione massima.
* **Messaggi Personalizzabili**: Supporto completo per i colori (`&`) e configurazione flessibile tramite `config.yml`.
* **Persistenza**: I mute e le configurazioni vengono salvati automaticamente allo spegnimento del server.

---

## 🛠 Comandi e Permessi

| Comando | Descrizione | Utilizzo |
| --- | --- | --- |
| `/ban` | Banna un giocatore dal server | `/ban <player> [motivo]` |
| `/unban` | Rimuove il ban a un giocatore | `/unban <player>` |
| `/mute` | Impedisce a un giocatore di scrivere in chat | `/mute <player>` |
| `/unmute` | Permette a un giocatore di tornare a scrivere | `/unmute <player>` |
| `/blacklist` | Banna per IP e Nome un giocatore | `/blacklist <player> [motivo]` |
| `/unblacklist` | Rimuove un giocatore dalla blacklist | `/unblacklist <player>` |

> **Nota**: Assicurati di configurare i permessi nel tuo plugin manager (es. LuckPerms) per limitare l'accesso a questi comandi.

---

## ⚙️ Configurazione

Il plugin genera automaticamente un file `config.yml` nella cartella `/plugins/ModerationPlugin/`. Esempio di struttura messaggi:

```yaml
messages:
  ban-usage: "&cUtilizzo: /ban <player> [motivo]"
  ban-success: "&aHai bannato %player% per: %reason%"
  ban-kick-message: "&cSei stato bannato!\nMotivo: %reason%"
  muted-message: "&cSei mutato e non puoi scrivere in chat!"
  # ... altri messaggi

```

---

## 🔨 Installazione

1. Scarica il file `.jar` del plugin.
2. Trascinalo nella cartella `plugins` del tuo server Minecraft 1.21.
3. Riavvia il server o carica il plugin.
4. Modifica il file `config.yml` secondo le tue preferenze e usa `/reload` (o riavvia).

---

## 📄 Licenza

Questo progetto è distribuito sotto licenza **Apache License 2.0**. Consulta il file `LICENSE` per ulteriori dettagli.

```
Copyright 2025 KernelPanic

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

```

---

## 👨‍💻 Sviluppo

Il plugin è scritto in **Java** utilizzando le API di **Spigot/Paper**.

* **Package**: `me.emmanuele.moderation`
* **Versione API**: 1.21

---

**Sviluppato con ❤️ da KernelPanic**

---
