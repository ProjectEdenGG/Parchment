# Parchment

This is a fork of [Paper](https://github.com/PaperMC/Paper) for the [Project Eden](https://projecteden.gg/) Minecraft server.
It contains changes to the API to make development of our plugin [Nexus](https://github.com/Pugabyte/Nexus) easier.

## Building

Requirements:
- You need `git` installed, with a configured username and email. 
   On Windows you need to run from git bash.
- You need `jdk` 16+ installed to compile (and `jre` 16+ to run).

If all you want is a paperclip server jar, just run `./gradlew reobfJar`.

Otherwise, to setup the `Parchment-API` and `Parchment-Server` repo, just run
`./gradlew applyPatches` in your project root. Afterwards you can run `./gradlew build`
to build the respective API and server jars.


`./gradlew applyPatches` should initialize the repo such that you can now start modifying and
creating patches. The folder `Parchment-API` is the API repository and the `Parchment-Server`
folder is the server repository. These contain the source files you will modify.

### Creating a patch

Patches are effectively just commits in either `Parchment-API` or `Parchment-Server`.
To create one, just add a commit to either repo and run `./gradlew rebuildPatches` and a
patch will be placed in the patches folder. Modifying commits will also modify its
corresponding patch file.

## License

See https://github.com/PaperMC/paperweight-examples for the license of material used/modified by
this project.

### Note

The fork is based off of [paperweight-example](https://github.com/PaperMC/paperweight-examples).