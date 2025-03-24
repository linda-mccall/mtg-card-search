DO $$

DECLARE USER_ID integer;

BEGIN

INSERT INTO person (email, password) VALUES
	 ('integration@test.com', '$2a$10$Tc7WQpbsHPema8k8V7KzyOKvrcPo4ifcEK6Grv91oBWPgYjVsJOVi') RETURNING id INTO USER_ID;

INSERT INTO list ("name", person_id) VALUES
	 ('favs', USER_ID);
END $$;
