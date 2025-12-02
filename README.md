<h3 align="center">
  PET HORSE
</h3>

<p align="center">
  <a href="https://modrinth.com/plugin/pet-horse">
    <img src="https://img.shields.io/modrinth/dt/pet-horse?color=green&style=for-the-badge&logo=modrinth" alt="Modrinth Downloads"/>
  </a><br>
  <a href="https://www.curseforge.com/minecraft/bukkit-plugins/pet-horses">
    <img src="https://cf.way2muchnoise.eu/full_1347084_downloads.svg" alt="CurseForge Downloads"/>
  </a><br>
  <a href="https://www.codefactor.io/repository/github/iliiasik/pet-horse/overview/main">
    <img src="https://www.codefactor.io/repository/github/iliiasik/pet-horse/badge/main" alt="CodeFactor"/>
  </a>
</p>


<img width="2372" height="1427" alt="Horse-header" src="https://github.com/user-attachments/assets/5f112ddc-52b0-4fde-b443-a2cdeb530ed0" />

<p align="center">
<img width="327" height="146" alt="Horse stats" src="https://github.com/user-attachments/assets/2396f8d9-9e2e-4d4a-9dab-227c3d04a21a" />
</p>

<p align="center">
  <img width="800" height="400" alt="Horse Level" src="https://github.com/user-attachments/assets/1ac20d64-dab8-4981-974c-736f87e18be9">
</p>

<p align="center">
  <img width="603" height="238" alt="Horse die" src="https://github.com/user-attachments/assets/d7ddb615-f72c-449c-b7e5-9a7d28296463">
</p>

```yaml
# Language for the plugin.
# Available: 'en', 'ru', 'es', 'fr', 'zh', 'pt',
# 'de', 'ja', 'ko', 'it', 'hi, 'ar', 'kg', 'pl',
# 'nl', 'sv', 'cs' 'th', 'fi', 'no'
language: 'en'

# Database configuration (MariaDB only) false - yaml storage
database:
  enabled: false
  host: "localhost"
  port: 3306
  name: "minecraft"
  user: "root"
  password: ""

# Leveling system configuration
leveling:
  base_xp: 100
  xp_increment: 50

# Horse stats configuration
stats:
  speed_base: 0.18
  speed_max_bonus: 0.225
  health_base: 15.0
  health_max_bonus: 15.0
  jump_base: 0.6
  jump_max_bonus: 0.6

allow_fall_damage: false
allow_other_players_damage_horses: false

# Horse backpack configuration
# IMPORTANT. Size must be between 9 and 54, and must be a multiple of 9
backpack:
  base_size: 9
  size_per_level: 9
  max_size: 54
  armor_slot_enabled: true
  drop_on_death: true # Drop backpack contents on horse death

# Cooldown in minutes before a dead horse can be respawned
respawn_cooldown_minutes: 15
```

<p align="center">
  <img src="https://github.com/user-attachments/assets/94a4f993-d64d-407c-9401-e5c1b49a0f9b" width="800" height="400" alt="Horse Customize">
</p>

<p align="center">
  <img width="750" height="272" alt="Horse customize" src="https://github.com/user-attachments/assets/107844f7-adf4-4987-a9bf-58d3c8cea8e1">
</p>


<p align="center">
  <img src="https://github.com/user-attachments/assets/db600f08-4781-49eb-9b3c-15a3f0ecca1e" width="800" alt="Horse Backpack" />
</p>

<p align="center">
  <img width="750" height="338" alt="Horse inventory" src="https://github.com/user-attachments/assets/d52ebc9a-4cb6-464e-8724-28694d0cab94">
</p>


---

- **Supports multiple languages:**
  - English (en)
  - Russian (ru)
  - Spanish (es)
  - French (fr)
  - Chinese (zh)
  - Portuguese (pt)
  - German (de)
  - Japanese (ja)
  - Korean (ko)
  - Italian (it)
  - Hindi (hi)
  - Arabic (ar)
  - Dutch (nl)
  - Swedish (sv)
  - Czech (cs)
  - Thai (th)
  - Finnish (fi)
  - Norwegian (no)
  - Kyrgyz (kg)

- **Storage options:**
  - YAML (small servers 0–70 players)
  - MariaDB (large servers 100+ players)
