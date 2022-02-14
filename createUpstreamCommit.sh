#!/usr/bin/env bash

# requires curl & jq

# createUpstreamCommit --paper HASH
# flag: --paper HASH - the commit hash to use for comparing commits between paper (PaperMC/Paper/compare/HASH...HEAD)

function getCommits() {
    echo "$(curl -H "Accept: application/vnd.github.v3+json" https://api.github.com/repos/"$1"/compare/"$2"...HEAD | jq -r '.commits[] | "'"$1"'@\(.sha[:7]) \(.commit.message | split("\r\n")[0] | split("\n")[0])"')"
}

(
set -e
PS1="$"

paperHash=""

TEMP=$(getopt --long paper: -o "" -- "$@")
eval set -- "$TEMP"
while true; do
    case "$1" in
        --paper)
            paperHash="$2"
            shift 2
            ;;
        *)
            break
            ;;
    esac
done

paper=""
updated=""
logsuffix=""

# Paper updates
if [ -n "$paperHash" ]; then
    paper=$(getCommits "PaperMC/Paper" "$paperHash")

    # Updates found
    if [ -n "$paper" ]; then
        updated="Paper"
        logsuffix="$logsuffix\n\nPaper Changes:\n$paper"
    fi
fi

disclaimer="Upstream has released updates that appear to apply and compile correctly"
log="Updated Upstream ($updated)\n\n${disclaimer}${logsuffix}"

echo -e "$log" | git commit -a -F -

) || exit 1