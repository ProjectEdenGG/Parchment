Things I've found while just trying to get this to work:

- If you've made an update to either of the build.gradle.kts files in api or server, run `./gradlew rebuildPaperSingleFilePatches` after adding to git (no commit)
- To create a file patch:
  - Make the changes you need and have no other staged changes
  - cd **paper-api** or **paper-server** or **parchment-server/src/minecraft/java**
  - git add .
  - git commit --amend
    - This ammends it to the specific 'File Patch Commit' 
  - Then run one of the following
    - ./gradlew rebuildPaperApiFilePatches
    - ./gradlew rebuildPaperServerFilePatches
    - ./gradlew rebuildMinecraftFilePatches