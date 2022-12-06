# Parchment

This is a fork of [Paper](https://github.com/PaperMC/Paper) for the [Project Eden](https://projecteden.gg/) Minecraft server.
It contains changes to the API to make development of our plugin [Nexus](https://github.com/Pugabyte/Nexus) easier.

## Building

Requirements:
- You need `git` installed, with a configured username and email. 
   On Windows you need to run from git bash.
- You need `jdk` 17+ installed to compile (and `jre` 17+ to run).

If all you want is a paperclip server jar, just run `./gradlew createReobfPaperclipJar`.

Otherwise, to setup the `parchment-api` and `parchment-server` repos, just run
`./gradlew applyPatches` in your project root. Afterwards you can run `./gradlew build`
to build the respective API and server jars.

`./gradlew applyPatches` should initialize the repo such that you can now start modifying and
creating patches. The folder `parchment-api` is the API repository and the `parchment-server`
folder is the server repository. These contain the source files you will modify.

### Creating a patch

Patches are effectively just commits in either `parchment-api` or `parchment-server`.
To create one, just add a commit to either repo and run `./gradlew rebuildPatches` and a
patch will be placed in the patches folder. Modifying commits will also modify its
corresponding patch file.

## License

The fork is based off of [paperweight-examples](https://github.com/PaperMC/paperweight-examples).
See its project page for the license of material used/modified by this project.
