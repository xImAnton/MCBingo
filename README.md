# MCBingo
🎰 Play a Minecraft version of Bingo 🎱

Minecraft Version: **1.16-1.18.1**

## What is Minecraft Bingo?
Minecraft Bingo is a minigame in which each team has to find certain items. The first to find all random generated items wins.

## Installation
To run this plugin you got to have a spigot or paper minecraft server.
Build this or download a prebuilt jar and insert it into your plugins folder.
Restart your server or type /rl confirm

## Usage
On plugin load a bingo game is created and no one can move or break blocks except players in creative mode.
There are 4 Teams by default. Players can join teams using /team <team-id>. The tab completion doesn't suggest full teams.
You can adjust settings using /settings. This command can only be used by OP players.
When you want to start the game and everyone joined a team, you can click in the bottom right corner on "Start"
To see the items the players have to find, use /bingo.

## Setting-Specific Commands
### /backpack, /bp
This command opens, when enabled in settings a shared inventory for every team.

### /preset
**The preset system is getting a overhaul in the future.**  
Currently available presets:
- easy (Relatively easy to get items)
- nether (Nether items, a bit more difficult)
  Subcommands
#### /preset reset
Removes all items to choose from. To start a game, at least 9 items have to be set.
#### /preset add <preset-name>
Adds the items of a preset to the item list.
#### /preset set <preset-name>
Explicitly sets the item list to a preset.
#### /preset info
Shows all selected presets.

### /teams
Views all teams with their players.

### [Deprecated] /random
Prints the username of a random player for team selection. Use the random-team setting instead.

### /teamtp
Teleports you to the next player in your team. Has a cooldown of 2 Minutes. Only works when enabled in settings.

### /top
Teleports the executing player to the surface. Has to be enabled in settings.

## Config file
In the config file you can specify the default options for settings.
Under presets you will in the future be able to specify own presets and choose them.
