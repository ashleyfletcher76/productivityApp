# Europace Todo App

# Important commands
* Build and begin project
```bash
make build-up
```
* Stop containers running
```bash
make down
```
* Check docker logs
```bash
make logs
```
* Remove docker files
```bash
make clean
```

#SQL commmands for H2 console
* Insert a user
```bash
INSERT INTO users (username, password) VALUES ('test', 'test');
```
* View all users in db
```bash
SELECT * FROM users;
```