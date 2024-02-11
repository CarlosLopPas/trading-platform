#!/usr/bin/sh

# Create users
echo 'Creating "Diamond" and "Paper" users'
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/user --data '[{"id":1, "name":"Diamond", "password":"diamond"}]'
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/user --data '[{"id":2, "name":"Paper", "password":"paper"}]'

# Create security item
echo 'Creating "WSB" security item'
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/security --data '[{"id":1, "name":"WSB"}]'

# Create sell and buy orders
echo 'Creating a buy order for "Diamond" and a sell order for "Paper"'
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/order --data '[{"id":1, "user":{"id":1,"name":"Diamond","password":"diamond"}, "security":{"id":1,"name":"WSB"}, "type": 0, "price": 101.0, "quantity": 50}]'
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/order --data '[{"id":2, "user":{"id":2,"name":"Paper","password":"paper"}, "security":{"id":1,"name":"WSB"}, "type": 1, "price": 100.0, "quantity": 100}]'

# Do a trade
echo 'Trying to do a trade'
curl -X POST -H 'Content-Type: application/json' -i http://localhost:8080/trade --data '{"id":1,"name":"WSB"}'