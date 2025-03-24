DO $$
DECLARE
  USER_ID integer;
  LIST_ID integer;

BEGIN
  SELECT id INTO USER_ID
  FROM person
  WHERE email = 'linda.mccall@unosquare.com';

  INSERT INTO list ("name", person_id)
  VALUES ('favs 2', USER_ID)
  RETURNING id INTO LIST_ID;

END $$;
