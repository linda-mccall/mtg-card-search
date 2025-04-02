COMMAND=start
ENV=dev

ifeq "${ENV}" "PROD"
COMMAND=build
endif

#help instructions
default_goal:
	@echo "options:"
	@$(MAKE) -pRrq -f $(lastword $(MAKEFILE_LIST)) : 2>/dev/null | awk -v RS= -F: '/^# File/,/^# Finished Make data base/ {if ($$1 !~ "^[#.]") {print $$1}}' | sort | egrep -v -e '^[^[:alnum:]]' -e '^$@$$' | xargs

start_local:
	docker compose --profile dev up

#start_backend: start_local_db
#	cd card-search && java -Dspring.profiles.active=dev -jar build/app.jar
