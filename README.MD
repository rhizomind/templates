how to use this repo:
1. cd into template of interest e.g. cra-template-mhl
2. make 'output' directory: 
3. execute qa-engine
```
cd cra-template-mhl
mkdir ./output
docker run -v $(pwd):/input -v $(pwd)/output:/output -u $(id -u):$(id -g)  mustache-cli:local
```