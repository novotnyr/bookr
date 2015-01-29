# Bookr [![Build Status](https://travis-ci.org/ppatrik/paz1c-bookr.svg)](https://travis-ci.org/ppatrik/paz1c-bookr)

**Starting a database**
```
mvn -Pdb exec:java
```

**Insert sample data (one-time) !migrate before continue**
```
mvn flyway:migrate
```

**Starting a database GUI**
```
mvn -Pdb-gui exec:java
```