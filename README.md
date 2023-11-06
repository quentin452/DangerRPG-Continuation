# Notes for Developers

You must add the following line in the VM arguments in your IDE

    -Dfml.coreMods.load=mixac1.dangerrpg.hook.RPGHookLoader

if your builded jar crash your game , you need to overwrite your MANIFEST.MF from META-INF folder from the jar
with these lines or some features from the mod will not work but ATTENTION this can corrupt your jar

    Manifest-Version: 1.0
    FMLAT: dangerrpg_at.cfg
    FMLCorePlugin: mixac1.dangerrpg.hook.RPGHookLoader
    FMLCorePluginContainsFMLMod: true
    
# Description

Just a continuation of DangerRPG for minecraft 1.7.10

The original mod : [HERE](https://legacy.curseforge.com/minecraft/mc-mods/dangerrpg)

# What this mod have for now?

Go here : [Wiki](https://github.com/quentin452/DangerRPG-Continuation/wiki)

# Links to descriptions of my projects.

[*Modrinth*](https://modrinth.com/mod/dangerrpg-continuation)

[*Github*](https://github.com/quentin452/DangerRPG-Continuation)

[*Curseforge*](https://legacy.curseforge.com/minecraft/mc-mods/dangerrpg-continuation)

# Requirement

no requirements

# Incompat

This mod can cause problems with [Golem Tweaks](https://www.curseforge.com/minecraft/mc-mods/golem-tweaks) , see [#40](https://github.com/quentin452/DangerRPG-Continuation/issues/40) 

# Discord

Add me on discord : imacatfr

Discord server : https://discord.gg/ZnmHKJzKkZ

