# Notes for Developers

You must add the following line in the VM arguments in your IDE

    -Dfml.coreMods.load=mixac1.dangerrpg.hook.RPGHookLoader

When your jar is builded you need to overwrite your MANIFEST.MF from META-INF folder from the jar
with these lines or some features from the mod will not work

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

[*Modrinth*]()(NOT YET , need to fix some bugs etc... before uploading on it)

[*Github*](https://github.com/quentin452/DangerRPG-Continuation)

[*Curseforge*]()(NOT YET , need to fix some bugs etc... before uploading on it)

# Requirement

no requirements

# Discord

Add me on discord : imacatfr

Discord server : https://discord.gg/ZnmHKJzKkZ
