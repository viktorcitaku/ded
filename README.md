# Dungeons and Dragons (ded) ![Travis (.org)](https://img.shields.io/travis/viktorcitaku/ded?label=tests)
Simple Java + React Application using a public API to create fairy tale characters

### System requirements

```
OS: macOS (should be compatible on Linux as well)
Docker: 19.03.1
Java: AdoptOpenJDK (build 11.0.8+10) (Java 11)
Node: v12.18.3
Browser: Chrome (latest), Firefox (latest)
```

### How to build and run the application?
#### The docker way (The only way)

If you have docker installed the following commands have to be executed one after the other in the current root folder (Please tweak the docker commands accordingly).

1. You need to run first a script which builds the React application and then copies over the files to the Java app dir. The following command is required: `./build.sh`
2. `docker build --file Dockerfile.development -t ded .`
3. `docker run -it -p 8080:8080 -e PORT=8080 -e NEW_RELIC_LICENSE_KEY='YOUR KEY' --name=ded_container ded`

### Live Demo

[D&D](http://serene-crag-32422.herokuapp.com/ded)

### Open API (Swagger)

The current Swagger documentation might have some issues!

[Swagger](http://serene-crag-32422.herokuapp.com/ded/swagger-ui)
