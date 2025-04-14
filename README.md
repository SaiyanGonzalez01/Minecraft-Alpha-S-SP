## ![promo](https://github.com/user-attachments/assets/9d728288-e69d-47ae-bab4-a8940d5f37ab)

This is based off of real Minecraft Alpha v1.2.6, the decompiled source code has been ported to TeaVM with the goal of being as close to the real Alpha v1.2.6 as possible. Worlds and settings are saved to your browsers local storage using IndexedDB, worlds are then compressed using jzlib's `GZIP`, existing uncompressed worlds will be loaded uncompressed and will then be compressed when the game writes to the file.

# Making The client

### Gradle
After modifying files in `src/main/java` you can compile your changes to the javascript client by running `gradlew generatejavascript`, for linux run `./gradlew generatejavascript`. This will then transpile the Java code into javascript files in `web/js/`.

### Textures
The textures are compiled into `resources.mc` using [laxdude](https://github.com/lax1dude)'s EPK Compiler. The resources are located in `resources/`, after modifying the resources you can compile them using `CompileEPK`, and for Linux use `./CompileEPK.sh`.

### Offline Download
As of right now there is no system to compile an offline download, so you will have to manually copy and paste the javascript from `web/js/app.js` into the HTML file, for the `resources.mc` just encode the file using [Base64](https://www.base64encode.org/) and paste it into the assets div.

# Multiplayer
Steps to setup a multiplayer server:

1. Make sure have to have Java 8 (or higher) installed.
2. Download the [Alpha.jar](https://github.com/PeytonPlayz595/Alpha-v1.2.6/blob/main/minecraft_server/Alpha.jar)
3. Run the server by using `java -jar Alpha.jar`
4. Make sure to have the latest version of NodeJS installed
5. Download or clone my [ws-tcp-bridge repo](https://github.com/PeytonPlayz595/ws-tcp-bridge/) and extract it into a folder
6. CD into the folder of the extracted repo
7. Proxy the Minecraft server to WebSockets by running `node ws-tcp-bridge --websocket <WebSocket_Port> --minecraft localhost:<Minecraft_Port>`

Note: Replace "<WebSocket_Port>" with the port you want clients to use when connecting to the server, replace "<Minecraft_Port>" with the port you are hosting the Minecraft Server on.

WebSockify will probably work better but I'm not gonna write a guide for you, if you want to use it you're gonna have to figure it out yourself.

# Texture Packs
This is pretty much self explanitory, just make sure that the texture pack has the same file structure as in `resources/`, and then add the files to a ZIP archive, if a texture pack does not work then most likely it is not for this version of Minecraft. You're probably gonna have to make your own texture pack because texture packs for Alpha are very rare these days.

# Hosting a client
If you make your own client or just want to host one yourself just upload the contents of the `web/` folder to a web server, I use [Simple Web Server](https://simplewebserver.org/) but feel free to use any web server. You can also use NGROK if you want, just set the file location to the web folder.

Note: For multiplayer to work over https (wss), you will need a valid SSL certificate.

# How to decompile older Minecraft versions
To decompile older Minecraft versions you can use RetroMCP!

RetroMCP is a rewrite of MCP that adds support for many different older versions of Minecraft that were never supported by MCP. To get started head over to the [RetroMCP GitHub](https://github.com/MCPHackers/RetroMCP-Java/releases) and download the latest release. Make sure you have Java 8 (or higher) installed in order to run and decompile Minecraft versions.

# Code used within this project

- Modified version of lax1dude's OpenGL Emulator
- Eaglercraft 1.5.2 service pack
- Eaglercraft beta 1.3 service pack
- Decompiled Minecraft Alpha v1.2.6 source code
