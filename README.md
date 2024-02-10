# Minecraft Alpha-v1.2.6

This is based off of real Minecraft Alpha v1.2.6, the decompiled source code has been ported to TeaVM with as minimal changes as possible. Multiplayer has been removed because I am too lazy to rewrite it, do NOT ask for multiplayer to be added. Worlds and settings are saved to your browsers local storage using IndexedDB, the reason why the world size's are so large is because I had to leave them uncompressed, whenever I tried to compress them using `CompressedStreamTools`, TeaVM would get stuck on an infinite loop. A newly generated world may take up around 20 MBs.

![Screenshot (23)](https://github.com/PeytonPlayz595/Alpha-v1.2.6/assets/106421860/84c133e9-935e-4edf-8ced-66b752bc5800)

Play here: https://peytonplayz595.github.io/Alpha-v1.2.6/js/
