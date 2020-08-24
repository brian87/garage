up:
	mvn clean compile package
	docker-compose up -d --build
	
down:
	docker-compose down -v