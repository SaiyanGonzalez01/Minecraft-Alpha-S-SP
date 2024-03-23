# Minecraft Alpha-v1.2.6

This is based off of real Minecraft Alpha v1.2.6, the decompiled source code has been ported to TeaVM with the goal of being as close to the real Alpha v1.2.6 as possible. Worlds and settings are saved to your browsers local storage using IndexedDB, worlds are then compressed using jzlib's `GZIP`, existing uncompressed worlds will be loaded uncompressed and will then be compressed when the game writes to the file.

![h12mgp8oidpc1](https://github.com/PeytonPlayz595/Alpha-v1.2.6/assets/106421860/324a94c1-468f-4907-9127-64bdf12906c8)


### [Play the official release (No download required)](https://peytonplayz595.github.io/Alpha-v1.2.6/web/)

### [Download offline HTML file](https://github.com/PeytonPlayz595/Alpha-v1.2.6/blob/main/offline_download/Alpha_Offline_Download.html)

# Making a client

### Gradle
After modifying files in `src/main/java` you can compile your changes to the javascript client by running `gradlew generatejavascript`, for linux run `./gradlew generatejavascript`. This will then transpile the Java code into javascript files in `web/js/`.

### Textures
The textures are compiled into `resources.mc` using [Laxdude](https://github.com/lax1dude)'s EPK Compiler. The resources are located in `resources/`, after modifying the resources you can compile them using `CompileEPK`, and for Linux use `./CompileEPK.sh`.

### Offline Download
As of right now there is no system to compile an offline download, so you will have to manually copy and paste the javascript from `web/js/app.js` into the HTML file, for the `resources.mc` just encode the file using [Base64](https://www.base64encode.org/) and paste it into the assets div.

# Multiplayer
Multiplayer has been successfully rewritten and thoroughly tested. It seems to be pretty stable but it had to be removed due to a glitch in Singleplayer's chunk loading, the issue lies within Alpha's multiplayer code somewhere and I am unable to pinpoint the exact cause of the issue so until I am able to figure it out, multiplayer will not be avalible.

If you are really impatient and cannot wait then go through the commit history and find commit 62af5c9 titled "oops" and download the offline download from there, **THIS VERSION IS BUGGED AND CHUNK LOADING IN SINGLEPLAYER IS EXTREMELY BROKEN, SOME (IF NOT MOST) CHUNKS MAY NOT EVEN SAVE AT ALL!**

If you do decide to use this version (not recommended) just download the Alpha v1.2.6 server software from web archive and use websockify to proxy it to websockets.

# Texture Packs
This is pretty much self explanitory, just make sure that the texture pack has the same file structure as in `resources/`, and then add the files to a ZIP archive, if a texture pack does not work then most likely it is not for this version of Minecraft. You're probably gonna have to make your own texture pack because texture packs for Alpha are very rare these days.

# Hosting a client
If you make your own client or just want to host one yourself just upload the contents of the `web/` folder to a web server, I use [Simple Web Server](https://simplewebserver.org/) but feel free to use any web server. You can also use NGROK if you want, just set the file location to the web folder.

Note: For multiplayer to work over https (wss), you will need a valid SSL certificate.

# How to decompile older Minecraft versions
To decompile older Minecraft versions you can use RetroMCP!

RetroMCP is a rewrite of MCP that adds support for many different older versions of Minecraft that were never supported by MCP. To get started head over to the [RetroMCP GitHub](https://github.com/MCPHackers/RetroMCP-Java/releases) and download the latest release. Make sure you have Java 8 (or higher) installed in order to run and decompile Minecraft versions.

# Code used within this project

- Modified version of Lax1dude's OpenGL Emulator
- Eaglercraft 1.5.2 service pack
- Eaglercraft beta 1.3 service pack
- Decompiled Minecraft Alpha v1.2.6 source code
