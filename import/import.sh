#!/bin/zsh
if [ -z "$1" ] || [ -z "$2" ] || [ -z "$3" ];
  then echo "usage: import.sh <username> <password> <url>";
  exit 0;
fi

if [ ! -e ../../ec-tutorials ];
  then echo "You must have the ec-tutorials repository located at ../../ec-tutorials"
  exit 0;
fi

export HOST_URL=$3

curl -d "username=$1" -d "password=$2" --cookie-jar ./cookies $HOST_URL/login
export TUTORIAL_ID=`curl -b cookies -d "@ec-tutorial-create.json" -H "Content-Type: application/json" -X POST $HOST_URL/api/ectutorials/tutorial  | python -c "import sys,json; print json.load(sys.stdin)['id']"`
export TUTORIAL_DIR=../../ec-tutorials/EntityCompiler
for MODULE_DIR in $TUTORIAL_DIR/Module*
do
  curl -b cookies --data-binary "@$TUTORIAL_DIR/$MODULE_DIR/README.md" -H "Content-Type: text/markdown" -X POST $HOST_URL/api/ectutorials/tutorial/$TUTORIAL_ID/import/markdown
  done

export TUTORIAL_ID=`curl -b cookies -d "@tutorial-microservice-create.json" -H "Content-Type: application/json" -X POST $HOST_URL/api/ectutorials/tutorial  | python -c "import sys,json; print json.load(sys.stdin)['id']"`
export TUTORIAL_DIR=../../ec-tutorials/TutorialMicroservice
for MODULE_DIR in $TUTORIAL_DIR/Module*
do
  curl -b cookies --data-binary "@$TUTORIAL_DIR/$MODULE_DIR/README.md" -H "Content-Type: text/markdown" -X POST $HOST_URL/api/ectutorials/tutorial/$TUTORIAL_ID/import/markdown
  done

rm -f ./cookies
