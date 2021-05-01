# Parchment

This is a fork of [Paper](https://github.com/PaperMC/Paper) for the [Project Eden](https://bnn.gg/) server.
It contains changes to the API to make development of our plugin [Nexus](https://github.com/Pugabyte/Nexus) easier.

## Building

Requirements:
- You need `git` installed, with a configured user name and email. 
   On Windows you need to run from git bash.
- You need `maven` installed.
- You need `jdk` 8+ installed to compile (and `jre` 8+ to run).
- Anything else that `paper` requires to build.

If all you want is a paperclip server jar, just run `./parchment jar`.

Otherwise, to setup the `Parchment-API` and `Parchment-Server` repo, just run the following command
in your project root `./parchment patch` additionally, after you run `./parchment patch` you can run `./parchment build` to build the 
respective api and server jars.

`./parchment patch` should initialize the repo such that you can now start modifying and creating
patches. The folder `Parchment-API` is the api repo and the `Parchment-Server` folder
is the server repo and will contain the source files you will modify.

### Creating a patch

Patches are effectively just commits in either `Parchment-API` or `Parchment-Server`.
To create one, just add a commit to either repo and run `./parchment rb`, and a
patch will be placed in the patches folder. Modifying commits will also modify its
corresponding patch file.

## License

See https://github.com/starlis/empirecraft, https://github.com/electronicboy/byof, and https://github.com/Spottedleaf/Tuinity
for the license of material used/modified by this project.

### Note

The fork is based off of [Tuinity](https://github.com/Spottedleaf/Tuinity).