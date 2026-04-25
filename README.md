<p align="center">
  <a href="https://modrinth.com/plugin/pet-horse">
    <img src="https://img.shields.io/modrinth/dt/pet-horse?color=green&style=for-the-badge&logo=modrinth" alt="Modrinth Downloads"/>
  </a><br>
</p>

<img width="3601" height="2236" alt="Header" src="https://github.com/user-attachments/assets/73ebc362-ef32-4a07-9b11-2e4bcb8743cb" />

<p align="center">
  <img width="800" height="400" alt="Horse Level" src="https://github.com/user-attachments/assets/1ac20d64-dab8-4981-974c-736f87e18be9">
</p>

<img width="3409" height="1517" alt="Horse die" src="https://github.com/user-attachments/assets/a7b5ee22-1afb-4bf3-ad88-f6a203f8cecc" />


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

# Autosave options
autosave:
  interval_minutes: 10 # Interval in minutes for autosaving horse data. Must be between 1 and 10080
  log: true # Log autosave events, disable to reduce server console spam

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

<img width="4017" height="1760" alt="Horse customize" src="https://github.com/user-attachments/assets/adce8ef6-f0ac-437e-8ff8-201f94893576" />

<p align="center">
  <img src="https://github.com/user-attachments/assets/db600f08-4781-49eb-9b3c-15a3f0ecca1e" width="800" alt="Horse Backpack" />
</p>

<img width="4737" height="2048" alt="Horse inventory" src="https://github.com/user-attachments/assets/10352610-e791-4643-bcc7-eeed6570a56e" />
